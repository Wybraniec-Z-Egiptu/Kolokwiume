import java.time.LocalTime;
import java.util.Locale;

public class SecondHand extends ClockHand{
    private double angle; //przechowujacy kat wskazowki w stopnaich

    private final double lenght=85; //dl wskazowki sekundowej


    public double getAngle() {
        return angle;
    }

    public double getLenght() {
        return lenght;
    }

    @Override
    public void setTime(LocalTime time) {
    int sekundy=time.getSecond(); //pobierze skundy
        this.angle=sekundy*6;
    }

    @Override
    public String toSvg() {
        double rad=Math.toRadians(this.angle);

        double x2=100+lenght *Math.sin(rad);
        double y2=100-lenght*Math.cos(rad);
        return String.format(Locale.ENGLISH,"  <line x1=\"100\" y1=\"100\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"blue\" stroke-width=\"1\" />",x2,y2);
    }
}
