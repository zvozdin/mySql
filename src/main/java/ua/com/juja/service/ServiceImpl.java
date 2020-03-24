package ua.com.juja.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ServiceImpl implements Service {

    @Override
    public List<String> getActions() {
        return Arrays.asList(
                "help",
                "connect",
                "newDatabase",
                "dropDatabase",
                "tables",
                "newTable",
                "dropTable",
                "insert",
                "update",
                "delete",
                "clear"
        );
    }
}