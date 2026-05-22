public class Vec3D {
   public  double r,g,b;

    public Vec3D(double r, double g, double b) {
        this.r = r;  // this.r to POLE KLASY (szufladka w obiekcie)
                     // samo r to PARAMETR z nawiasu (wartość, którą ktoś podał)
        this.g = g;
        this.b = b;


    }
    public Vec3D add(Vec3D v) {

        return new Vec3D(this.r+v.r,this.g+v.g,this.b+v.b);
    }
    public Vec3D mul(double a) {
        return new Vec3D(this.r*a,this.g*a,this.b*a);
    }

    @Override
    public String toString() {
        return "Vec3D{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
