import java.util.Locale;

public class HotTemperatureBar extends ChartBar {
    private int dlugosc;

    @Override
    public void setValue(Double temperatura) {
    int zaokraglanie=(int)temperatura.doubleValue();
    this.dlugosc=zaokraglanie;
    }

    @Override
    public String toTxtChart() {
       String znak="";

       for(int i=0;i<dlugosc;i++){
           znak+="#";
       }

        return znak;
    }
}
