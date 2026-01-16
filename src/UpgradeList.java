import java.util.ArrayList;

public class UpgradeList {
    private final ArrayList<Upgrade> UPGRADES;
    private int multiplier;

    public UpgradeList() {
        this.UPGRADES = new ArrayList<>();
        this.UPGRADES.add(new Upgrade("First Steps", "Base", 2, 0));
        this.UPGRADES.add(new Upgrade("Quadruple Digits!", "Base", 4, (int) Math.pow(10, 3)));
        this.UPGRADES.add(new Upgrade("Baby steps", "Base", 2, (int) Math.pow(10, 4)));
        this.UPGRADES.add(new Upgrade("It just keeps growing", "Base", 2, (int) Math.pow(10, 5)));
        this.UPGRADES.add(new Upgrade("Basically a million", "Base", 2, 999999));
        this.UPGRADES.add(new Upgrade("Second Life", "Base", 2, (int) Math.pow(10, 6)));
        this.UPGRADES.add(new Upgrade("Now do it again", "Base", 2, (int) Math.pow(10, 7)));
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