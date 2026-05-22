public class ColdTemperatureBar extends ChartBar{
    private  int dlugosc;
    @Override
    public void setValue(Double temperatura) {
       int obliczonaDl=(int)(temperatura *10);
        this.dlugosc=obliczonaDl;
    }

    @Override
    public String toTxtChart() {
String wynik="";
       for(int i=0;i<dlugosc;i++){
           wynik+="-";
       }
       return wynik;
    }
}
