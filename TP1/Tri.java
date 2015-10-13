/* tri a bulle*/ 
public class Tri { 
    public static void bulle (int [] tab){ 
    	int temp=0;
    	for (int i=1; i<tab.length; i++) {
    		for (int j=0; j<tab.length;j++) {
    			if (tab[i] < tab[j]) {
	    			temp = tab[i];
	    			tab[i] = tab[j];
	    			tab[j] = temp;
    			}	
    		}
    	}
    } 
   
   public static void main (String [] arg){ 
	   int t[] = {2,3,7,5,6,11,0}; 
	   System.out.println("Avant"); 
	   for (int i=0; i<t.length; i++){ 
	 		System.out.println(t[i] + " "); 
	   } 
	   System.out.println("Apres"); 
	   Tri.bulle(t);
	   for (int i=0; i<t.length; i++){ 
	   		System.out.println(t[i] + " "); 
	   } 
   } 
} 