import java.io.FileNotFoundException;
import java.util.*;



public class Main {
    public static void main(String [] args) throws FileNotFoundException {
        Map<String ,City>date=City.parseFile("src/dane.csv");
        List<City> lista=new ArrayList<>(date.values());
        for(City dane:date.values()){
            System.out.println(dane.getName()+" "+dane.getTemp()+" "+dane.getWysokosc());
            DigitalDisplay digitalDisplay=new DigitalDisplay(dane.getTemp(), dane,DigitalDisplay.temperaturType.Celciusz);

            System.out.println(digitalDisplay);
        }

        lista.sort(Comparator.comparing(city -> city.getTemp())); // .reversed() owrocony cyzli sortuje od najwiekszego do najmniejszego

        for (City city : lista) {
            double f=Math.abs(city.getTemp()-city.localMeanTemperature(city.getTemp()));
            System.out.println(String.format(Locale.ENGLISH,"Różnica: %.2f, Wysokość: %d, Miasto: %s",f,city.getWysokosc(),city.getName()));

        }
        //Zad 1 wypisz miasta z temp powyzej 15 stopni
        System.out.println("temp powyzej 15 stopni");

        date.values().stream()
        .filter(city -> city.getTemp() >15.0)
                .forEach(city -> System.out.println(city.getName()+" "+city.getTemp()));

       // Zad 2 Wypisz same nazwy wszystkich miast, ale zmienione na WIELKIE LITERY.

        System.out.println("miasta zamienione na wielkie litery");
        date.values().stream()
                .map(city -> city.getName().toUpperCase())
                        .forEach(nazwa -> System.out.println(nazwa));

        //zad 3 Policz za pomocą strumienia, ile dokładnie miast znajduje się w Twojej bazie (mapie)
        System.out.println("Ile miast jest w bazie");
        long ilosc= date.values().stream().count();
        System.out.println(ilosc);

        //Zad 4 Posortuj miasta od najzimniejszego do najcieplejszego i je wypisz

        System.out.println("Miasta posortowane od najzimniejszego");
        date.values().stream()
                .sorted(Comparator.comparing(city -> city.getTemp()))
                .forEach(city -> System.out.println(city.getName()+" "+city.getTemp()));
    }


}

/* Filtorwanie miast i wyciaganie temp powyzej 15 stopni
List<City> ciepleMiasta = new ArrayList<>();
for (City city : date.values()) {
    if (city.getTemp() > 15.0) {
        ciepleMiasta.add(city);
    }
}
for (City city : ciepleMiasta) {
    System.out.println(city.getName());
}
 */