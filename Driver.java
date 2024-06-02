import java.io.File;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {-6,1};
        int [] e1 = {0, 2};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {1,-2};
        int[] e2 = {1, 5};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        System.out.print("s + t = ");
        Polynomial q = p1.multiply(p2);
        q.printPolynomial();
        System.out.println();
        Polynomial r = new Polynomial(new File("textfile"));
        r.printPolynomial();
        p1.saveToFile("textfile");
    }
}
