
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author thebauda
 */
public class BatailleEspagnoleJava {

    /**
     * @param args the command line arguments
     */
    
    /************************[ TODO ]************************   
    * - revoir l'affichage en général
    * - javadoc
    * - gérer partie à 0 joueurs
    * - partie à 1 joueur : weird behavior atout
    * - mettre à jour UML
    * - mettre un Stack à la place d'une Arraylist pour les cartes ?
    * - revoir méthode de comptage des points ?
    * - expliquer premier + rangvainqueur % joueurs.size()
    *********************************************************/ 
    
    private static int nb_jeux = 3;
    
    public static void main(String[] args) {
        
        System.out.println("=============[ BATAILLE ESPAGNOLE ]=============\n");
        System.out.println("CONDITION(S) DE VICTOIRE :");
        System.out.println("-> Définissez le nombre de points maximum à atteindre :");
        System.out.println("(Saisir \"0\" pour ignorer ce paramètre)");
        int nbPoints = lireEntier();
        System.out.println("-> Définissez la limite de nombre de jeux :");
        System.out.println("(Saisir \"0\" pour ignorer ce paramètre)");
        int nbJeux = lireEntier();
        //création de la partie
        Partie p = new Partie(nbPoints, nbJeux);
        System.out.println("------------------------------------------------");
        System.out.println("CREATION DES JOUEURS :");
        System.out.println("(Saisir \"stop\" pour arrêter la saisie)");
        Scanner sc = new Scanner(System.in);
        String str;
        while(true) {
            System.out.println("-> Saisir un nom de joueur à ajouter à la partie :");
            str = sc.nextLine();
            if(str.toUpperCase().equals("STOP")) break;
            p.addJoueur(new Joueur(str));
        }
        System.out.println("================================================\n");
        //lancer la partie
        p.lancer();
    }
    
    //author = prof de bd
    public static int lireEntier() {
        
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        int res = 0;
        boolean ok = false;
        do {
            try {
                res = Integer.parseInt(stdin.readLine());
                ok = true;
            } catch (Exception e) {
                System.out.println("(saisir un entier)");
            }
        } while (! ok);
        return res;
    }
}
