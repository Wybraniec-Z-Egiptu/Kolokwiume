import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Person implements Comparable<Person>{   // java ma wbudowany interfejs deklarujemy go implements comparable<Person>,interfejs ma wbudowane gettery settery i konstruktor
    private String imie;
    private String nazwisko;
    private LocalDate dataUrodzin;
    private LocalDate dataSmierci;

    public Person(String imie, String nazwisko, LocalDate dataUrodzin, LocalDate dataSmierci) {

        if(dataSmierci!=null &&dataSmierci.isBefore(dataUrodzin)){
            throw new NegativelifespanException(" bład "+imie+" "+nazwisko+" data smierci "+dataSmierci+" data uro "+dataUrodzin);
        }
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzin = dataUrodzin;
        this.dataSmierci = dataSmierci;
    }

    private Set<Person>zbiorDzieci=new HashSet<>();

    public boolean adopt(Person dziecko, boolean force){   //boolen sam z siebie zwraca true jesli dodano obiekt a jesli nie false
        if(!force) {
            long lata = ChronoUnit.YEARS.between(this.dataUrodzin, dziecko.getDataUrodzin()); //oblicza wiek rodzicow ile lat mial w momenci eurodzic dziecka bo go nie mamy podanego w pliku
            boolean zaMlody = lata < 15;
            boolean nieZyje = this.dataSmierci != null && this.dataSmierci.isBefore(dziecko.getDataUrodzin());

            if (zaMlody || nieZyje) {
                throw new ParentingAgeException(this.imie);
            }
        }
            return zbiorDzieci.add(dziecko);
        }


    public Person getYoungChild(){
        Person najmlodsze=null;  // Inicjalizujemy zmienną pomocniczą; domyślnie null (jeśli nie będzie dzieci)

        for (Person person : zbiorDzieci) {  //przechodizmy petla po zbiorze Dzieci xd

            // Sprawdzamy: jeśli to pierwsze dziecko (najmlodsze jest jeszcze null)
            // LUB jeśli data urodzenia obecnego dziecka jest późniejsza (isAfter) niż dotychczasowego "lidera"
            if(najmlodsze==null || person.compareTo(najmlodsze)>0){  //isAfter() (odpowiednik >), isBefore() (odpowiednik <)

                najmlodsze=person; //Mamy naajmlodsze dziecko tu zapisane
            }
        }
        // Jeśli warunek spełniony, obecne dziecko staje się nowym najmłodszym
        return najmlodsze;
    }

    public List<Person>children(){
    List<Person> listaDzieci=new ArrayList<>(zbiorDzieci); // Przenoszeniue danych ze zbioru do listy

        //2. Sortujemy (korzysta z Twojego Comparable)
        Collections.sort(listaDzieci);

        //Zwracamy gotową, posortowaną listę
        return listaDzieci;

        }

    public static Person fromCsvLine(String linie) {
        // Dzielimy linię przecinkami. Parametr -1 sprawia, że puste pola na końcu (np. po dacie śmierci) nie zostaną ucięte.
        String[] parts = linie.trim().split(",", -1);

        //  Rozdzielamy pierwszą kolumnę (imię i nazwisko) spacją.
        // Limit 2 oznacza: podziel na maksymalnie dwa kawałki (imię i reszta).
        String[] partsName = parts[0].trim().split(" ", 2);

        // Definiujemy wzór daty, który występuje w pliku (dzień.miesiąc.rok).
        // Musimy go użyć, bo Java domyślnie oczekuje myślników (RRRR-MM-DD).
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Parsujemy datę urodzenia z drugiej kolumny (indeks 1) przy użyciu naszego formatu.
        LocalDate datauro = LocalDate.parse(parts[1], format);

        //Przygotowujemy zmienną na datę śmierci. Domyślnie ustawiamy null (zakładamy, że osoba żyje).
        LocalDate dataSmierci = null;

        // Sprawdzamy bezpieczeństwo: czy w tablicy jest kolumna z datą śmierci (długość > 2) chodzi o index bo od 0 liczymy
        // oraz czy ta kolumna nie jest pusta (!isEmpty).
        if (parts.length > 2 && !parts[2].isEmpty()) {
            // Jeśli pole zawiera datę, parsujemy ją i przypisujemy do zmiennej.
            dataSmierci = LocalDate.parse(parts[2], format);
        }

        // Tworzymy i zwracamy nowy obiekt Person.
        // Argumenty muszą iść w kolejności: imię, nazwisko, data urodzenia, data śmierci.
        return new Person(partsName[0], partsName[1], datauro, dataSmierci);
    }

    public static List<Person> fromCsv(String path) throws FileNotFoundException {
        List<Person> listadzieci = new ArrayList<>();
        Set<String> listaOsob = new HashSet<>(); //tworzymy zbior ktory bedzie trzymal dane osobowe unikalne i bede sprawdzac czy ich nie ma juz
        Map<String, Person> mapaOsob = new HashMap<>(); //klucz String i wartosc Person czyli ludzie

        // KROK 1: Wczytujemy wszystkich ludzi do mapy (Twoje pierwsze przejście)
        try (Scanner scanner = new Scanner(new File(path))) {
            // Pomijamy nagłówek, jeśli plik nie jest pusty
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Sprawdzamy czy linia nie jest pusta, żeby uniknąć błędów
                if (!line.trim().isEmpty()) {
                    Person p = fromCsvLine(line); //Zmieniamy surowy tekst z linii na gotowy obiekt klasy Person
                    String klucz = p.getImie() + " " + p.getNazwisko(); //zmienna klucz dla imienia i nazwiska

                    if (listaOsob.contains(klucz)) { // pyutam zbior czy ma juz w sobie taki tekst czyli to moje imie i nazwisko
                        throw new AmbiguousPersonException("Imie " + klucz + "istnieje"); //rzuca wyjatek jesli osoba juz istnieje
                    }

                    mapaOsob.put(klucz, p); //klucz bo co i potem p bo kogo
                    listaOsob.add(klucz); // jelsi to nowa osoba dodaje jej dane do zbioru pamieci
                    listadzieci.add(p); //wrzucam gotowy obiekt do glownej listy wynikowej
                }
            }
        } // Koniec pierwszego czytania

        // KROK 2: Łączymy rodziny (Twoje drugie przejście)
        try (Scanner scanner1 = new Scanner(new File(path))) { //otwieram plik jeszcze raz bo po 1 przejsciu kursor jest na samym koncu i musimy wrocic na start
            scanner1.nextLine(); // omijamy nagłówek
            while (scanner1.hasNextLine()) {
                String line2 = scanner1.nextLine();
                String[] parts2 = line2.split(",", -1);

                String kluczDziecka = parts2[0].trim(); // -> wyciagam imie i nazwisko z 1 kolumny
                Person dziecko = mapaOsob.get(kluczDziecka);

                for (int i = 3; i <= 4; ++i) { //sprawdza czt kolummy o indexach 3 i 4 sa tam wpisani rodzice
                    if (parts2.length > i && !parts2[i].isEmpty()) { //czy kolumna w ogole istnieje i czy nie jest pusta
                        String kluczRodzica = parts2[i].trim();
                        Person rodzic = mapaOsob.get(kluczRodzica); //pytam mape czy mam obiekt o takim imieniu jesli byl wczesniej wczytay mapa go zwraca

                        if (rodzic != null) {
                            try {
                                rodzic.adopt(dziecko, false); //jesli rodzic nie jest pusty adoptuje dziecko (false = sprawdzaj wiek)
                            } catch (ParentingAgeException e) {
                                System.out.println(e.getMessage() + " czy napewno dodat Y/N?");
                                Scanner klawiatura = new Scanner(System.in);
                                String wybor = klawiatura.nextLine();

                                if (wybor.equalsIgnoreCase("Y")) {
                                    rodzic.adopt(dziecko, true); // wymuszamy dodanie (true = pomin wiek)
                                }
                            }
                        }
                    }
                }
            }
        }
        // Scanner zamknie się sam tutaj (nawet przy błędzie)
        return listadzieci;
    }

    @Override
    public String toString() {
        return "Person{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", dataUrodzin=" + dataUrodzin +
                ", dataSmierci=" + dataSmierci +
                '}';
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public LocalDate getDataUrodzin() {
        return dataUrodzin;
    }

    public LocalDate getDataSmierci() {
        return dataSmierci;
    }
    public Set<Person> getChildren() {
        return zbiorDzieci;
    }



    @Override
    public int compareTo(Person other) {

        //"Porównaj moją datę urodzenia z datą urodzenia tej drugiej osoby i zwróć wynik w formie liczby".
        return this.dataUrodzin.compareTo(other.dataUrodzin);
    }


}
