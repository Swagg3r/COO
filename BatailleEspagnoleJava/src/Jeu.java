
import java.util.ArrayList;
import java.util.Scanner;

public class Jeu {

    private famille atout;
    private ArrayList<Carte> paquet;
    private final ArrayList<Joueur> joueurs;
    private int dealer;
    
    /* Constructeur */
    
    public Jeu(ArrayList<Carte> paquet, ArrayList<Joueur> participants) {
        this.joueurs = participants;
        this.paquet = melangerPaquet(paquet);
        
        // initialisation des joueurs :
        // - si c'est le tout premier jeu, j1 distribue et j2 commence
        // - sinon :
        //      . l'attribut aLaMain de tous les joueurs est remis à false
        //      . le dealer devient le joueur suivant le précédent dealer
        //      . le premier à commencer est le joueur suivant le dealer
        
        boolean premierJeu = true;
        for(int i = 0; i < joueurs.size(); i++) {
            if(joueurs.get(0).getMain() == true) {
                premierJeu = false;
            }
        }
       
        if(premierJeu) {
            //le premier joueur de la liste distribue
            joueurs.get(0).setDealer(true);
        } else {
            for(int i = 0; i < joueurs.size(); i++) {
                //on remet l'attribut aLaMain de tous les joueurs à false
                joueurs.get(i).setMain(false);
            }
            //changement de dealer
            joueurs.get(dealer).setDealer(false);
            dealer = (dealer+1) % joueurs.size();
            joueurs.get(dealer).setDealer(true);
        }
        //premier joueur à jouer
        joueurs.get((dealer+1) % joueurs.size()).setMain(true);
    }
    
    /* get / set / add / remove */
    
    // atout
    public famille getAtout() {
        return this.atout;
    }
    public void setAtout(famille atout) {
        this.atout = atout;
    }
    
    /* Fonctions */
    
    public void lancer() {
        distribuerCartes();
        définirAtout();
        if (this.atout == null) {
            System.out.println("fin prématurée du jeu");
            //reset des cartes en main
            for(Joueur j : joueurs) {
                j.setCartes(new ArrayList<Carte>());
            }
            return;
        }
        int i = 1;
        //chaque itération du while correspond à un pli
        while(resteDesCartes()) { 
            System.out.println("================================================");
            System.out.println(" pli n° "+i+"/"+52/joueurs.size());
            System.out.println("------------------------------------------------");
            tourDeJeu();
            i++;
        }
    }
    
    public boolean resteDesCartes() {
        //si il reste des cartes dans le paquet
        if(!paquet.isEmpty()) return true;
        //si les joueurs ont encore des cartes en main
        //(les joueurs ont tjr le meme nombre de cartes en main)
        if(joueurs.get(0).getCartes().isEmpty() == false) return true;
        return false;
    }
    
    public void tourDeJeu() {
        
        int nbJoueurs = joueurs.size();
        ArrayList<Carte> pli = new ArrayList<>();
        int premier = premierJoueur();
        
        //les joueurs jouent une carte chacun leur tour
        for(int i = 0; i < nbJoueurs; i++) {
            Joueur joueurCourant = joueurs.get((premier + i) % nbJoueurs);
            System.out.println("\n" + joueurCourant.getNomJoueur() + ", c'est à vous de jouer.");
            //affichage des cartes jouées par les autres joueurs
            if(!pli.isEmpty()) {
                System.out.println("Pli courant :");
                for (Carte c : pli) {
                    System.out.println("- " + c.toString());
                }
            }
            //le joueur joue une carte (placée dans le pli courant)
            Carte carteJouee = joueurCourant.jouerCarte();
            pli.add(carteJouee);
            //le joueur pioche une nouvelle carte (si il en reste)
            if(!paquet.isEmpty()) {
                joueurCourant.piocher(paquet);
            }
        }
        
        //déterminer vainqueur du pli.
        int indexCarteGagnante = 0;
        for(int i = 1; i < pli.size(); i++) {
            if(pli.get(i).getFamille() == atout && pli.get(indexCarteGagnante).getFamille() != atout) {
                //si premier atout joué : cette carte passe d'office gagnante potentielle
                indexCarteGagnante = i;
            } else if(pli.get(i).getFamille() == pli.get(indexCarteGagnante).getFamille()) {
                //si même couleur que la carte gagnante retenue pour le moment : tester si plus fort
                if(pli.get(indexCarteGagnante).compareTo(pli.get(i)) == -1) indexCarteGagnante = i;
            }
        }
        
        Joueur gagnantPli = joueurs.get((premier + indexCarteGagnante) % joueurs.size());
        
        //passage de la main
        joueurs.get(premier).setMain(false);
        gagnantPli.setMain(true);
        
        //Comptage des points.
        int points = 0;
        for(Carte c : pli) {
            points += c.getType().getValue();
        }
        gagnantPli.addPoints(points);
        
        System.out.println("------------------------------------------------");
        System.out.println(gagnantPli.getNomJoueur() + " remporte ce pli (" + points + " points) et prend la main.\n");
    }
    
    public int premierJoueur() {
        int i;
        for(i = 0; i < joueurs.size(); i++) {
            if(joueurs.get(i).getMain() == true) break; 
        }
        return i;
    }
    
    public ArrayList<Carte> melangerPaquet(ArrayList<Carte> paquetRangé) {
        //copie du paquet rangé
        ArrayList paquetBuffer = new ArrayList<Carte>(paquetRangé);
        //création du paquet mélangé
        ArrayList paquetFinal = new ArrayList<Carte>();
        int rand;
        
        while(paquetBuffer.size() > 0) {
            rand = (int)(Math.random()*paquetBuffer.size());
            paquetFinal.add(paquetBuffer.get(rand));
            paquetBuffer.remove(rand);
        }
        //cas particulier : si il y a 3 joueurs, il faut retirer une carte du paquet
        if(joueurs.size() == 3) {
            System.out.println("------------------------------------------------");
            System.out.println("/!\\ Cette partie comporte 3 joueurs.");
            System.out.println("Une carte a par conséquent été retirée du paquet :");
            System.out.println("(" + paquetFinal.get(0).toString() + ")");
            System.out.println("------------------------------------------------");
            paquetFinal.remove(0);
        }
        
        return paquetFinal;
    }

    public void distribuerCartes() {
        //distribution des cartes 1 par 1
        for(int i = 0; i < 3; i++) {
            for(Joueur j : joueurs){
                j.addCarte(paquet.get(0));
                paquet.remove(0);
            }
        }
    }
    
    public void print() {
        System.out.println("---------[ Jeu courant ]---------");
        System.out.println("- dealer : " + joueurs.get(dealer).getNomJoueur());
        System.out.println("- atout : " + (atout == null ? "pas encore choisi" : atout));
        System.out.println("- paquet (" + paquet.size() + ") cartes :");
        for(Carte c : paquet) {
            c.print();
        }
        System.out.println("---------------------------------\n");
    }
    
    public void définirAtout() {
        
        System.out.println("Choix de l'atout :");
        
        boolean atoutChoisi = false;
        //pioche la première carte
        Carte atoutPioché = paquet.get(0);
        paquet.remove(0);
        System.out.println("carte piochée : "+ atoutPioché.toString()+"\n");

        //interroger les joueurs (en commençant par celui joueur suivant le dealer)
        int nb_joueurs = joueurs.size();//pour alléger le code
        for(int i = 1; i <= nb_joueurs; i++) {
            Joueur joueurCourant = joueurs.get((dealer + i) % nb_joueurs);
            if(joueurCourant.accepteAtout(atoutPioché)) {
                atout = atoutPioché.getFamille();
                atoutChoisi = true;
                break;
            }     
        }
        //remettre la carte à la fin du paquet
        paquet.add(atoutPioché);
        
        //2e tour : on demande à chaque joueur la couleur qu'il veut pour l'atout
        if(!atoutChoisi) {
            System.out.println("\n2e tour : ");
            for(int i = 1; i <= nb_joueurs; i++) {
                if (atoutChoisi == true) break;
                Joueur joueurCourant = joueurs.get((dealer + i) % nb_joueurs);
                String choix = joueurCourant.choisirAtout();
                if (choix.equals("C")) {
                    this.atout = famille.Coupe;
                    atoutChoisi = true;
                } else if (choix.equals("B")) {
                    this.atout = famille.Bâton;
                    atoutChoisi = true;
                } else if (choix.equals("O")) {
                    this.atout = famille.Or;
                    atoutChoisi = true;
                } else if (choix.equals("E")) {
                    this.atout = famille.Epée;
                    atoutChoisi = true;
                }
            }
        }
        
        if(atoutChoisi) {
           System.out.println("l'atout choisi est " + this.atout);
        }
        System.out.println();
    }
}