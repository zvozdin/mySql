package ua.com.juja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.command.*;

import java.util.Arrays;
import java.util.List;

@Component
public class ServiceImpl implements Service {

    @Autowired
    private Command connect;

    @Override
    public List<Command> commands() {
        return Arrays.asList(
                new Help(),
                connect,
                new CreateDatabase(),
                new DropDatabase(),
                new Tables(),
                new Find(),
                new CreateTable(),
                new DropTable(),
                new Insert(),
                new Update(),
                new DeleteRow(),
                new Clear()
        );
    }
}