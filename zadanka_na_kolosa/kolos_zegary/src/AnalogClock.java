import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Klasa AnalogClock generująca zegar w formacie SVG.
 * Dziedziczy po klasie Clock, co daje dostęp do danych o czasie i mieście.
 */
public class AnalogClock extends Clock {
private  final List<ClockHand>listawskazowek=new ArrayList<>();



    public AnalogClock(int h, int m, int s, City city) {
        super(h, m, s, city);
        listawskazowek.add(new HourHand());
        listawskazowek.add(new MinuteHand());
        listawskazowek.add(new SecondHand());
    }

    public void toSvg  (String path) {
        // Używamy try-with-resources dla automatycznego zamknięcia pliku.
        try (PrintWriter writer = new PrintWriter(path)) {

            // 1. Nagłówek SVG - definiujemy obszar rysowania 200x200 jednostek.
            //writer.println(" <circle cx=\"100\" cy=\"100\" r=\"95\" ... />"); jak chce kolo
            writer.println("<svg width=\"200\" height=\"200\" viewBox=\"0 0 200 200\" xmlns=\"http://www.w3.org/2000/svg\">");

            // 2. Kwadratowa tarcza zegara:
            // x, y: lewy górny róg (zaczynamy od 5, żeby mieć margines).
            // width, height: ustawiamy na 190, aby tarcza była wycentrowana (5 + 190 + 5 = 200).
            // rx: opcjonalnie możesz dodać rx=\"10\", aby zaokrąglić rogi.
            writer.println("  <rect x=\"5\" y=\"5\" width=\"190\" height=\"190\" stroke=\"black\" stroke-width=\"3\" fill=\"white\" />");

            // 3. Pętla rysująca 12 indeksów godzinowych (kreski ułożone w okręgu):
            for (int i = 0; i < 12; i++) {
                // Co 30 stopni (360/12) wyliczamy pozycję kreski.
                double angle = Math.toRadians(i * 30);

                // Start kreski (odległość 80 od środka 100,100).
                double x1 = 100 + 80 * Math.sin(angle);
                double y1 = 100 - 80 * Math.cos(angle);

                // Koniec kreski (odległość 90 od środka).
                double x2 = 100 + 90 * Math.sin(angle);
                double y2 = 100 - 90 * Math.cos(angle);


                // Zapisujemy linię do pliku.
                writer.println(String.format(
                        "  <line x1=\"%.2f\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"black\" stroke-width=\"2\" />",
                        x1, y1, x2, y2
                ));
            }
            LocalTime aktualnyCzas=LocalTime.of(this.getH(),this.getM(),this.getS());
            System.out.println("=== DIAGNOSTYKA ZEGARA ===");
            System.out.println("Pobrany czas z klasy Clock: " + aktualnyCzas);
            for (ClockHand clockHand : listawskazowek) {
                clockHand.setTime(aktualnyCzas);
                System.out.println(clockHand.getClass().getSimpleName() + " generuje kod: " + clockHand.toSvg());
                writer.println(clockHand.toSvg());
            }

            // 4. Domknięcie tagu SVG.
            writer.println("</svg>");

            System.out.println("Plik SVG (kwadrat) wygenerowany: " + path);

        } catch (FileNotFoundException e) {
            System.err.println("Błąd zapisu pliku: " + e.getMessage());
        }
    }
}