import java.io.*; 

public class Crible { 
    public static void main (String [] arg) { 
	    boolean [] tab = new boolean[101];
	    int i,j,test=0;
	    for(i=3;i<101;i++){
	    	tab[i] = false;
	    }
	    for (i=2; i<101; i++) {
	    	//for(j=2;j<i;j++){
	    	//	if(i%j==0) tab[i]=false;
	    	//	else tab[i] = true;
	    	//}
	    	test = 0;
	    	for (j=i-1;j > 1 ; j--) {
	    		if (i%j == 0) {
	    			test++;
	    		}
	    	}
	    	if (test == 0) {
	    		tab[i] = true;
	    	}
	    }
	    for (i=2;i<101 ;i++ ) {
	    	if (tab[i]==true) System.out.println(i);
	    }
    }
}
