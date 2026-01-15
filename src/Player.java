public class Player {
    private int numCookies;
    private int maxCookies;
    private int numRebirths;
    private final long BASE_REBIRTH_AMT = (int) Math.pow(10, 6);
    private UpgradeTree upgrades;

    public Player() {
        this.numCookies = 0;
        this.maxCookies = this.numCookies;
        this.numRebirths = 0;
        this.upgrades = new UpgradeTree();
    }

    public int getNumCookies() {
        return this.numCookies;
    }

    public int getMaxCookies() {
        return this.maxCookies;
    }

    public int getNumRebirths() {
        return this.numRebirths;
    }

    public void incrementCookies(int cookies) {
        this.numCookies += cookies;
        this.maxCookies = Math.max(this.numCookies, this.maxCookies);
    }

    public long requiredRebirthAmt() {
        int multiplier = (int) Math.pow(10, this.numRebirths);
        return BASE_REBIRTH_AMT * multiplier;
    }

    public boolean canRebirth() {
        return this.numCookies >= requiredRebirthAmt();
    }

    public void rebirth() {
        this.numCookies = 0;
        this.maxCookies = this.numCookies;
        this.numRebirths++;
    }
}
