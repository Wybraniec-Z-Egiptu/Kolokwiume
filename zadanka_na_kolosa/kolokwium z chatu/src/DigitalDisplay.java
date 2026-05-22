import java.util.Locale;

public class DigitalDisplay extends WeatherStation {
    private temperaturType mode;

    public DigitalDisplay(double temp, City city,temperaturType mode) {
        super(temp, city);
        this.mode=mode;
    }


    public enum temperaturType{
            Celciusz, Fahrenheit;
        }

    @Override
    public String toString() {
     if(mode==temperaturType.Celciusz){
         return super.toString(); // wywoalnie metody toString klasy nadrzednej
     }else {
    double wynik;

    wynik=getTemp()*1.8+32;
         return String.format(Locale.ENGLISH,"%.1f\u00B0F",wynik);

     }
    }
}
