import java.util.ArrayList;

public class UpgradeList {
    private final ArrayList<Upgrade> UPGRADES;
    private int multiplier;

    public UpgradeList() {
        this.UPGRADES = new ArrayList<>();
        this.UPGRADES.add(new Upgrade("First Steps", "Base", 10, 0));
        this.UPGRADES.add(new Upgrade("Quadruple Digits!", "Base", 4, 1000));
        this.UPGRADES.add(new Upgrade("Baby steps", "Base", 2, 10000));
        this.UPGRADES.add(new Upgrade("It just keeps growing", "Base", 10, 100000));
        this.UPGRADES.add(new Upgrade("Basically a million", "Base", 2, 999999));
        this.UPGRADES.add(new Upgrade("Second Life", "Base", 2, 1000000));
        this.multiplier = 1;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public void updateMultiplier() {
        int newMulti = 1;
        for (Upgrade u : this.UPGRADES) {
            if (u.isOwned() && u.getType().equals("Base")) {
                newMulti *= u.getMultiplier();
            }
        }
        this.multiplier = newMulti;
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

    public boolean upgradeUnbought(String target) {
        for (Upgrade u : UPGRADES) {
            if (!u.isOwned() && u.getName().toLowerCase().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public void buyUpgrade(String target) {
        for (Upgrade u : this.UPGRADES) {
            if (u.getName().toLowerCase().equals(target)) {
                u.buy();
                break;
            }
        }
        updateMultiplier();
    }
}

class Upgrade {
    private final String name;
    private final String type;
    private final int multiplier;
    private final int cost;
    private boolean owned;

    public Upgrade(String name, String type, int multiplier, int cost) {
        this.name = name;
        this.type = type;
        this.multiplier = multiplier;
        this.cost = cost;
        this.owned = false;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getCost() {
        return cost;
    }

    public boolean isOwned() {
        return owned;
    }

    public void buy() {
        this.owned = true;
    }
}