package view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        try {
            return new Scanner(System.in).nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
