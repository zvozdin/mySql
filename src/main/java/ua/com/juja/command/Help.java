package ua.com.juja.command;

import ua.com.juja.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Help implements Command {

    private View view;

    public Help(View view) {
        this.view = view;
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
}