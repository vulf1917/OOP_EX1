package myMath;

public class mains {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 String str = "5x^2-2x+l"; 
	        String test = str.replaceAll ("-", "@-") ;
	        String[] arr = test.split("[+//@]");
	  
	        for (String a : arr) 
	            System.out.println(a); 
System.out.println(test);
	    } 
	}


