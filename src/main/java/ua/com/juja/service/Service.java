package ua.com.juja.service;

import ua.com.juja.controller.UserActionLog;
import ua.com.juja.model.entity.Description;

import java.util.List;

public interface Service {

    List<String> getCommands();

    List<Description> getCommandsDescription();

    List<UserActionLog> getAllFor(String userName);
}