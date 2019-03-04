package config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application.yml")
public class DataSourceProperties {

    @Value("${datasource.url}")
    private String jdbcUrl;

    @Value("${datasource.user}")
    private String user;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driver}")
    private String driverClassName;

}
