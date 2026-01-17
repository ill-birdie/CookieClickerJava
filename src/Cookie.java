public class Cookie {
    private int timesClicked;

    public Cookie() {
        this.timesClicked = 0;
    }

    public int getTimesClicked() {
        return this.timesClicked;
    }

    public boolean isFirstClick() {
        return this.timesClicked == 0;
    }

    public boolean isHundredthClick() {
        return this.timesClicked % 100 == 0 && !isFirstClick();
    }

    public boolean isLuckyClick() {
        return (int) (Math.random() * 1000) + 1 == 1000;
    }

    public int click() {
        int cookieAmt = 1;
        double multiplier = 1;
        if (isFirstClick()) {
            multiplier *= 1000;
            System.out.println("Your first click! x1000 cookies!");
        }
        if (isHundredthClick()) {
            multiplier *= 100;
            System.out.println("Special click! x100 cookies!");
        }
        if (isLuckyClick()) {
            multiplier *= 1000;
            System.out.println("Lucky click! x1000 cookies!");
        }
        this.timesClicked++;
        if (multiplier > 1) {
            System.out.println("Awesome! Total multiplier: " + (int) multiplier + "x");
        }
        return (int) (cookieAmt * multiplier);
    }
}
