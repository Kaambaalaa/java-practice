package config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.Entity;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = {@ComponentScan("dao"), @ComponentScan("service"), @ComponentScan("entity")})
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

    @Bean
    public HibernatePersistenceProvider hibernatePersistenceProvider() {
        return new HibernatePersistenceProvider();
    }

//    //    @Bean
//    protected EntityManagerFactory persistenceUnitInfo(HibernatePersistenceProvider hibernatePersistenceProvider, DataSource dataSource) throws SQLException {
//        PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl("LOCAL",
//                getEntities(), properties(dataSource));
//        Map<String, Object> configuration = new HashMap<>();
//
//        Integrator integrator = null;
//        if (integrator != null) {
//            configuration.put(
//                    "hibernate.integrator_provider",
//                    (IntegratorProvider) () ->
//                            Collections.singletonList(integrator)
//            );
//        }
//
//        return hibernatePersistenceProvider
//                .createContainerEntityManagerFactory(
//                        persistenceUnitInfo,
//                        configuration
//                );
//    }

    public List<String> getEntities() {
        return new Reflections("").getTypesAnnotatedWith(Entity.class).stream().map(Class::getName).collect(Collectors.toList());
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(HibernatePersistenceProvider hibernatePersistenceProvider, DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setPersistenceProvider(hibernatePersistenceProvider);
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setPersistenceUnitName("LOCAL");
//        return entityManagerFactoryBean;
//    }

//    @Bean
//    public JpaTransactionManager geJpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }

//    private Properties getHibernateProperties() {
//        Properties properties = new Properties();
//        properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
//        properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
////        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
//        properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
//        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
//        return properties;
//    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory(DataSource dataSource) throws SQLException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("");
        sessionFactory.setHibernateProperties(properties(dataSource));
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

}
