import java.util.Scanner;

public class GameLogic {
    private final Scanner consoleReader;
    private final Player player;
    private final Cookie cookie;
    private final Upgrades upgrades;

    public GameLogic() {
        this.consoleReader = new Scanner(System.in);
        this.player = new Player();
        this.cookie = new Cookie();
        this.upgrades = new Upgrades();
    }

    /**
     * Crucial function which acts in the same way as Python 3's "input" built-in.
     * @param prompt The text to be displayed to the user.
     * @return The text the user enters into the console.
     */
    public String promptUser(String prompt) {
        System.out.print(prompt);
        return this.consoleReader.nextLine().strip();
    }

    public String getCommands() {
        return """
                List of commands:\
                
                stats : displays user statistics\
                
                buy : asks for a name, then buys the upgrade if possible\
                
                upgrades : displays unbought upgrades\
                
                rebirth : allows the user to rebirth, resetting cookies for a higher base multiplier\
                
                quit : quits the simulation\
                """;
    }

    public String getStats() {
        return  "-- USER STATS --" +
                "\nBase click value: " + player.getRebirthMulti() +
                "\nUpgrade multiplier: " + upgrades.getMultiplier() +
                "\nTotal click value: " + (player.getRebirthMulti() * upgrades.getMultiplier()) +
                "\nCookies: " + player.getNumCookies() +
                "\nHighest cookies: " + player.getMaxCookies() +
                "\nNumber of cookie clicks: " + cookie.getTimesClicked() +
                "\nNumber of upgrades: " + upgrades.getNumBought() +
                "\nRebirths: " + player.getNumRebirths();

    }

    public String validatePurchase(String target) {
        if (target.isEmpty()) {
            return "Exiting upgrade menu";
        }
        int upgradeIdx = upgrades.getUpgradeIdx(target);
        if (upgradeIdx == -1) {
            return "This upgrade does not exist: \"" + target + "\"";
        }

        // User wants to buy an upgrade and the upgrade exists
        Upgrade targetUpgrade = upgrades.getUpgrade(upgradeIdx);
        boolean unbought = upgrades.upgradeUnbought(targetUpgrade);
        boolean canAfford = this.player.getNumCookies() >= targetUpgrade.getCost();
        if (!unbought || !canAfford) {
            if (!unbought) {
                return "You already have this upgrade: \"" + target + "\"";
            } else {
                return "You don't have enough cookies to buy this upgrade! " +
                        "(" + this.player.getNumCookies() + "/" + targetUpgrade.getCost() + ")";
            }
        }

        // Upgrade is unbought and user can afford to buy it
        int prevCookies = player.getNumCookies();
        purchase(targetUpgrade);
        int newCookies = player.getNumCookies();
        return "Bought! Cookie balance: " + prevCookies + " -> " + newCookies +
                "\nNew upgrade multiplier: " + upgrades.getMultiplier();
    }

    public void purchase(Upgrade u) {
        upgrades.buyUpgrade(u);
        player.incrementCookies(-u.getCost());
    }

    public void executeCommand(String command) {
        System.out.println();
        switch (command.substring(0, 1)) {
            case "c" -> System.out.println(getCommands());

            case "s" -> System.out.println(getStats());

            case "b" -> {
                System.out.println(upgrades.getUnbought());
                String target = promptUser("Which upgrade would you like to buy? (Type full name, leave blank if none) | > ");
                while (!target.isEmpty()) {
                    System.out.println(validatePurchase(target));
                    target = promptUser("> ");
                }
            }

            case "u" -> {
                System.out.println("-- Unbought upgrades: --");
                System.out.println(upgrades.getUnbought());
            }
            case "r" -> {
                String rebirthOutput;
                if (player.canRebirth()) {
                    upgrades.reset();
                    player.rebirth();
                    rebirthOutput = "Rebirthed! Current rebirth multiplier: " + player.getRebirthMulti();
                } else {
                    rebirthOutput = "Cannot rebirth: " + player.getNumCookies() +
                            "/" + player.requiredRebirthAmt() + " cookies required";
                }
                System.out.println(rebirthOutput);
            }
            default -> System.out.println("Unknown command: \"" + command + "\"");
        }
        System.out.println();
    }

    public void outro() {
        System.out.println("\n" + getStats() +
                "\n" +
                "\nBefore you go, take a look at your stats!");
        Dialogue.displayAt(this.consoleReader, "src/dialogue/goodbye.txt");
    }

    /**
     * Places the user in the Cookie Clicker terminal state.
     * Each iteration, the user is prompted to press ENTER or type a command.
     * If lastWasCommand is true, the prompt simply tells the user to continue clicking.
     * Otherwise, it will display the amount of cookies, which would have changed.
     * Afterward, if the user types a command, the executeCommand() function is called, and lastWasCommand is set to true.
     * Otherwise, the function clicks the Cookie object and increments the user's cookie count.
     */
    public void play() {
        boolean lastWasCommand = false;
        String userInput;
        while (true) {
            String prompt;
            if (!lastWasCommand) {
                prompt = "Cookies: " + player.getNumCookies() + " | ";
            } else {
                prompt = "Press [ENTER] to continue clicking, or type another command: ";
            }
            userInput = promptUser(prompt);
            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
            if (!userInput.isEmpty()) {
                executeCommand(userInput);
                lastWasCommand = true;
            } else {
                int baseClick = cookie.click();
                int upgradesMulti = upgrades.getMultiplier();
                int rebirthMulti = player.getRebirthMulti();
                player.incrementCookies(baseClick * upgradesMulti * rebirthMulti);
                lastWasCommand = false;
            }
        }
        outro();
    }

    public void launch() {
        Dialogue.displayAt(this.consoleReader, "src/dialogue/intro.txt");
        play();
        this.consoleReader.close();
    }
}
