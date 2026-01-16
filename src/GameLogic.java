public class GameLogic {
    Player player;
    Cookie cookie;
    UpgradeList upgrades;

    public GameLogic() {
        this.player = new Player();
        this.cookie = new Cookie();
        this.upgrades = new UpgradeList();
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
                "\nCookies: " + player.getNumCookies() +
                "\nHighest cookies: " + player.getMaxCookies() +
                "\nNumber of cookie clicks: " + cookie.getTimesClicked() +
                "\nNumber of upgrades: " + upgrades.getNumBought() +
                "\nUpgrade multiplier: " + upgrades.getMultiplier() +
                "\nRebirths: " + player.getNumRebirths() +
                "\nRebirth base multiplier: " + player.getRebirthMulti();

    }

    public void purchase(String target) {
        int upgradeIdx = upgrades.getUpgradeIdx(target);
        if (upgradeIdx != -1) {
            Upgrade targetUpgrade = upgrades.getUpgrade(upgradeIdx);
            boolean unbought = upgrades.upgradeUnbought(targetUpgrade);
            boolean canAfford = this.player.getNumCookies() >= targetUpgrade.getCost();
            if (unbought && canAfford) {
                upgrades.buyUpgrade(targetUpgrade);
                player.incrementCookies(-targetUpgrade.getCost());
                System.out.println("Bought!");
            } else if (!canAfford && unbought) {
                System.out.println("You don't have enough cookies to buy this upgrade! " +
                        "(" + this.player.getNumCookies() + "/" + targetUpgrade.getCost() + ")");
            } else {
                System.out.println("You already have this upgrade: \"" + target + "\"");
            }
        } else {
            System.out.println("This upgrade does not exist: \"" + target + "\"");
        }
    }

    public void executeCommand(String command) {
        System.out.println();
        switch (command.substring(0, 1)) {
            case "c" -> System.out.println(getCommands());

            case "s" -> System.out.println(getStats());

            case "b" -> {
                System.out.println(upgrades.getUnbought());
                String target = Dialogue.promptUser("Which upgrade would you like to buy? (Type full name) ");
                purchase(target);
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

    public void play() {
        boolean lastWasCommand = false;
        String userInput;
        while (true) {
            String prompt;
            if (!lastWasCommand) {
                prompt = "Cookies: " + player.getNumCookies() + " | ";
            } else {
                prompt = "Press [ENTER] to continue clicking, or type a different command: ";
            }
            userInput = Dialogue.promptUser(prompt);
            if (userInput.equals("quit")) {
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
    }

    public void launch() {
        Dialogue.displayAt("src/dialogue/intro.txt");
        play();
        Dialogue.displayAt("src/dialogue/goodbye.txt");
    }
}
