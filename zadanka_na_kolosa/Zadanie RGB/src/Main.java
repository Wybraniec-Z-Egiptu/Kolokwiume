public class Main  {
    public static void main(String[] args) {
Color c1=new Color(1,0,0);
Color c2=new Color(1,1,0);
Color c3=Color.mix(c1,c2,0.5);
        System.out.println(c3);
    }
}
