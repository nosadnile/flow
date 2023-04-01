package net.nosadnile.flow.api.time;

public enum TimeUnit {
    MILLISECONDS("ms", "MILLIS"),
    SECONDS("s", "SECS"),
    MINUTES("m", "MINS"),
    HOURS("h", "HRS"),
    DAYS("d", "DAYS"),
    WEEKS("w", "WEEKS"),
    MONTHS("mo", "MNTHS");

    public final String small;
    public final String big;

    TimeUnit(String small, String big) {
        this.small = small;
        this.big = big;
    }

    public static TimeUnit deserialize(String val) throws Exception {
        if (TimeUnit.MILLISECONDS.matches(val)) {
            return TimeUnit.MILLISECONDS;
        }

        if (TimeUnit.SECONDS.matches(val)) {
            return TimeUnit.SECONDS;
        }

        if (TimeUnit.MINUTES.matches(val)) {
            return TimeUnit.MINUTES;
        }

        if (TimeUnit.HOURS.matches(val)) {
            return TimeUnit.HOURS;
        }

        if (TimeUnit.DAYS.matches(val)) {
            return TimeUnit.DAYS;
        }

        if (TimeUnit.WEEKS.matches(val)) {
            return TimeUnit.WEEKS;
        }

        if (TimeUnit.MONTHS.matches(val)) {
            return TimeUnit.MONTHS;
        }

        throw new Exception("Unknown time unit!");
    }

    public boolean matches(String val) {
        return val.equalsIgnoreCase(this.small) || val.equalsIgnoreCase(this.big);
    }

    public String serialize() {
        return this.small;
    }
}
