public class Cookie {
    private int timesClicked;

    public Cookie() {
        this.timesClicked = 0;
    }

    public int getTimesClicked() {
        return this.timesClicked;
    }

    public boolean isSpecialClick() {
        return this.timesClicked % 100 == 0;
    }

    public boolean isLuckyClick() {
        return (int) (Math.random() * 100) + 1 == 100;
    }

    public int click(Player target) {
        int cookieAmt = (int) Math.pow(2, target.getNumRebirths());
        double multiplier = 1;
        if (isSpecialClick()) {
            multiplier *= 100;
            System.out.println("Special click! x100 cookies!");
        }
        if (isLuckyClick()) {
            multiplier *= 50;
            System.out.println("Lucky click! x50 cookies!");
        }
        this.timesClicked++;
        if (multiplier > 1) {
            System.out.println("Awesome! Total multiplier: " + multiplier + "x");
        }
        return (int) (cookieAmt * multiplier);
    }
}
