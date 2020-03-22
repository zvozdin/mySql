package ua.com.juja.controller.action;

import ua.com.juja.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NullAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return false;
    }

    @Override
    public void get(Service service, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // do nothing
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) {
        // do nothing
    }
}
