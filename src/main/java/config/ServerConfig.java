package config;

import config.property.DataSourceProperties;
import config.property.TomcatProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.Tomcat;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@Configuration
public class ServerConfig {

    @Bean
    public Server serverH2(DataSourceProperties datasourceProperties) {
        Server server = null;
        try {
            log.info("Creating TCP H2 server.");
            server = Server.createTcpServer("-tcpAllowOthers");
            server.start();
            server.stop();
            log.info("Starting TCP H2 server.");
            server.start();
            Class.forName(datasourceProperties.getDriverClassName());
            Connection conn = DriverManager.getConnection(datasourceProperties.getJdbcUrl(),
                    datasourceProperties.getUser(), datasourceProperties.getPassword());
            System.err.println("Connection Established: "
                    + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return server;
    }

    @Bean
    public Tomcat tomcat(TomcatProperties tomcatProperties) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(tomcatProperties.getPort());
        return tomcat;
    }

}
