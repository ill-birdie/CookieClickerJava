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

    public static void displayAt(Scanner scan, String path) {
        ArrayList<String> text = getFileText(path);
        boolean skipped = false;
        for (String line : text) {
            System.out.print(line);
            if (!skipped) {
                System.out.print(" ");
                if (scan.nextLine().strip().equalsIgnoreCase("skip")) {
                    skipped = true;
                }
            } else {
                System.out.println();
            }
        }
    }
}
