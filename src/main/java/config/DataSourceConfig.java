package config;

import config.property.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @DependsOn("serverH2")
    @PersistenceUnit(unitName = "dataSource")
    public DataSource dataSource(DataSourceProperties datasourceProperties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(datasourceProperties.getJdbcUrl(),
                datasourceProperties.getUser(), datasourceProperties.getPassword());
        dataSource.setDriverClassName(datasourceProperties.getDriverClassName());
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
