package config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    @DependsOn("serverH2")
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.setLocations("db/migration");
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();
        return flyway;
    }

}
