package myMath;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
//import java.util.function.Predicate;
import java.util.Map;
import java.util.Map.Entry;

import myMath.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also
 * should support the following: 1. Riemann's Integral:
 * https://en.wikipedia.org/wiki/Riemann_integral 2. Finding a numerical value
 * between two values (currently support root only f(x)=0). 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able {

	HashMap<Integer, Monom> polyMap; // the datastcture for the coefficient of the polynom

	/**
	 * Zero (empty polynom) constracting an polynom of the form 0.0x^0
	 * 
	 */
	public Polynom() {
		this.polyMap = new HashMap<Integer, Monom>(); // init polynom
		Monom a = new Monom(); // constract an empty monom
		polyMap.put(0, a); // put in the hashmap

	}

	// public static int biggesPower (String s) { //the biggest power
	//
	// int max=0;
	// int temp=0;
	// for (int i =0 ; i<s.length();i++) {
	// if(s.charAt(i)=='x'&&s.charAt(i+1)=='^'&&i<s.length()-2)
	// max=(int)s.charAt(i+2);
	// else if(s.charAt(i)=='x');
	//
	//
	// }
	// return max;
	//
	// }

	/**
	 * init a Polynom from an String in a hashtable with the power as key and monom
	 * as value ";
	 * 
	 * @param String of a simply Monoms of the type a*x^b , where a is is double and
	 *               b is a positive integer. The Monoms can only have a + or - sine
	 *               between them such as: {"x", "3+1.4X^3-34x"} a unvalid Polynom
	 *               example : {(2x^2-4)*(-1.2x-7.1)",$x^2,5x^^b,6x^7.7",5*x^2+1}
	 */
	public Polynom(String s) {
		s = s.toLowerCase();// in case there is a big X by mistake
		// boolean debagFlag = false;
		this.polyMap = new HashMap<Integer, Monom>(); // key is the intger and monom is the value
		Monom m = new Monom("0");
		Monom tempMonom = new Monom("0");

		s = s.replaceAll("-", "@-"); // add @ later were remove it and in this way will going to keep the minus
		String[] splitPol = s.split("[+/@]"); // split the arr
		for (int i = 0; i < splitPol.length; i++) {
			if (!splitPol[i].equals("")) {
				m = new Monom(splitPol[i]); // constracturing a monom
				if (!polyMap.containsKey(m.get_power())) { // the case there is not such a monom in the table already
					polyMap.put(m.get_power(), m); // put the monom to the hashmap
				} else // the case the monom is already in the hash map
				{
					tempMonom = new Monom(m);// deep copy
					// tempMonom = tempMonom.deepCopy(m, tempMonom);// store the monom that alredy
					// in the map in a temp monom
					// //
					// important make sure its a deep copy ! type name =
					// new
					// type();
					m.add(polyMap.get(m.get_power()));
					polyMap.remove(tempMonom.get_power());// delete the old monom from the hashmap
					polyMap.put(tempMonom.get_power(), m); // put the new monom into the map
				}

			} else {
				continue;
			}

		}

	}

	/**
	 * function to substitute to a Polynom an double value by replacing the x with
	 * the user parameter The function returning a double value
	 * 
	 * @param an double value such as {4.34324,1948}
	 */
	public double f(double x) {
		double ans = 0;
		for (Integer key : polyMap.keySet()) {
			ans = ans + polyMap.get(key).f(x);
		}
		return ans;
	}

	@Override
	/**
	 * function to substitute to a Polynom an double value by replacing the x with
	 * the user parameter The function returning a double value
	 * 
	 * @param an double value such as {4.34324,1948}
	 */
	public void add(Polynom_able other) {
		if (other instanceof Polynom) {
			Polynom p = new Polynom(other.toString());// convert 'Polynon_able' to 'Polynom'
			for (Integer pkey : p.polyMap.keySet()) {
				if (this.polyMap.containsKey(pkey)) {
					this.polyMap.get(pkey).add(p.polyMap.get(pkey));// adding to same power
				} else {
					polyMap.put(pkey, p.polyMap.get(pkey));// add new 'Monom'
				}
			}
		} else {
			throw new RuntimeException(" your Polynom_able is not  a Polynom type ");
		}

	}

	@Override
	/**
	 * function to add a Monom to the Polynom onject by adding the Monom coeficent
	 * to the Polynom object (Hashtable)
	 * 
	 * @param an valid Monom
	 */
	public void add(Monom m1) {
		Monom tempMonom = new Monom("0");
		if (polyMap.containsKey(m1.get_power())) // the case there alresdy is polynom with what power
		{
			// tempMonom = tempMonom.deepCopy(m1, tempMonom);// store the monom that alredy
			// in the map in a temp monom
			tempMonom = new Monom(m1);
			m1.add(polyMap.get(m1.get_power()));
			polyMap.remove(tempMonom.get_power());// delete the old monom from the hashmap
			polyMap.put(tempMonom.get_power(), m1); // put the new monom into the map

		}

		else {
			polyMap.put(m1.get_power(), m1); // the case therer is not such a Monom ( with this power in the Polynom)
		}

	}

	@Override
	/**
	 * function to subtract a Polynom_able from the Polynom onject by subtracting
	 * all the Monom's from the Polynom object (Hashtable)
	 * 
	 * @param an valid Polynom_able that is instance of Polynom
	 */
	public void substract(Polynom_able other) {
		if (other instanceof Polynom) {
			Polynom p = new Polynom(other.toString());// convert 'Polynon_able' to 'Polynom'
			Monom m = new Monom("-1");
			for (Integer pkey : p.polyMap.keySet()) {
				p.polyMap.get(pkey).multipy(m);
			}

			for (Integer pkey : p.polyMap.keySet()) {
				if (this.polyMap.containsKey(pkey)) {
					this.polyMap.get(pkey).add(p.polyMap.get(pkey));// adding to same power
				} else {
					polyMap.put(pkey, p.polyMap.get(pkey));// add new 'Monom'
				}
			}

		}

		else {
			throw new RuntimeException(" your Polynom_able is not  a Polynom type ");
		}

	}

	// polyMap.get(key).multipy(p.polyMap.get(pkey));
	// Polynom temp= new Polynom(polyMap.toString());
	@Override
	/**
	 * function to multiply a Polynom_able from the Polynom onject by multiply all
	 * the Monom's from the Polynom object (Hashtable)
	 * 
	 * @param an valid Polynom_able that is instance of Polynom
	 */
	public void multiply(Polynom_able other) {

		if (other instanceof Polynom) {

			Polynom p = new Polynom(other.toString());// convert 'Polynon_able' to 'Polynom'
			String s = "0";
			Polynom count = new Polynom(s);
			// temp.polyMap.clear();

			for (Integer pkey : p.polyMap.keySet()) {
				Polynom temp = new Polynom(this.toString());
				for (Integer key : temp.polyMap.keySet()) {
					temp.polyMap.get(key).multipy(p.polyMap.get(pkey));

				}
				count.add(temp);
				temp.polyMap.clear();
			}
			polyMap = count.polyMap;
		}

		else {
			throw new RuntimeException(" your Polynom_able is not  a Polynom type ");
		}

	}

	//@Override (removed i hope it's unneseserry )
	/**
	 * function to multiply a Polynom_able from the Polynom onject by multiply all
	 * the Monom's from the Polynom object (Hashtable)
	 * 
	 * @param an valid Polynom_able that is instance of Polynom
	 */
	public boolean equals(Polynom_able other) {

		if (other instanceof Polynom) {
			Polynom p = new Polynom(other.toString());

			try {
				for (Integer key : polyMap.keySet()) {
					if (Math.abs(
							polyMap.get(key).get_coefficient() - p.polyMap.get(key).get_coefficient()) <= 0.000001) { // check
																														// if
																														// all
						// the
						// coeficent is
						// the
						// same and all
						// the
						// power are the
						// same

					} else
						return false; // the case the power is the same but coeficent is diffrent

				}
				return true; // after all the power and the coeficent was checed return true
			}

			catch (Exception e) {
				return false;
			} // incase somthing go wrong return false the case the power is not the same
		} else {
			throw new RuntimeException(" your Polynom_able is not  a Polynom type ");
		}
	}

	@Override
	/**
	 * function to check if the Polynom is the zero Polynom .that means he have no
	 * Monoms with coeficent diffrent than zero
	 */
	public boolean isZero() {
		boolean ans = true;
		if (this.toString() == "")
			return true; // if its the Polynom as String is "" return true because off curse tthat mean
							// all the coeficent are zero
		for (Integer key : polyMap.keySet()) {
			if (polyMap.get(key).get_power() != 0 && polyMap.get(key).get_coefficient() != 0)
				return false; // if polynom contains any x that with 1 power or more
		}
		if (polyMap.get(0).get_coefficient() != 0)
			return false; // incase its a real number return false

		return ans;

	}

	@Override
	/**
	 * In mathematical analysis, the intermediate value theorem states that if f is
	 * a continuous function whose domain contains the interval [a, b], then it
	 * takes on any value between f(a) and f(b) at some point within the interval.
	 * its important that one of the point will have a negative scine and the other
	 * possitive one , the function returns double.
	 * 
	 * @param an x0 is the right point and x1 is the other point and eps is the size
	 *           of the final interval that the cutting point is going to be in
	 *           between
	 */
	public double root(double x0, double x1, double eps) {

		if (x0 > 0 && x1 > 0 || x0 < 0 && x1 < 0)
			return Double.MIN_VALUE; // if they are bouth possiitive or bouth negative

		double mid = x0;
		while ((Math.abs(x1 - x0)) >= eps) {

			mid = (x0 + x1) / 2;

			if (this.f(mid) == 0.0)// check if the middele point is the cut point.
				return mid;

			// cut the search area in half and search again depanding on wich side is
			// positive and wich side is negtive
			else if (this.f(mid) * this.f(x0) > 0)
				x0 = mid;
			else
				x1 = mid;
		}

		return mid;
	}

	@Override
	/*
	 * copies the Polynom to a Polynom_able
	 */
	public Polynom_able copy() {
		Polynom_able copiedPoly = new Polynom(); // init the new polynom
		for (Integer x : polyMap.keySet()) { // the main loop
			copiedPoly.add(polyMap.get(x)); // addidng all the monoms one by one

		}
		return copiedPoly;
	}

	@Override
	/*
	 * The derivative of a function of a real variable measures the sensitivity to
	 * change of the function value (output value) with respect to a change in its
	 * argument (input value). Derivatives are a fundamental tool of calculus. this
	 * function is doing derivative by the fprmula "xn=nxn–1"
	 */
	public Polynom_able derivative() {
		Polynom copy = new Polynom(this.toString());
		Polynom ans = new Polynom();
		Monom temp = new Monom();
		for (Integer x : copy.polyMap.keySet()) {
			temp = copy.polyMap.get(x).derivative();
			ans.polyMap.put(temp.get_power(), temp);
		}
		return ans;
	}

	@Override
	/**
	 * is approximating the area of functions or lines on a graph, but also the
	 * length of curves and other approximations by Riemann sum
	 * 
	 * @param an x0 is the right point and x1 of the other point of the graph that
	 *           the are is going to be counted in between and eps is width of the
	 *           rectangle that the area is going to be calculated by.
	 * 
	 */
	public double area(double x0, double x1, double eps) {
		double count = (x1 - x0) / eps;
		double sum = 0;

		for (int i = 0; i < count; i++) {
			sum = sum + (eps * this.f(x0));
			x0 = x0 + eps;
		}

		return sum;

	}

	@Override
	/*
	 * An iterator over a collection. Iterator takes the place of Enumeration in the
	 * Java Collections Framework. Iteratorsdiffer from enumerations in two ways: •
	 * Iterators allow the caller to remove elements from theunderlying collection
	 * during the iteration with well-definedsemantics. • Method names ( the
	 * explanation taken from java oracle )
	 * 
	 */
	public Iterator<Monom> iteretor() {

		return polyMap.values().iterator();
	}

	@Override
	/**
	 * multipling an Monom to all the polynom one by one for example (5x^2)*(4x^5+8)
	 * 
	 * @param an valid Monom
	 * 
	 */
	public void multiply(Monom m1) {
		ArrayList<Integer> coff = new ArrayList();

		for (Integer key : polyMap.keySet()) { // copy all the keys to an arraylist because if we use the ket it going
												// to make problem once we remove one of the keys
			coff.add(key);
		}

		for (int i = 0; i < coff.size(); i++) {
			polyMap.get(coff.get(i)).multipy(m1); // multyplaing the Monom
			Monom temp = new Monom(polyMap.get(coff.get(i)).toString()); // saving it to a copy because we need to
																			// update the key
			polyMap.remove(coff.get(i));// remove the old monom by the key
			polyMap.put(temp.get_power(), temp); // put the new Monom by the new key
		}

		/*
		 * making a String of all the Monoms of the Polynom where they arre sorted from
		 * the smallest in the right to the biggest in the left an zero Polynom is going
		 * to be displayed as an empty Sting ("")
		 */
	}

	public String toString() {

		String ans = "";
		for (Integer x : polyMap.keySet()) {

			// if(polyMap.get(x).get_power()==0) continue;

			if ((polyMap.get(x).get_coefficient() == 0))
				continue;

			if (polyMap.get(x).toString().charAt(0) != '-') { // if it not the coeficent is not minus add a plus
				ans = ans + "+" + polyMap.get(x).toString();
			} else {
				ans = ans + polyMap.get(x).toString();
			}

			if ((ans.length() != 0) && ans.charAt(ans.length() - 1) == '+') { // if the last char is + remove it
				ans = ans.substring(0, ans.length());
			}

			if ((ans.length() != 0) && ans.charAt(0) == '+') { // if the first char is + remove it
				ans = ans.substring(1, ans.length());
			}
		}

		return ans;
	}

	public static void main(String[] args) {
		Monom mon = new Monom("5x");
		Polynom pol = new Polynom("2x^2+4");
		pol.multiply(mon);
		System.out.println(pol.toString());

	}

	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		return null;
	}

}