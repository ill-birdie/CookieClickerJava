import java.util.ArrayList;
import java.util.Arrays;

public class Upgrades {
    private final ArrayList<Upgrade> UPGRADES;
    private int multiplier;

    public Upgrades() {
        this.UPGRADES = new ArrayList<>(Arrays.asList(
            new Upgrade("First Steps", "Base", 2, 0),
            new Upgrade("Triple Digits", "Base", 3, 100),
            new Upgrade("Quadruple Digits!", "Base", 4, 1000),
            new Upgrade("Greed Sets In", "Base", 2, 10000),
            new Upgrade("Your First Drought", "Base", 5, (int) Math.pow(10, 5)),
            new Upgrade("The Real Long Haul", "Base", 2, (int) (2.5 * Math.pow(10, 5))),
            new Upgrade("Second Life", "Base", 2, (int) Math.pow(10, 6)),
            new Upgrade("Death Can't Stop Me From Doing It Again", "Base", 2, (int) Math.pow(10, 7))
        ));
        this.multiplier = 1;
    }

    public void reset() {
        for (Upgrade u : this.UPGRADES) {
            if (u.isOwned()) {
                u.removeOwnership();
            }
        }
        updateMultiplier();
    }

    /**
     * Finds the index of an Upgrade by name.
     * Upgrades can only be identified through their attributes.
     * Therefore, this method searches each Upgrade and returns the Upgrade with a name that matches the argument name.
     * @param name The name of the Upgrade.
     * @return The index of the Upgrade.
     */
    public int getUpgradeIdx(String name) {
        int idx = -1;
        if (name == null || name.isEmpty()) {
            return idx;
        }
        for (int i = 0; i < this.UPGRADES.size(); i++) {
            Upgrade u = this.UPGRADES.get(i);
            if (name.equalsIgnoreCase(u.getName())) {
                return i;
            }
        }
        return idx;
    }

    public Upgrade getUpgrade(int idx) {
        return this.UPGRADES.get(idx);
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public void updateMultiplier() {
        int newMulti = 1;
        for (Upgrade u : this.UPGRADES) {
            if (u.isOwned() && u.getType().equalsIgnoreCase("Base")) {
                newMulti *= u.getMultiplier();
            }
        }
        this.multiplier = newMulti;
    }

    public int getNumBought() {
        int count = 0;
        for (Upgrade u : this.UPGRADES) {
            if (u.isOwned()) {
                count++;
            }
        }
        return count;
    }

    public String getUnbought() {
        StringBuilder unbought = new StringBuilder();
        for (Upgrade u : this.UPGRADES) {
            if (!u.isOwned()) {
                unbought.append(u.getName())
                        .append(" | Cost: ")
                        .append(u.getCost())
                        .append(" | Multiplier: ")
                        .append(u.getMultiplier())
                        .append("\n");
            }
        }
        return unbought.toString();
    }

    public boolean upgradeUnbought(Upgrade u) {
        return !u.isOwned();
    }

    public void buyUpgrade(Upgrade u) {
        u.buy();
        updateMultiplier();
    }
}