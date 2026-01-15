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

    public int getNumRebirths() {
        return this.numRebirths;
    }

    public int getMaxDigits() {
        if (this.maxCookies < 1) {
            return 1;
        } else {
            return (int) (Math.log10(this.maxCookies)) + 1;
        }
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
