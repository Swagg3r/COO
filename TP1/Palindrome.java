import java.io.*; 

public class Palindrome { 
    public static void main (String [] arg) { 
    	if (arg.length < 1) {
    		System.out.println("Erreur argument");
    		System.exit(-1);
    	}
    	String chaine = arg[0];
    	for (int i = 0; i<chaine.length(); i++) {
    		if (chaine.charAt(i) != chaine.charAt(chaine.length()-i-1)) {
    			System.out.println(chaine+" n'est pas un palindrome");
    			System.exit(-1);
    		}
    	}
    	System.out.println(chaine+" est un palindrome");
    }
}
