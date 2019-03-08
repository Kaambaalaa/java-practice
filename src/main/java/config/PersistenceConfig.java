package config;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "repository", entityManagerFactoryRef = "")
//@ComponentScans(value = {@ComponentScan("dao"), @ComponentScan("service"), @ComponentScan("entity")})
public class PersistenceConfig {

    protected Properties properties(DataSource dataSource) throws SQLException {
        Properties properties = new Properties();

        properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
        properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
//        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl");
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "org.springframework.orm.hibernate5.SpringSessionContext");

        if (dataSource != null) {
            properties.put("hibernate.connection.datasource", dataSource);
        }

        properties.put("hibernate.generate_statistics", Boolean.TRUE.toString()
        );

        return properties;
    }

    //
    @Bean
    public HibernatePersistenceProvider hibernatePersistenceProvider() {
        return new HibernatePersistenceProvider();
    }

    //
    @Bean
    protected EntityManagerFactory entityManagerFactory(HibernatePersistenceProvider hibernatePersistenceProvider, DataSource dataSource) throws SQLException {
        PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl("LOCAL",
                getEntities(), properties(dataSource));
        Map<String, Object> configuration = new HashMap<>();

        Integrator integrator = null;
        if (integrator != null) {
            configuration.put(
                    "hibernate.integrator_provider",
                    (IntegratorProvider) () ->
                            Collections.singletonList(integrator)
            );
        }

        return hibernatePersistenceProvider
                .createContainerEntityManagerFactory(
                        persistenceUnitInfo,
                        configuration
                );
    }

    public List<String> getEntities() {
        return new Reflections("").getTypesAnnotatedWith(Entity.class).stream().map(Class::getName).collect(Collectors.toList());
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager dbTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory);
        return transactionManager;
    }

}
