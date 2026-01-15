public class GameLogic {
    Player player;
    Cookie cookie;

    public GameLogic() {
        this.player = new Player();
        this.cookie = new Cookie();
    }

    public void executeCommand(String command) {
        System.out.println();
        switch (command.substring(0, 1)) {
            case "c" -> {
                System.out.println("""
                        List of commands:\
                        
                        stats : displayers user statistics\
                        
                        upgrades : displays the upgrade tree\
                        
                        rebirth : allows the user to rebirth, resetting cookies for a higher base multiplier\
                        
                        quit : quits the simulation\
                        
                        """);
            }
            case "s" -> {
                System.out.println("-- USER STATS --" +
                        "\nCookies: " + player.getNumCookies() +
                        "\nHighest cookies: " + player.getMaxCookies() +
                        "\nNumber of cookie clicks: " + cookie.getTimesClicked() +
                        "\nRebirths: " + player.getNumRebirths());
            }
            case "u" -> {
                System.out.println("Viewing upgrade tree");
            }
            case "r" -> {
                String rebirthOutput;
                if (player.canRebirth()) {
                    player.rebirth();
                    rebirthOutput = "Rebirthed!\n";
                } else {
                    rebirthOutput = "Cannot rebirth: " + player.getNumCookies() +
                            "/" + player.requiredRebirthAmt() + " cookies required";
                }
                System.out.println(rebirthOutput);
            }
            default -> {
                System.out.println("Unknown command: \"" + command + "\"");
            }
        }
    }

    public void play() {
        boolean lastWasCommand = false;
        String userInput;
        while (true) {
            String prompt;
            if (!lastWasCommand) {
                prompt = "Cookies: " + player.getNumCookies() + " | ";
            } else {
                prompt = "Press [ENTER] to continue clicking: ";
            }
            userInput = Dialogue.promptUser(prompt);
            if (userInput.equals("quit")) {
                break;
            }
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
        Dialogue.displayAt("src/dialogue/goodbye.txt");
    }
}
