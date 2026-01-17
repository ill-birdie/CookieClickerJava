public class GameLogic {
    private final Dialogue DIALOGUE;
    private final Player PLAYER;
    private final Cookie COOKIE;
    private final Upgrades UPGRADES;

    public GameLogic() {
        this.DIALOGUE = new Dialogue();
        this.PLAYER = new Player();
        this.COOKIE = new Cookie();
        this.UPGRADES = new Upgrades();
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
                "\nBase click value: " + PLAYER.getRebirthMulti() +
                "\nUpgrade multiplier: " + UPGRADES.getMultiplier() +
                "\nTotal click value: " + (PLAYER.getRebirthMulti() * UPGRADES.getMultiplier()) +
                "\nCookies: " + PLAYER.getNumCookies() +
                "\nHighest cookies: " + PLAYER.getMaxCookies() +
                "\nNumber of cookie clicks: " + COOKIE.getTimesClicked() +
                "\nNumber of upgrades: " + UPGRADES.getNumBought() +
                "\nRebirths: " + PLAYER.getNumRebirths();

    }

    public String validatePurchase(String target) {
        if (target.isEmpty()) {
            return "Exiting upgrade menu";
        }
        int upgradeIdx = UPGRADES.getUpgradeIdx(target);
        if (upgradeIdx == -1) {
            return "This upgrade does not exist: \"" + target + "\"";
        }

        // User wants to buy an upgrade and the upgrade exists
        Upgrade targetUpgrade = UPGRADES.getUpgrade(upgradeIdx);
        boolean unbought = UPGRADES.upgradeUnbought(targetUpgrade);
        boolean canAfford = this.PLAYER.getNumCookies() >= targetUpgrade.getCost();
        if (!unbought || !canAfford) {
            if (!unbought) {
                return "You already have this upgrade: \"" + target + "\"";
            } else {
                return "You don't have enough cookies to buy this upgrade! " +
                        "(" + this.PLAYER.getNumCookies() + "/" + targetUpgrade.getCost() + ")";
            }
        }

        // Upgrade is unbought and user can afford to buy it
        int prevCookies = PLAYER.getNumCookies();
        purchase(targetUpgrade);
        int newCookies = PLAYER.getNumCookies();
        return "Bought! Cookie balance: " + prevCookies + " -> " + newCookies +
                "\nNew upgrade multiplier: " + UPGRADES.getMultiplier();
    }

    public void purchase(Upgrade u) {
        UPGRADES.buyUpgrade(u);
        PLAYER.incrementCookies(-u.getCost());
    }

    public void executeCommand(String command) {
        System.out.println();
        switch (command.substring(0, 1)) {
            case "c" -> System.out.println(getCommands());

            case "s" -> System.out.println(getStats());

            case "b" -> {
                System.out.println(UPGRADES.getUnbought());
                String target = DIALOGUE.input("Which upgrade would you like to buy? (Type full name, leave blank if none) | > ");
                while (!target.isEmpty()) {
                    System.out.println(validatePurchase(target));
                    target = DIALOGUE.input("> ");
                }
            }

            case "u" -> {
                System.out.println("-- Unbought upgrades: --");
                System.out.println(UPGRADES.getUnbought());
            }
            case "r" -> {
                String rebirthOutput;
                if (PLAYER.canRebirth()) {
                    UPGRADES.reset();
                    PLAYER.rebirth();
                    rebirthOutput = "Rebirthed! Current rebirth multiplier: " + PLAYER.getRebirthMulti();
                } else {
                    rebirthOutput = "Cannot rebirth: " + PLAYER.getNumCookies() +
                            "/" + PLAYER.requiredRebirthAmt() + " cookies required";
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
        DIALOGUE.displayAt("src/dialogue/goodbye.txt");
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
                prompt = "Cookies: " + PLAYER.getNumCookies() + " | ";
            } else {
                prompt = "Press [ENTER] to continue clicking, or type another command: ";
            }
            userInput = DIALOGUE.input(prompt);
            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
            if (!userInput.isEmpty()) {
                executeCommand(userInput);
                lastWasCommand = true;
            } else {
                int baseClick = COOKIE.click();
                int upgradesMulti = UPGRADES.getMultiplier();
                int rebirthMulti = PLAYER.getRebirthMulti();
                PLAYER.incrementCookies(baseClick * upgradesMulti * rebirthMulti);
                lastWasCommand = false;
            }
        }
        outro();
    }

    public void launch() {
        DIALOGUE.displayAt("src/dialogue/intro.txt");
        play();
    }
}
