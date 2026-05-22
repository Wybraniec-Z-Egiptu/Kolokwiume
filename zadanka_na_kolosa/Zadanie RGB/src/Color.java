public class Color extends Vec3D{

    public Color(double r, double g, double b) {
        super(r, g, b);

        if(r<0.0 ||r>1.0 ||g<0.0 ||g>1.0 ||b<0.0 ||b>1.0 ){
            throw new IllegalArgumentException("Skladowe rgb musza byc w przedziale od 0-1");
        }
    }

    public static Color mix(Color c1, Color c2,double ratio){
        Vec3D values=c1.mul(ratio).add(c2.mul(1-ratio)); //oblicza brakującą część do pełnych 100%
        return new Color(values.r,values.g,values.b);
    }
}
