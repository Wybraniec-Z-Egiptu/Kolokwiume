import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class City {
    private String stolica;
    private int strefaLetnia;
    private double szerokosc;
    private double dlugosc;



    public City(String stolica, int strefaLetnia, double szerokosc, double dlugosc){
        this.stolica = stolica;
        this.strefaLetnia = strefaLetnia;
        this.szerokosc = szerokosc;
        this.dlugosc = dlugosc;

    }

    private static City parseLine(String line){
        String[] parts=line.trim().split(",",-1);
        String name=parts[0].trim();
        String szerText=parts[2].trim().replace("N", " ").replace("S", " ").trim(); //replace to tak ametoda znajdz i zamien
        String dlText=parts[3].trim().replace("E", " ").replace("W", " ").trim();
        int strefa=Integer.parseInt(parts[1].trim());
        double szerokosc=Double.parseDouble(szerText);
        double dlugosc=Double.parseDouble(dlText);

       return new City(name,strefa,szerokosc,dlugosc);
    }
public static Map<String,City> parseFile(String path) throws FileNotFoundException {
    Map<String, City>mapaDanych=new HashMap<>();
    try(Scanner scanner=new Scanner(new File(path))){
        if(scanner.hasNextLine()){
            scanner.nextLine();
        }
        while (scanner.hasNextLine()){
            String line=scanner.nextLine();
            if(!line.trim().isEmpty()){
                City p= parseLine(line);
                String klucz=p.getStolica();

                mapaDanych.put(klucz,p);
            }

        }
    }
    return mapaDanych;
}
    public LocalTime localMeanTime(LocalTime time) {
        // Obliczam, ile minut "słonecznych" dzieli miasto od południka zero (Londynu).
        // Każdy 1 stopień długości geograficznej to 4 minuty czasu.
        double totalMinutes = this.dlugosc * 4;

        // Przeliczam strefę czasową (np. +1, +2) z godzin na pełne minuty.
        // Robię to, żeby mieć tę samą jednostkę co wyżej (minuty).
        int zoneMiutes = this.strefaLetnia * 60;

        // Obliczam różnicę: o ile słońce nad miastem "wyprzedza" lub "zostaje w tyle"
        // w stosunku do tego, co pokazuje zegarek ustawiony na daną strefę.
        double roznica = totalMinutes - zoneMiutes;

        // Zamieniam wyliczoną różnicę minut na sekundy (bo Java lubi sekundy przy dodawaniu czasu).
        // (long) to takie "upchnięcie" wyniku do formatu liczby całkowitej bez ułamków.
        long secondtoAdd = (long) (roznica * 60);

        // Biorę czas z argumentu (np. z zegara) i przesuwam go o te wyliczone sekundy.
        // To jest ostateczny wynik: czas lokalny słoneczny (LMT).
        return time.plusSeconds(secondtoAdd);
    }

    public static int worstTimezoneFit(City c1, City c2) {
        // Obliczamy błąd (tzw. "rozjazd") dla obu miast
        double fit1 = Math.abs(c1.getDlugosc() * 4 - c1.getStrefaLetnia() * 60);
        double fit2 = Math.abs(c2.getDlugosc() * 4 - c2.getStrefaLetnia() * 60);

        // Sortowanie malejące: fit2 porównujemy do fit1
        return Double.compare(fit2, fit1);
    }
    @Override
    public String toString() {
        return "City{" +
                "stolica='" + stolica + '\'' +
                ", strefaLetnia=" + strefaLetnia +
                ", szerokosc=" + szerokosc +
                ", długosc=" + dlugosc +
                '}';
    }

    public String getStolica() {
        return stolica;
    }

    public int getStrefaLetnia() {
        return strefaLetnia;
    }

    public double getSzerokosc() {
        return szerokosc;
    }

    public double getDlugosc() {
        return dlugosc;
    }
}
