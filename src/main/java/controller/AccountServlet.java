package controller;

import com.google.gson.Gson;
import dao.Dao;
import entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
@WebServlet(name = "AccountServlet", urlPatterns = "/accounts")
public class AccountServlet extends HttpServlet {

    private Dao<Account> accountDao;

    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Account> accountList = accountDao.findAll();
        String accountListString = gson.toJson(accountList);

        PrintWriter out = response.getWriter();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(accountListString);
        out.flush();
    }

}
