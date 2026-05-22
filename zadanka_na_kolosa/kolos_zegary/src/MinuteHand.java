import java.time.LocalTime;
import java.util.Locale;

public class MinuteHand extends  ClockHand{
    private double angle; //przechowujacy kat wskazowki w stopnaich

    private final double lenght=75;

    public double getAngle() {
        return angle;
    }

    public double getLenght() {
        return lenght;
    }

    @Override
    public void setTime(LocalTime time) {
        int minuty=time.getMinute();
        this.angle=minuty*6;
    }

    @Override
    public String toSvg() {
        double radiany=Math.toRadians(this.angle);
        double x2=100+lenght*Math.sin(radiany);
        double y2=100-lenght*Math.cos(radiany);
        return String.format(Locale.ENGLISH,"<line x1=\"100\" y1=\"100\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"red\" stroke-width=\"2.5\" />",x2,y2);
    }
}

