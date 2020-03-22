package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.juja.controller.action.Action;
import ua.com.juja.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private Service service;

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(HttpServletRequest request) {
        List<Action> commands = service.getActions();
        commands = commands.subList(0, commands.size() - 2);

        request.setAttribute("commands", commands.toString()
                .substring(1, commands.toString().length() - 1));
        return "menu";
    }
}