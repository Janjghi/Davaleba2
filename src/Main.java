import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class InvalidNumberException extends Exception {
    public InvalidNumberException(String message) {
        super(message);
    }
}

class NumberUtils {
    private NumberUtils() {
        // Private constructor to prevent instantiation
    }

    public static int[] readNumbersFromFile(String filename) throws IOException, InvalidNumberException {
        int[] numbers = new int[10];
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int number = Integer.parseInt(line);
                    if (number < 0) {
                        throw new InvalidNumberException("Invalid number: " + number);
                    }
                    numbers[index++] = number;
                } catch (NumberFormatException e) {
                    // Handle NumberFormatException
                    System.err.println("Invalid number format: " + line);
                }
            }
        }

        return numbers;
    }

    public static int calculateSum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            try {
                sum = Math.addExact(sum, number);
            } catch (ArithmeticException e) {
                // Handle ArithmeticException
                System.err.println("Arithmetic exception occurred: " + e.getMessage());
            }
        }
        return sum;
    }
}

public class Main {
    public static void main(String[] args) {
        String filename = "numbers.txt";

        try {
            int[] numbers = NumberUtils.readNumbersFromFile(filename);
            for (int number : numbers) {
                System.out.println(number);
            }

            int sum = NumberUtils.calculateSum(numbers);
            System.out.println("Sum: " + sum);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (InvalidNumberException e) {
            System.err.println("Invalid number found: " + e.getMessage());
        }
    }
}
