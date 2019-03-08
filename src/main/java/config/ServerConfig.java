package config;

import com.google.gson.Gson;
import config.property.DataSourceProperties;
import config.property.TomcatProperties;
import controller.AccountServlet;
import dao.AccountDao;
import dao.Dao;
import dao.DaoFactory;
import entity.Account;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import repository.AccountRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@DependsOn("daoConfig")
@ComponentScan
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

    @NoArgsConstructor
    @WebServlet(name = "AccountServlet", urlPatterns = "/accounts")
    public static class AccountServlet extends HttpServlet {

        public static Dao<Account> accountDao;

        @Override
        public void init() {

        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            Account account = Account.builder()
                    .accountId(1L)
                    .password("qwerty")
                    .email("mail@mail").build();
            accountDao.save(account);


            System.out.println(accountDao);
            List<Account> accountList = accountDao.findAll();
            Map<String, Object> json = new HashMap<>();
            json.put("accountList", accountList);
            String accountListString = new Gson().toJson(json);
            System.err.println(accountDao.findAll());
            System.err.println(accountListString);
            PrintWriter out = response.getWriter();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            out.print(accountListString);
            out.flush();
        }

    }

//    @Bean
//    @Autowired
//    public AccountServlet accountServlet(Dao<Account> accountDao) {
//        System.err.println(accountDao);
//        return new AccountServlet(accountDao);
//    }

    @Bean
    @DependsOn("demo")
    public Tomcat tomcat(Dao<Account> accountDao) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setHostname("localhost");
        String appBase = ".";
        tomcat.getHost().setAppBase(appBase);

        File docBase = new File("tomcat:8081");
        Context context = tomcat.addContext("", docBase.getAbsolutePath());
        Class servletClass = AccountServlet.class;
        AccountServlet.accountDao = accountDao;
        Tomcat.addServlet(
                context, servletClass.getSimpleName(), servletClass.getName());
        context.addServletMappingDecoded(
                "/my-servlet/*", servletClass.getSimpleName());

//        tomcat.addServlet("/accounts", "AccountServlet", accountServlet);
        tomcat.start();
        tomcat.getServer().await();
        return tomcat;
    }

}
