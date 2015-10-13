public class Occ { 
    public static void main ( String [] arg){ 
 	if (arg.length < 1){
		System.out.println("Usage : java Occ <chaine> <chaine>*"); 
		System.exit(-1);
	}

	String arg1 = arg[0];
	int i = 1;
	for(i=1; i<arg.length;i++){
		if(arg1.equals(arg[i])){
			System.out.println("Trouve");
			System.exit(-1);
		}
	}
	System.out.println("Non Trouve");
    } 
} 
