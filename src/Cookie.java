public class Cookie {
    private boolean golden;

    public Cookie() {
        this.golden = false;
    }

    public void toggleGolden() {
        this.golden = !this.golden;
    }

    public int click(Player target) {
        int cookieAmt = (int) Math.pow(2, target.getNumRebirths());
        if (golden) {
            cookieAmt *= 7;
        }
        return cookieAmt;
    }
}
