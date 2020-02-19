package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Help implements Command {

    private View view;

    public Help(View view) {
        this.view = view;
    }

    public Help() {

    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        try (Scanner scanner = new Scanner(new File("src\\main\\resources\\help.txt"))) {
            String commands = scanner.nextLine();
            while (scanner.hasNextLine()) {
                commands += "\r\n" + scanner.nextLine();
            }
            view.write(commands);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processWeb(DatabaseManager manager, String name, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public String toString() {
        return "help";
    }
}