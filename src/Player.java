public class Player {
    private int numCookies;
    private int maxCookies;
    private int numRebirths;
    private final int BASE_REBIRTH_AMT = (int) Math.pow(10, 6);

    public Player() {
        this.numCookies = 0;
        this.maxCookies = this.numCookies;
        this.numRebirths = 0;
    }

    public int getNumCookies() {
        return this.numCookies;
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

    public boolean canRebirth() {
        return this.numCookies >= Math.pow(this.numRebirths, this.BASE_REBIRTH_AMT);
    }

    public void rebirth() {
        this.numCookies = 0;
        this.maxCookies = this.numCookies;
        this.numRebirths++;
    }
}
