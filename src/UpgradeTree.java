import java.util.ArrayList;
import java.util.HashMap;

record Attributes(boolean isOwned, String type, double multiplier, int cost) { }

public class UpgradeTree {
    private HashMap<String, Attributes> upgrades;
    private double totalMultiplier;

    public UpgradeTree() {
        this.upgrades = new HashMap<>();
        this.upgrades.put("First Steps", new Attributes(false, "Base", 2.0, 0));
        this.upgrades.put("Triple digits!", new Attributes(false, "Base", 1.5, 100));
        this.totalMultiplier = 1;
    }

    public void buyUpgrade(String name) {
        if (this.upgrades.containsKey(name)) {
            this.upgrades.put("First Steps", new Attributes(true, "Base", 2.0, 0));
        }
    }

    public void updateMultiplier() {
        double newMultiplier = 1;
        for (String upgrade : this.upgrades.keySet()) {
            Attributes attributes = this.upgrades.get(upgrade);
             if (attributes.isOwned() && attributes.type().equals("Base")) {
                 newMultiplier *= attributes.multiplier();
             }
        }
        this.totalMultiplier = newMultiplier;
    }

    public double getTotalMultiplier() {
        return this.totalMultiplier;
    }
}