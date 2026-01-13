public class Cookie {
    private int perClick;
    private boolean golden;

    public Cookie() {
        this.perClick = 1;
        this.golden = false;
    }

    public void toggleGolden() {
        this.golden = !this.golden;
    }

    public int click() {
        int cookieAmt = this.perClick;
        if (golden) {
            cookieAmt *= 7;
        }
        return cookieAmt;
    }
}
