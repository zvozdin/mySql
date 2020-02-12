package ua.com.juja.service;

import java.util.List;

public interface Service {

    List<String> commands();

    void connect(String database, String user, String password);
}