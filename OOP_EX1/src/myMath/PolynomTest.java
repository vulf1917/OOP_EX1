package myMath;

public class PolynomTest {
	public static void main(String[] args) {
		stringConstracorTest();
		test1();
		test2();
		Functest();
	}

	private static void stringConstracorTest() {
		System.out.println("*****  stringConstracor:  *****");
		System.out.println("*****  Good monoms:  *****");
		String[] goodPoly = { "2x^2+x^3+X", "-x", "", "0", "2x^23333333+4x^1+4x^3+4x^3", "8x^4", "-3.2X^2+55", "" };
		String[] badPoly = { "2*x^2+1", "{x+3+4}", "-", "4x^3.0", "(2x^3)", "$4x^2", "Boaz+5x^4", "(x^22)-x^3","2/3x^2",
				"2x^-3" };
		Polynom m = new Polynom();
		int counter = 0;

		for (int i = 0; i < goodPoly.length; i++) {
			m = new Polynom(goodPoly[i]);
			String s = m.toString();
			m = new Polynom(s);
			double fx = m.f(i);
			System.out.println(i + ") " + m + "    \tisZero: " + m.isZero() + "\t f(" + i + ") = " + fx);
		}
		System.out.println("*****  Bad monoms:  *****");

		for (int i = 0; i < badPoly.length; i++) {
			try {
				m = new Polynom(badPoly[i]);
				String s = m.toString();
				m = new Polynom(s);
				double fx = m.f(i);
				System.out.println(i + ") " + m + "    \tisZero: " + m.isZero() + "\t f(" + i + ") = " + fx);
			}

			catch (Exception e) {

				System.out.println(counter + 1 + ") " + "Error acurded on " + badPoly[counter]);
				counter++;

			}
		}

		System.out.println();
	}

	public static void test1() {
		System.out.println();
		System.out.println("*****  test1:  *****");
		Polynom p1 = new Polynom();
		String[] monoms = { "1", "x", "x^2", "0.5x^2" };
		// for(int i=0;i<monoms.length;i++) {
		Monom m = new Monom(monoms[1]);
		p1.add(m);
		double aa = p1.area(0, 1, 0.0001);
		p1.substract(p1);
		System.out.println(p1);
	}

	public static void test2() {
		System.out.println();
		System.out.println("*****  test2:  *****");
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		String[] monoms1 = { "2", "-x", "-3.2x^2", "4", "-1.5x^2" };
		String[] monoms2 = { "5", "1.7x", "3.2x^2", "-3", "-1.5x^2" };
		for (int i = 0; i < monoms1.length; i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for (int i = 0; i < monoms2.length; i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: " + p1);
		System.out.println("p2: " + p2);
		p1.add(p2);
		System.out.println("p1+p2: " + p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: " + p1);
		String s1 = p1.toString();
	}

	public static void Functest() {
		System.out.println();
		System.out.println("*****  FuncTest:  *****");
		Polynom p1 = new Polynom();
		String[] Polynoms = { "2x^2+x^3+X", "x", "x^2+5", "0.5x^2", "3x^4+45+x+x" };
		String[] PolynomsEq = { "", "0", "x^2+2", "1.9999999999999999+x^2", "3", "2+1" };
		// for(int i=0;i<monoms.length;i++) {
		for (int i = 0; i < Polynoms.length - 1; i++) {
			Polynom a = new Polynom(Polynoms[i]);
			Polynom_able b = new Polynom(Polynoms[i + 1]);
			System.out.println(i + 1 + ") " + "add function " + a.toString() + "/ " + b.toString()
					+ " equals (false expected) answer is :  " + a.equals(b));
			System.out.println("root function   " + a.root(0, 10, 0.001) + " " + b.derivative().toString());
		}
		for (int i = 0; i < PolynomsEq.length - 1; i++) {
			Polynom a = new Polynom(PolynomsEq[i]);
			Polynom_able b = new Polynom(PolynomsEq[i + 1]);
			System.out.println(i + 1 + ") " + "equals func" + "     a:=" + PolynomsEq[i] + "b:= " + PolynomsEq[i + 1]
					+ " answer is :  " + a.equals(b));
		}
		String[] q = { "2x^2-x^3+X", "x", "x^2+5", "0.5x^2", "3x^4+45+x+x" };
		String[] w = { "", "0", "x^2-2", "1.9999999999999999+x^2", "3", "2+1" };
		Polynom a = new Polynom(q[0]);
		Polynom_able b = new Polynom(w[2]);
		Polynom area = new Polynom(q[4]);
		Monom m = new Monom("-5x");
		System.out.println("poly a:           " + a);
		a.multiply(m);
		System.out.println("multiply poly a by monom m      :" + a);
		a.add(b);
		System.out.println("poly 'b' added to 'a'          " + a);
		a.substract(b);
		System.out.println("poly 'b' subtract to 'a'        " + a);
		a.multiply(b);
		System.out.println("poly a muliply by b  " + a);
		System.out.println("the sum area of poly \"3x^4+45+x+x\":        " + area.area(3, 9, 0.000001));
		System.out.println("poly a: " + a.toString() + " and the copy :     " + a.copy().toString());
		a.multiply(m);
		System.out.println("multiply poly a by monom m      :" + a);
	}
}
