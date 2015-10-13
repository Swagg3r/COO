public class Arg { 
    public static void main ( String [] arg){ 
 	if (arg.length < 2){
		System.out.println("Usage : java Arg <nombre> <nombre>"); 
		System.exit(-1);
	}

	int nb1 = Integer.parseInt(arg[0]);
	int nb2 = Integer.parseInt(arg[arg.length-1]);

	if(nb1 < nb2){
		System.out.println(arg[0]+" est inferieur a "+arg[arg.length-1]);
	}

	else {
		System.out.println(arg[1]+" est inferieur a "+arg[0]);
	}
    } 
} 
