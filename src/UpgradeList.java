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

    public int getUpgradeIdx(String name) {
        int idx = -1;
        if (name == null || name.isEmpty()) {
            return idx;
        }
        for (int i = 0; i < this.UPGRADES.size(); i++) {
            Upgrade u = this.UPGRADES.get(i);
            if (name.equals(u.getName().toLowerCase())) {
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

    public boolean upgradeUnbought(Upgrade u) {
        return !u.isOwned();
    }

    public void buyUpgrade(Upgrade u) {
        u.buy();
        updateMultiplier();
    }
}