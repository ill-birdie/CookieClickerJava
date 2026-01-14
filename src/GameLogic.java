public class GameLogic {
    Player player;
    Cookie cookie;

    public GameLogic() {
        this.player = new Player();
        this.cookie = new Cookie();
    }

    public void executeCommand(String command) {
        switch (command.substring(0, 1)) {
            case "r" -> {
                String rebirthOutput;
                if (player.canRebirth()) {
                    player.rebirth();
                    rebirthOutput = "Rebirthed!";
                } else {
                    rebirthOutput = "Cannot rebirth: " + player.getNumCookies() +
                            "/" + player.requiredRebirthAmt() + " cookies required";
                }
                System.out.println(rebirthOutput);
            }
            case "u" -> {
                System.out.println("Viewing upgrade tree");
            }
            default -> {
                System.out.println("Unknown command: \"" + command + "\"");
            }
        }
    }

    public void play() {
        boolean lastWasCommand = false;
        String userInput = "";
        while (!userInput.equals("quit")) {
            String prompt;
            if (!lastWasCommand) {
                prompt = "Cookies: " + player.getNumCookies() + " | ";
            } else {
                prompt = "Press [ENTER] to continue clicking: ";
            }
            userInput = Dialogue.promptUser(prompt);
            if (!userInput.isEmpty()) {
                executeCommand(userInput);
                lastWasCommand = true;
            } else {
                player.incrementCookies(cookie.click(player));
                lastWasCommand = false;
            }
        }
    }

    public void launch() {
        Dialogue.displayAt("src/dialogue/intro.txt");
        play();
    }
}
