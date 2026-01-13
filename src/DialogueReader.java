import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DialogueReader {
    DialogueReader() { }

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
}
