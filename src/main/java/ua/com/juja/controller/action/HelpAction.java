package ua.com.juja.controller.action;

import ua.com.juja.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelpAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/help");
    }

    @Override
    public void get(Service service, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            goToJsp("help.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) {
        // do nothing
    }

    @Override
    public String toString() {
        return "help";
    }
}
