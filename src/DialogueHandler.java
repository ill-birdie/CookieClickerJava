import java.util.ArrayList;
import java.util.Scanner;

public class DialogueHandler {
    Scanner scan;

    public DialogueHandler() {
        scan = new Scanner(System.in);
    }

    public String promptUser(String prompt) {
        System.out.print(prompt);
        return scan.nextLine().strip().toLowerCase();
    }

    public void displayDialogue(String path) {
        ArrayList<String> text = DialogueReader.getFileText(path);
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
