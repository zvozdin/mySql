package ua.com.juja.controller.action;

import org.springframework.stereotype.Component;
import ua.com.juja.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class Menu implements Action {

//    @Autowired
//    private Service serv;

    @Override
    public boolean canProcess(String url) {
        return url.equalsIgnoreCase("/menu") || url.equals("/");
    }

    @Override
    public void get(Service service, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Action> commands = service.getActions();
        commands = commands.subList(0, commands.size() - 2);

        req.setAttribute("commands", commands.toString()
                .substring(1, commands.toString().length() - 1));
        goToJsp("menu.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) {
        // do nothing
    }

    @Override
    public String toString() {
        return "menu";
    }
}
