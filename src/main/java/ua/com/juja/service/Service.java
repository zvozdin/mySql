package ua.com.juja.service;

import ua.com.juja.controller.action.Action;

import java.util.List;

public interface Service {

    List<Action> getActions();

    Action getAction(String url);
}