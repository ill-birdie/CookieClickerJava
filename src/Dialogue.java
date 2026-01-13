import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public final class Dialogue {

    public Dialogue() { }

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

    public static String promptUser(String prompt) {
        Scanner consoleReader = new Scanner(System.in);
        System.out.print(prompt);
        return consoleReader.nextLine().strip().toLowerCase();
    }

    public static void displayAt(String path) {
        ArrayList<String> text = getFileText(path);
        boolean skipped = false;
        for (String line : text) {
            System.out.print(line);
            if (!skipped) {
                if (promptUser(" ").equals("skip")) {
                    skipped = true;
                }
            }
            else {
                System.out.println();
            }
        }
    }
}
