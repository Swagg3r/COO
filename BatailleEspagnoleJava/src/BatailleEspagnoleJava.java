
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
    * - javadoc.
    * - mettre à jour UML
    * - mettre un Stack à la place d'une Arraylist pour les cartes ?
    * - revoir méthode de comptage des points ?
    * - expliquer premier + rangvainqueur % joueurs.size()
<<<<<<< HEAD
    * - le joueur qui a le plus de points à gagner
    * - Une fois les cartes jouer par un joueurs : afficher les cartes qui sont sur la table
    * - Quand on rentre comme parametre de partie "0" et "0", que se passe t il ?? 
    *      Rajouter un affichage des regles par default et/ou apres definitions des conditions dans tous les cas.
=======
    *                       Apres avoir effectué quelques test ...
    * - Une fois les cartes jouer par un joueurs : afficher les cartes qui sont sur la table.
    * - Gerer l'exception generée en tapant une une lettre quand on demande un chiffre pour jouer une carte. try catch
    * - Verifier le nom de joueurs ?? (pas 2x fois le meme)
    * - Preciser quoi repondre pour le choix de l'atout.
    * - BUG : lorsque on termine le premier jeu en mode prématuré (car personne ne choisit d'atout) le jeu suivant commence et les joueurs on 6 cartes en main .... WTF ?
    * - Gerer l'exception de 0 joueur. (Il en faut entre 2 et 4).
    * - Ne pas autoriser le lancement de la partie avec 1 joueur.
    * - Une fois les 4 joueurs rentrés : arreter la boule (il ne peut pas yen avoir d'autre
    * - Quand on rentre comme parametre de partie "0" et "0", que se passe t il ?? Rajouter un affichage des regles par default et/ou apres definitions des conditions dans tous les cas.
    * - IA
    * - IHM
>>>>>>> 18f933b054843db989e3b6525e9b4ec262c30f92
    *********************************************************/ 
    
    private static int nb_jeux = 3;
    
    public static void main(String[] args) {
        
        System.out.println("=============[ BATAILLE ESPAGNOLE ]=============\n");
        System.out.println("CONDITION(S) DE VICTOIRE :");
        System.out.println("-> Condition de victoire : \n- nombre de points à atteindre ? (P)"
                                                    + "\n- nombre de jeux maximum ? (J)"
                                                    + "\n- valeur par défaut : victoire 200 points ? (D)");
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
        nb = lireEntier();
        //création de la partie
        Partie p = new Partie(nb, mode);
        System.out.println("------------------------------------------------");
        System.out.println("CREATION DES JOUEURS :");
        p.createJoueurs();
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
                if(res == 0) throw new Exception();
                ok = true;
            } catch (Exception e) {
                System.out.println("(saisir une valeur entière > 0)");
            }
        } while (! ok);
        return res;
    }
}
