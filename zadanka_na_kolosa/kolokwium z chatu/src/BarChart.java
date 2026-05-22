import javax.swing.plaf.synth.SynthLabelUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BarChart extends WeatherStation{
    private final List<ChartBar>listaSlupkow=new ArrayList<>();

    public BarChart(double temp, City city, List<ChartBar> listaSlupkow) {
        super(temp, city);
    }

    public  void toTxtChart(String path, Map<String, City>date) throws FileNotFoundException {
       PrintWriter writer = new PrintWriter(path); //pryzjmuje sciezke do pliku

       /* for(City city : date.values()){
            int iloscGwiazdek=(int)city.getTemp();

            if(iloscGwiazdek<0){
                iloscGwiazdek=0;
            }
            String gwiazdki="";

            for(int i=0;i<iloscGwiazdek;i++){
                gwiazdki+="*";
            }
            writer.println(city.getName()+" "+city.getTemp()+" "+gwiazdki);
        }

      */ writer.close();

        for(City city:date.values()){
            double temp=city.getTemp();

            ChartBar slupek;

            if(temp<10.0){
                slupek=new ColdTemperatureBar();
            }else if(temp>=22.0){
                slupek=new NormalTemperatureBar();
            }else{
                slupek=new HotTemperatureBar();
            }
            slupek.setValue(temp);

            String linijka=String.format(Locale.ENGLISH,"%-15s {%5.1f] : %s", city.getName(), temp, slupek,temp,slupek.toTxtChart());
            writer.println(linijka);
        }
        writer.close();
    }

}

// OBJAŚNIENIE SZABLONU FORMATOWANIA:
// "%-15s [%5.1f°C] : %s"
//
// %-15s   -> Miejsce na tekst (String - nazwa miasta).
//            Liczba 15 rezerwuje równe 15 znaków szerokości,
//            a minus (-) wyrównuje tekst do lewej (uzupełnia spacjami z prawej).
//
// [       -> Zwykły znak nawiasu kwadratowego, wypisze się bez zmian.
//
// %5.1f   -> Miejsce na liczbę zmiennoprzecinkową (double - temperatura).
//            Liczba 5 oznacza, że całe pole ma mieć 5 znaków szerokości.
//            Kropka i 1 (.1) wymusza dokładnie JEDNO miejsce po przecinku.
//
// °C]     -> Tekst wyświetlany dosłownie na końcu pola temperatury.
//
//  :      -> Spacja, dwukropek, spacja – dla estetycznego oddzielenia.
//
// %s      -> Miejsce na drugi tekst (String), czyli gotowy słupek (-, + lub #)
//            zwrócony z metody toTxtChart().
