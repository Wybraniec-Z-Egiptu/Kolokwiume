import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

       //City city=City.parseLine("Abu Dhabi,4,24.4539 N, 54.3773 E");
       // System.out.println(city);
        Map<String,City>dane=City.parseFile("kolos_zegary/src/strefy.csv");
        List<City>lista=new ArrayList<>(dane.values());

        System.out.println("Lista wszystkich miast");

        for(City c: dane.values()){
            System.out.println(c);
        }
        System.out.println("-------------------------------------------------------");
        lista.sort(City::worstTimezoneFit);
        for (City city : lista) {
            double f = Math.abs(city.getDlugosc() * 4 - city.getStrefaLetnia() * 60);
            System.out.println(String.format("%.2f | %s", f, city.getStolica()));
        }

        City city=dane.get("Lublin"); //to jest city ktore jest w naszym csv
        AnalogClock clock=new AnalogClock(2,3,45,city);
        clock.toSvg("zegar.svg");


    }


}