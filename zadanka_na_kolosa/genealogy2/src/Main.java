import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        //  Tworzymy listę dynamiczną (ArrayList), która będzie przechowywać obiekty klasy Person
        //List<Person> people = new ArrayList<>();
        // Wywołujemy sortowanie. Dzięki 'implements Comparable' w klasie Person,
        // Java wie, że ma sortować według daty urodzenia (this.dataUrodzin.compareTo)->to mamy w Pearson .
        //Collections.sort(people);
        //Collections.reverse(people); // Odwraca Najmlodszy najstaarszy
        //System.out.println("Lista osób posortowana od najstarszej do najmłodszej:");
        // Przechodzimy pętlą for-each przez posortowaną listę i wypisujemy dane
        //for (Person person : people) {
           // // Łączymy dane za pomocą operatora '+', bo println przyjmuje jeden ciąg znaków
            //System.out.println(person.getImie() + " - " + person.getDataUrodzin());
        // Opcjonalny test adopcji (żeby sprawdzić Twoją metodę logiczną)
       // Person cezary = people.get(0); // Najstarszy (Cezary)
        //Person marlena = people.get(2); // Najmłodsza (Marlena)
       // cezary.adopt(marlena);
       // System.out.println("\nNajmłodsze dziecko Cezarego: " + cezary.getYoungChild().getImie());


            //Wypisuje ta liste po prostu cala
        List<Person>lista=Person.fromCsv("genealogy2/src/family.csv");
        for (Person person : lista) {


            if(person.getImie().equals("Ewa") && person.getNazwisko().equals("Kowalska")){
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("dzieci ewy");

                for (Person child : person.children()) {
                    System.out.println(child.getImie()+" "+child.getDataUrodzin());
                }

                Person najmlodsze=person.getYoungChild();
                System.out.println("--------------------------------------------------------------------------");
                if(najmlodsze!=null){
                    System.out.println("najmlodsze dziecko to "+ najmlodsze.getImie());

                }
            }
        }



    }
}