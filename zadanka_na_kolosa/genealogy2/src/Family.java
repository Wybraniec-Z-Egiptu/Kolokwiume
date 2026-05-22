import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Family {
    Map<String, Person>mapaRodziny=new HashMap<>();

   // public void add(Person p){ //- add(), która przyjmie osobę i doda ją do rodziny,
        //String klucz=p.getImie()+" "+p.getNazwisko();
       // mapaRodziny.put(klucz,p);
   // }

    // Wielokropek (...) oznacza, że możemy podać dowolną liczbę osób
    public void add(Person... people){ // wariadyczna lista osob

        // Java traktuje 'people' jak tablicę, więc przechodzimy przez nią pętlą for-each
        for (Person person : people) {
            String klucz=person.getImie()+" "+person.getNazwisko();
            mapaRodziny.put(klucz, person);
        }

    }

    public Person get(String klucz){ // get(), która przyjmie klucz i zwróci odpowiadający mu obiekt.
    return mapaRodziny.get(klucz);
    }
}
