public class Upgrade {
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

    public void removeOwnership() {
        this.owned = false;
    }
}
