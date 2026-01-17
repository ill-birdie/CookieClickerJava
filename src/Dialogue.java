import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public final class Dialogue {
    private final Scanner CONSOLE_READER;

    public Dialogue() {
        this.CONSOLE_READER = new Scanner(System.in);
    }

    /**
     * Returns an ArrayList containing a file's text, split by newlines.
     * @param path The path to the file.
     * @return An ArrayList with every line of text from the file.
     */
    public static ArrayList<String> getFileText(String path) {
        ArrayList<String> fileText = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                fileText.add(fileReader.nextLine());
            }
            fileReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("File not found");
        }
        return fileText;
    }

    /**
     * Crucial function which acts in the same way as Python 3's "input" built-in.
     * Prompts the user to enter text into a console, and returns the text.
     * @param prompt The text to be displayed to the user.
     * @return The text the user enters into the console.
     */
    public String input(String prompt) {
        System.out.print(prompt);
        return this.CONSOLE_READER.nextLine().strip();
    }

    /**
     * Prompts the user to press ENTER for each line of text in a file.
     * @param path The path to the file.
     */
    public void displayAt(String path) {
        ArrayList<String> text = getFileText(path);
        boolean skipped = false;
        for (String line : text) {
            System.out.print(line);
            if (skipped) {
                System.out.println();
            } else {
                String userInput = input(" ");
                if (userInput.equalsIgnoreCase("skip")) {
                    skipped = true;
                }
            }
        }
    }
}
