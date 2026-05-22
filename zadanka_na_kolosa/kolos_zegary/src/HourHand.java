import java.time.LocalTime;
import java.util.Locale;

public class HourHand extends ClockHand {
    private double angle; //przechowujacy kat wskazowki w stopnaich

    private final double lenght=55;

    public double getAngle() {
        return angle;
    }

    public double getLenght() {
        return lenght;
    }



    @Override
    public void setTime(LocalTime time) {
        int godziny=time.getHour();
        this.angle=(godziny%12)*30;
    }

    @Override
    public String toSvg() {
        double radiany=Math.toRadians(this.angle);
        double x2=100+lenght*Math.sin(radiany);
        double y2=100-lenght*Math.cos(radiany);
        return String.format(Locale.ENGLISH," <line x1=\"100\" y1=\"100\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"black\" stroke-width=\"4\" />",x2,y2);
    }
}
