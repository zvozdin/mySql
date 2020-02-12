package ua.com.juja.service;

import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public List<String> commands() {
        return Arrays.asList("help", "menu", "connect");
    }
}