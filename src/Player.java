public class Player {
    private int numCookies;
    private int numRebirths;

    public Player() {
        this.numCookies = 0;
        this.numRebirths = 0;
    }

    public void incrementCookies(int cookies) {
        this.numCookies += cookies;
    }

    public void rebirth() {
        this.numCookies = 0;
        this.numRebirths++;
    }
}
