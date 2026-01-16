import java.util.ArrayList;
import java.util.Arrays;

public class Upgrades {
    private final ArrayList<Upgrade> UPGRADES;
    private int multiplier;

    public Upgrades() {
        this.UPGRADES = new ArrayList<>(Arrays.asList(
            new Upgrade("First Steps", "Base", 2, 0),
            new Upgrade("Triple digits", "Base", 3, 100),
            new Upgrade("Quadruple Digits!", "Base", 4, (int) Math.pow(10, 3)),
            new Upgrade("Greed sets in", "Base", 2, (int) Math.pow(10, 4)),
            new Upgrade("It just keeps growing", "Base", 2, (int) Math.pow(10, 5)),
            new Upgrade("The Long Haul", "Base", 5, 250000),
            new Upgrade("Second Life", "Base", 2, (int) Math.pow(10, 6)),
            new Upgrade("Now do it again", "Base", 2, (int) Math.pow(10, 7))
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