import java.time.LocalTime;

public abstract class Clock {
    protected int h,m,s;
    protected DigitalClock.ClockType type;
    private City city;

    public Clock(int h, int m, int s, City city) {
        setTime(h,m,s);
        this.city=city;
    }

    public void setCurrentTime(){
        LocalTime czas=LocalTime.now();

       this.h= czas.getHour();
       this.m=czas.getMinute();
       this.s=czas.getSecond();
    }

public void setTime(int godzina,int minuta,int sekunda){
if(godzina>=24 || godzina<0){
    throw new IllegalArgumentException("Godzina nie miesci sie w zakresie");
}
    if(minuta>=60 || minuta<0) {
        throw new IllegalArgumentException("Minuta nie miesci sie w zakresie");
    }
    if(sekunda>=60 || sekunda<0) {
        throw new IllegalArgumentException("Sekunda nie miesci sie w zakresie");
    }
this.h=godzina;
this.m=minuta;
this.s=sekunda;

}


    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d",h,m,s);
    }

    public int getH() {
        return h;
    }

    public int getM() {
        return m;
    }

    public int getS() {
        return s;
    }
}
