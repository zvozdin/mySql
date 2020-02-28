package ua.com.juja.service;

import ua.com.juja.command.*;

import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public List<Command> commands() {
        return Arrays.asList(
                new Help(),
                new Connect(),
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