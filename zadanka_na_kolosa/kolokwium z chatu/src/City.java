import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class City {
    private String name;
    private double temp;
    private Integer wysokosc;

    public City(String name, double temp, Integer wysokosc) {
        this.name = name;
        this.temp = temp;
        this.wysokosc = wysokosc;
    }

    public String getName() {
        return name;
    }

    public double getTemp() {
        return temp;
    }

    public Integer getWysokosc() {
        return wysokosc;
    }

    private static City parseLinie(String linie){
    String[] part=linie.trim().split(";");

    double temperatura=Double.parseDouble(part[1].trim());
    int wysokosc=Integer.parseInt(part[2].trim());

    return new City(part[0],temperatura,wysokosc);
    }

    public static Map<String,City> parseFile(String path) throws FileNotFoundException {
       Map<String,City>mapDate=new LinkedHashMap<>(); //Linked od razu porzadkuje tak jak jesty w pliku
        try(Scanner scanner=new Scanner(new File(path))){
            if(scanner.hasNextLine()){
                scanner.nextLine();
            }
            while (scanner.hasNextLine()){
                String line=scanner.nextLine();
                if(!line.trim().isEmpty()){
                    City p=parseLinie(line);
                    String key=p.name;
                    mapDate.put(key, p);
                }
            }
        }

        return mapDate;
    }
    public double localMeanTemperature(Double tempBazowa){

        int wysokosc=this.wysokosc;
        double tempMiejscowa =tempBazowa- (wysokosc/150.0);


        return  tempMiejscowa;
    }

    public static int worstAltidudeFit(City c1, City c2) {
        double baza1= Math.abs(c1.getTemp()-c1.localMeanTemperature(c1.getTemp()));
        double baza2= Math.abs(c2.getTemp()-c2.localMeanTemperature(c2.getTemp()));

                return Double.compare(baza2,baza1);

    }
}
