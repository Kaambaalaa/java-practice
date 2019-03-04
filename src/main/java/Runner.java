import dao.Dao;
import entity.Account;
import entity.Attribute;
import entity.Chat;
import entity.Message;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;

public class Runner {

    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/default";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";
    private static final String JDBC_DRIVER = "org.h2.Driver";

    public static void main(String[] args) throws LifecycleException, SQLException {
        startTomcat();
        startH2();
//        initSchema();
//        demonstrateDao();
    }

    private static void startTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8081);
//        Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());
//        ctx.addServletMapping("/*", "Embedded");
//        tomcat.start();
    }

    private static void startH2() throws SQLException {
        Server server = null;
        try {
            server = Server.createTcpServer("-tcpAllowOthers").start();
            server.stop();
            server.start();
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.
                    getConnection(JDBC_URL + ";INIT=CREATE SCHEMA IF NOT EXISTS PUBLIC;", JDBC_USER, JDBC_PASSWORD);
            System.out.println("Connection Established: "
                    + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());
            System.err.println();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void initSchema() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        flyway.migrate();
    }

    private static void demonstrateDao() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(JDBC_URL);
        ds.setUser(JDBC_USER);
        ds.setPassword(JDBC_PASSWORD);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds.getConnection());
        Dao<Account> accountDao = new Dao<>(Account.class, jdbcTemplate);
        Dao<Chat> chatDao = new Dao<>(Chat.class, jdbcTemplate);
        Dao<Message> messageDao = new Dao<>(Message.class, jdbcTemplate);

        Account account = accountDao.save(
                new Account(1L, "qwerty", "mail@mail", null));

        Chat chat = chatDao.save(new Chat(1L, "cool_chat", null,Collections.singletonList(account),
                Collections.singletonList(new Attribute(1L, Attribute.Type.PUBLIC))));

        Message message = messageDao.save(new Message(1L, "Hello world!",account, chat));

//        Chat deletedChat = chatDao.delete(chat);
    }

}
