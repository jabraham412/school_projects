/* John Abraham
 * CS 1501: Algorithm Implementation 
 * Assignment 2: Polynomials & Graphs
 * Task 1: Convert the Polynomial class from integer coefficients to BigInteger coefficients. (20 points)
*/

import java.math.BigInteger;

public class Polynomial {
    private BigInteger[] coef;  // coefficients
    private int deg;     // degree of polynomial (0 for the zero polynomial)



    /** Creates the constant polynomial P(x) = 1.
      */
    public Polynomial(){
        coef = new BigInteger[1];
        coef[0] = BigInteger.ONE;
        deg = 0;
    }



    /** Creates the linear polynomial of the form P(x) =  x + a.
      */
    public Polynomial(int a){
        coef = new BigInteger[2];
        coef[1] = BigInteger.ONE;
        coef[0] = BigInteger.valueOf(a);
        deg = 1;
    }




    /** Creates the polynomial P(x) = a * x^b.
      */
    public Polynomial(BigInteger a, int b) {
        coef = new BigInteger[b+1];
        for (int i=0; i < coef.length; i++) {
            if (i == b) {
                coef[b] = a;
            } else
                coef[i] = BigInteger.ZERO;
        }
        deg = degree();
    }





    /** Return the degree of this polynomial (0 for the constant polynomial).
      */
    public int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (!coef[i].equals(BigInteger.ZERO)) d = i;
        return d;
    }





    /** Return the sum of this polynomial and b, i.e., return c = this + b.
      */
    public Polynomial plus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.ZERO, Math.max(a.deg, b.deg));
        for (int i = 0; i <= a.deg; i++) c.coef[i] = c.coef[i].add(a.coef[i]);
        for (int i = 0; i <= b.deg; i++) c.coef[i] = c.coef[i].add(b.coef[i]);
        
        c.deg = c.degree();
        return c;
    }






    /** Return the difference of this polynomial and b, i.e., return (this - b).
      */
    public Polynomial minus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.ZERO, Math.max(a.deg, b.deg));
        for (int i = 0; i <= a.deg; i++) c.coef[i] = c.coef[i].add(a.coef[i]);
        for (int i = 0; i <= b.deg; i++) c.coef[i] = c.coef[i].subtract(b.coef[i]);
        
        c.deg = c.degree();
        return c;
    }






    /** Return the product of this polynomial and b, i.e., return (this * b).
      */
    public Polynomial times(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.ZERO, a.deg + b.deg);
        for (int i = 0; i <= a.deg; i++)
            for (int j = 0; j <= b.deg; j++)
                c.coef[i+j] = c.coef[i+j].add(a.coef[i].multiply(b.coef[j]));
        c.deg = c.degree();
        return c;
    }






    /** Return the composite of this polynomial and b, i.e., return this(b(x))  - compute using Horner's method.
      */
    public Polynomial compose(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.ZERO, 0);
        for (int i = a.deg; i >= 0; i--) {
            Polynomial term = new Polynomial(a.coef[i], 0);
            c = term.plus(b.times(c));
        }
        return c;
    }




    /** Return true whenever this polynomial and b are identical to one another.
      */
    public boolean equals(Polynomial b) {
        Polynomial a = this;
        if (a.deg != b.deg) return false;
        for (int i = a.deg; i >= 0; i--)
            if (a.coef[i] != b.coef[i]) return false;
        return true;
    }





    /** Evaluate this polynomial at x, i.e., return this(x).
      */
    public int evaluate(int x) {
        int p = 0;
        for (int i = deg; i >= 0; i--)
            p = coef[i].intValue() + (x * p);
        return p;
    }






    /** Return the derivative of this polynomial.
      */
    public Polynomial differentiate() {
        if (deg == 0) return new Polynomial(BigInteger.ZERO, 0);
        Polynomial deriv = new Polynomial(BigInteger.ZERO, deg - 1);
        deriv.deg = deg - 1;
        for (int i = 0; i < deg; i++)
            deriv.coef[i] = BigInteger.valueOf(i + 1).multiply(coef[i + 1]);
        return deriv;
    }





    /** Return a textual representationof this polynomial.
      */
    public String toString() {
        if (deg ==  0) return "" + coef[0];
        if (deg ==  1) return coef[1] + "x + " + coef[0];
        String s = coef[deg] + "x^" + deg;
        for (int i = deg-1; i >= 0; i--) {
            if      (coef[i].equals(BigInteger.ZERO)) continue;
            else if (coef[i].compareTo(BigInteger.ZERO) > 0) s = s + " + " + ( coef[i]);
            else if (coef[i].compareTo(BigInteger.ZERO) < 0) s = s + " - " + ( coef[i].abs());
            if      (i == 1) s = s + "x";
            else if (i >  1) s = s + "x^" + i;
        }
        return s;
    }






    public static void main(String[] args) {
        Polynomial zero = new Polynomial(BigInteger.valueOf(0), 0);

        Polynomial p1   = new Polynomial(BigInteger.valueOf(4), 3);
        Polynomial p2   = new Polynomial(BigInteger.valueOf(3), 2);
        Polynomial p3   = new Polynomial(BigInteger.valueOf(-2), 1);
        Polynomial p4   = new Polynomial(BigInteger.valueOf(-1), 0);
        Polynomial p    = p1.plus(p2).plus(p3).plus(p4);   // 4x^3 + 3x^2  - 2x - 1


        Polynomial q1   = new Polynomial(BigInteger.valueOf(3), 2);
        Polynomial q2   = new Polynomial(BigInteger.valueOf(5), 0);
        Polynomial q    = q1.minus(q2);                     // 3x^2 - 5


        Polynomial r    = p.plus(q);
        Polynomial s    = p.times(q);
        Polynomial t    = p.compose(q);

        System.out.println("zero(x) =     " + zero);
        System.out.println("p(x) =        " + p);
        System.out.println("q(x) =        " + q);
        System.out.println("p(x) + q(x) = " + r);
        System.out.println("p(x) * q(x) = " + s);
        System.out.println("p(q(x))     = " + t);
        System.out.println("0 - p(x)    = " + zero.minus(p));
        System.out.println("p(3)        = " + p.evaluate(3));
        System.out.println("p'(x)       = " + p.differentiate());
        System.out.println("p''(x)      = " + p.differentiate().differentiate());


        Polynomial poly = new Polynomial();

        for(int k=0; k<=3; k++){
            poly = poly.times(new Polynomial(-k));
        }

        System.out.println(poly);
   }

}