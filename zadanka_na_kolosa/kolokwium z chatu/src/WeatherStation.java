import java.util.Locale;

public abstract class WeatherStation {
    private City city;
    private double temp;

    public WeatherStation(double temp,City city) {
        this.temp = temp;
        this.city=city;
    }


        public void readSystemSensor(){
            this.temp=21.6;
        }

        public void setTemp(Double newTemp){
        if(newTemp< -90.0 || newTemp>60.0){

            throw new IllegalArgumentException("Wartość  wykracza poza skale");

        }
        this.temp=newTemp;
        }



    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"%.1f\u00B0C",this.temp);
    }

/*
"przyjmie referencję na obiekt City" – oznacza: daj stacji informację o jakimś konkretnym mieście (pokaż jej to miasto).
"i zastąpi w obiekcie WeatherStation dotychczasową referencję na City" – oznacza: jeśli stacja myślała, że stoi w Warszawie, a teraz powiesz jej "Poznań",
 to ma wymazać Warszawę i wpisać sobie Poznań.
 */
    public void setCity(City newCity){
           this.city= newCity; // z prawej strony bierzemy i wkladamyuy do pudelka po lewj stronie czyli puste city u napisuje to nowe miasto NewCity
    }


    public double getTemp() {
        return temp;
    }

}


