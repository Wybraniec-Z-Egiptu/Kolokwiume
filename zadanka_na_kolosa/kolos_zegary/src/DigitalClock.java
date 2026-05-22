public class DigitalClock extends Clock {

    public enum ClockType {H12, H24}//typ wyliczeniowy

    private ClockType mode;

    public DigitalClock(int h, int m, int s, ClockType mode,City city) {
        super(h, m, s,city);
        this.mode = mode;
    }

    @Override
    public String toString() {
        if (mode == ClockType.H24) {
            return super.toString(); //wywolanie metody z klasy nadrzednej

        } else {
            String suffix;
            if (h < 12) {
                suffix = "Am";
            } else {
                suffix = "PM";
            }


            int h12 = h; // Zakładamy, że godzina jest ok (dla zakresu 1-12)
            if (h == 0) {
                h12 = 12;  // Poprawka tylko dla północy
            } else if (h > 12) {
                h12 = h - 12; // Poprawka dla godzin popołudniowych
            }


            return String.format("%d:%02d:%02d %s", h12, m, s, suffix);
        }

    }
}

