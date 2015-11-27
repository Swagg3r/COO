
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * <b>BatailleEspagnoleJava est la classe qui représente une partie de bataille espagnole. </b>
 * <p>Une bataille espagnole se déroule de la manière suivante :</p>
 * <ul>
 * <li>L'utiliseur de choisit la condition d'arrêt (de victoire) de la partie.</li>
 * <li>Il définit la valeur de cette condition (nombre de points à atteindre ou nombre de jeux). 
 * Cette étape est ignorée si l'utilisateur a choisi le mode par défaut.</li>
 * <li>Il saisit au clavier les noms des joueurs.</li>
 * </ul>
 * <p>Les paramètres saisis sont envoyés à la classe Partie. </p>
 * 
 * @see Partie
 * 
 * @author Aurélien MONNET-PAQUET, Antoine THEBAUD
 */

public class BatailleEspagnoleJava {
    
    /**
     * Cette méthode est automatiquement appelée au lancement du programme.
     * @param args les arguments de la méthode main (inutilisés ici).
     */
    public static void main(String[] args) {
        
        System.out.println("=============[ BATAILLE ESPAGNOLE ]=============\n");
        System.out.println("CONDITION(S) DE VICTOIRE :");
        System.out.println("-> Condition de victoire : \n- nombre de points à atteindre ? (P)"
                                                    + "\n- nombre de jeux maximum ? (J)"
                                                    + "\n- valeur par défaut : victoire en 200 points ? (D)");
        Scanner sc = new Scanner(System.in);
        String mode = sc.nextLine();
        while(!mode.toUpperCase().equals("P") && !mode.toUpperCase().equals("J") && !mode.toUpperCase().equals("D")) {
            System.out.println("Vous devez saisir \"P\", \"J\" ou \"D\"!");
            mode = sc.nextLine();
        }
        int nb = 0;
        if(mode.toUpperCase().equals("P")) {
            System.out.println("-> Définissez le nombre maximum de points à atteindre :");
        } else if(mode.toUpperCase().equals("J")) {
            System.out.println("-> Définissez la limite de nombre de jeux :");
        }
        nb = readUInt();
        //création de la partie
        Partie p = new Partie(nb, mode);
        System.out.println("------------------------------------------------");
        System.out.println("CREATION DES JOUEURS :");
        p.createJoueurs();
        System.out.println("================================================\n");
        //lancer la partie
        p.lancer();
    }
    
    /**
     * lecture au clavier d'un entier non-signé positif
     * @return l'entier lu.
     */
    public static int readUInt() {
        Scanner sc = new Scanner(System.in);
        int val = 0;
        boolean valid = false;
        do {
            try {
                val = Integer.parseInt(sc.nextLine());
                if(val <= 0) throw new Exception();
                valid = true;
            } catch (Exception e) {
                System.out.println("(saisir une valeur entière strictement positive)");
            }
        } while (!valid);
        return val;
    }
}
