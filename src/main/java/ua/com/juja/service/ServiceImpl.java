package ua.com.juja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.com.juja.controller.action.*;

import java.util.Arrays;
import java.util.List;

@Component
public class ServiceImpl implements Service {

    @Autowired
    @Qualifier("connectAction")
    private Action connect;
    @Autowired
    @Qualifier("menu")
    private Action menu;

    // TODO return List<String> without Action interface. Then delete action package
    @Override
    public List<Action> getActions() {
        return Arrays.asList(
                new HelpAction(),
                connect,
                new CreateDatabaseAction(),
                new DropDatabaseAction(),
                new TablesAction(),
                new FindAction(),
                new CreateTableAction(),
                new DropTableAction(),
                new InsertAction(),
                new UpdateAction(),
                new DeleteAction(),
                new CleartAction(),
                menu,
                new ErrorAction()
        );
    }

    @Override
    public Action getAction(String url) {
        for (Action action : getActions()) {
            if (action.canProcess(url)) {
                return action;
            }
        }
        return new NullAction();
    }
}