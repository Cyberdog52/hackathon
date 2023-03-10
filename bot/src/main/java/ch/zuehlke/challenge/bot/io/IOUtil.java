package ch.zuehlke.challenge.bot.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Function;

@Component
public class IOUtil<T> {

    private static final String STOP_SIGNAL = "STOP";

    public T requestInput(String message, Function<String, T> function) {
        System.out.print(message);
        String parsedInput;

        while (true) {
            try {
                parsedInput = new Scanner(System.in).nextLine();

                if (parsedInput.equals(STOP_SIGNAL)) {
                    System.out.println("Stopping...");
                    System.exit(0);
                }

                return function.apply(parsedInput);
            } catch (Exception e) {
                System.err.printf("We weren't able to parse your input. Enter '%s' to stop completely. \n", STOP_SIGNAL);
                System.err.printf("Reason why it could not be parsed: %s \n", e.getMessage());
                System.err.print("Please try again: ");
            }
        }
    }
}
