
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <b>Jeu est la classe représentant un jeu au sein d'une partie. </b>
 * <p>Un jeu est enchaînement de plis qui se termine lorsqu'il n'y a plus de cartes à jouer.
 * Il est caractérisé par les informations suivantes :</p>
 * <ul>
 * <li>Un paquet de cartes.</li>
 * <li>Un atout (l'une des 4 familles).</li>
 * <li>Une liste de joueurs (les participants au jeu).</li>
 * <li>Un index indiquant qui est le dealer (le joueur qui distribue les cartes au début du jeu).</li>
 * </ul>
 * 
 * @see famille
 * @see Carte
 * @see Joueur
 * 
 * @author Aurélien MONNET-PAQUET, Antoine THEBAUD
 */

public class Jeu {
    
    /**
     * L'atout du jeu. Cet attribut est modifiable.
     * @see Jeu#getAtout()
     */
    private famille atout;
    
    /**
     * Le paquet de cartes. Cet attribut est initialisé à la construction
     * de l'objet et n'est pas modifiable par la suite.
     * @see Jeu#Jeu(ArrayList<Carte>, ArrayList<Joueur>)
     * @see Jeu#getPaquet()
     */
    private ArrayList<Carte> paquet;
    
    /**
     * Les participants au jeu. Cet attribut est initialisé à la construction
     * de l'objet et n'est pas modifiable par la suite.
     * @see Jeu#Jeu(ArrayList<Carte>, ArrayList<Joueur>)
     * @see Jeu#getJoueurs()
     */
    private ArrayList<Joueur> joueurs;
    
    /**
     * L'index du joueur qui distribue les cartes. Cet attribut est modifiable.
     * @see Jeu#getDealer()
     * @see Jeu#setDealer(int)
     */
    private int dealer;
    
    /**
     * <p>Constructeur de Jeu. </p>
     * A la construction d'un objet Jeu, le paquet passé en paramètre est mélangé et les joueurs sont initialisés.
     * @param paquet Le paquet de cartes.
     * @param participants Les joueurs prenant part à la partie.
     * @see Jeu#atout
     * @see Jeu#paquet
     * @see Jeu#joueurs
     * @see Jeu#dealer
     * @see Jeu#melangerPaquet
     */
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

    /**
     * Lance le jeu et le déroule jusqu'à son terme.
     * <ul>
     * <li>les cartes sont distribuées.</li>
     * <li>l'atout est défini.</li>
     * <li>enchaînement des plis jusqu'à ce qu'il n'y ait plus de cartes.</li>
     * </ul>
     */
    public void lancer() {
        distribuerCartes();
        définirAtout();
        // cas particulier : arrêt prématuré du jeu
        if (this.atout == null) {
            System.out.println("fin prématurée du jeu");
            // reset des cartes en main
            for(Joueur j : joueurs) {
                j.setCartes(new ArrayList<Carte>());
            }
            return;
        }
        // enchaînement des plis jusqu'à ce qu'il n'y ait plus de cartes
        int nbPli = 52 / joueurs.size();
        int i = 1;
        while(resteDesCartes()) { 
            System.out.println("================================================");
            System.out.println(" pli n°" + i + "/" + nbPli);
            System.out.println("------------------------------------------------");
            tourDeJeu();
            i++;
        }
    }
    
    /**
     * Effectue un tour de jeu (un pli).
     * <ul>
     * <li>chaque joueur joue une carte (en commençant par le joueur ayant la main).</li>
     * <li>identification de la carte gagnante (et donc du vainqueur du pli).</li>
     * <li>la main passe au vainqueur du pli (il jouera en premier pour le pli suivant).</li>
     * <li>comptabilisation des points gagnés sur ce pli.</li>
     * </ul>
     */
    public void tourDeJeu() {
        
        int nbJoueurs = joueurs.size();
        ArrayList<Carte> pli = new ArrayList<>();
        int premier = premierJoueur();
        
        // les joueurs jouent une carte chacun leur tour
        for(int i = 0; i < nbJoueurs; i++) {
            Joueur joueurCourant = joueurs.get((premier + i) % nbJoueurs);
            System.out.println("\n" + joueurCourant.getNomJoueur() + ", c'est à vous de jouer.");
            // affichage des cartes jouées par les autres joueurs
            if(!pli.isEmpty()) {
                System.out.println("Pli courant :");
                for (Carte c : pli) {
                    System.out.println("- " + c.toString());
                }
            }
            // le joueur joue une carte (placée dans le pli courant)
            Carte carteJouee = joueurCourant.jouerCarte();
            pli.add(carteJouee);
            // le joueur pioche une nouvelle carte (si il en reste)
            if(!paquet.isEmpty()) {
                joueurCourant.piocher(paquet);
            }
        }
        
        // déterminer vainqueur du pli.
        int indexCarteGagnante = 0;
        for(int i = 1; i < pli.size(); i++) {
            if(pli.get(i).getFamille() == atout && pli.get(indexCarteGagnante).getFamille() != atout) {
                // si premier atout joué : cette carte devient gagnante potentielle
                indexCarteGagnante = i;
            } else if(pli.get(i).getFamille() == pli.get(indexCarteGagnante).getFamille()) {
                // si même couleur que la carte gagnante retenue pour le moment : tester si plus fort
                if(pli.get(indexCarteGagnante).compareTo(pli.get(i)) == -1) indexCarteGagnante = i;
            }
        }
        
        Joueur gagnantPli = joueurs.get((premier + indexCarteGagnante) % joueurs.size());
        
        // passage de la main
        joueurs.get(premier).setMain(false);
        gagnantPli.setMain(true);
        
        // comptage des points.
        int points = 0;
        for(Carte c : pli) {
            points += c.getType().getValue();
        }
        gagnantPli.addPoints(points);
        
        System.out.println("------------------------------------------------");
        System.out.println(gagnantPli.getNomJoueur() + " remporte ce pli (" + points + " points) et prend la main.\n");
    }
    
    /**
     * Mélange le paquet généré par le constructeur de Partie.
     * @param paquetRangé le paquet de départ (trié).
     * @return Un paquet mélangé.
     */
    public ArrayList<Carte> melangerPaquet(ArrayList<Carte> paquetRangé) {
        // copie du paquet rangé
        ArrayList paquetBuffer = new ArrayList<Carte>(paquetRangé);
        // création du paquet mélangé
        ArrayList paquetFinal = new ArrayList<Carte>();
        int rand;
        
        while(paquetBuffer.size() > 0) {
            rand = (int)(Math.random()*paquetBuffer.size());
            paquetFinal.add(paquetBuffer.get(rand));
            paquetBuffer.remove(rand);
        }
        // cas particulier : si il y a 3 joueurs, il faut retirer une carte du paquet
        if(joueurs.size() == 3) {
            System.out.println("------------------------------------------------");
            System.out.println("/!\\ Cette partie comporte 3 joueurs.");
            System.out.println("Une carte a été retirée du paquet :");
            System.out.println("(" + paquetFinal.get(0).toString() + ")");
            System.out.println("------------------------------------------------");
            paquetFinal.remove(0);
        }
        
        return paquetFinal;
    }

    /**
     * Distribue 3 cartes à chaque joueur.
     */
    public void distribuerCartes() {
        // distribution des cartes 1 par 1
        for(int i = 0; i < 3; i++) {
            for(Joueur j : joueurs){
                j.addCarte(paquet.get(0));
                paquet.remove(0);
            }
        }
    }
    
    /**
     * Définit l'atout pour ce jeu.
     * <ul>
     * <li>la première carte du paquet est piochée, c'est l'atout potentiel.</li>
     * <li>chaque joueur dit si cette carte lui convient comme atout ou non (s'arrête
     * au premier "oui" prononcé).</li>
     * <li>si tous les joueurs ont refusés la carte proposée, chaque joueur choisi
     * une couleur d'atout ou passe (s'arrête au premier choix émis).</li>
     * <li>si personne n'a choisi d'atout la partie est annulée.</li>
     * </ul>
     * @see Jeu#atout
     */
    public void définirAtout() {
        
        System.out.println("Choix de l'atout :");
        
        boolean atoutChoisi = false;
        // pioche la première carte
        Carte atoutPioché = paquet.get(0);
        paquet.remove(0);
        System.out.println("carte piochée : "+ atoutPioché.toString()+"\n");

        // interroge les joueurs (en commençant par celui suivant le dealer)
        int nbJ = joueurs.size();
        for(int i = 1; i <= nbJ; i++) {
            Joueur joueurCourant = joueurs.get((dealer + i) % nbJ);
            if(joueurCourant.accepteAtout(atoutPioché)) {
                atout = atoutPioché.getFamille();
                atoutChoisi = true;
                break;
            }     
        }
        // remet la carte à la fin du paquet
        paquet.add(atoutPioché);
        
        // 2e tour : on demande à chaque joueur la couleur qu'il veut pour l'atout
        if(!atoutChoisi) {
            System.out.println("\n2e tour : ");
            for(int i = 1; i <= nbJ; i++) {
                if (atoutChoisi == true) break;
                Joueur joueurCourant = joueurs.get((dealer + i) % nbJ);
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
    
    /**
     * <p>Détermine si il reste encore des cartes à jouer. </p>
     * Prend en compte les cartes dans le paquet ainsi que celles dans les mains des joueurs.
     * @return vrai ou faux selon si il reste des cartes ou non.
     */
    public boolean resteDesCartes() {
        // si il reste des cartes dans le paquet
        if(!paquet.isEmpty()) return true;
        // si les joueurs ont encore des cartes en main (au début d'un tour les joueurs 
        // ont tous le même nombre de cartes en main donc on ne teste que pour le premier)
        if(joueurs.get(0).getCartes().isEmpty() == false) return true;
        return false;
    }
    
    /**
     * Renvoie le joueur qui joue en premier pour ce tour
     * @return Premier joueur à jouer sur le tour en cours.
     */
    public int premierJoueur() {
        int i;
        for(i = 0; i < joueurs.size(); i++) {
            if(joueurs.get(i).getMain() == true) break; 
        }
        return i;
    }
    
    /* getters / setters */
    
    /**
     * Retourne l'atout du jeu.
     * @return l'atout du jeu.
     * @see Jeu#atout
     */
    public famille getAtout() {
        return this.atout;
    }
    
    /**
     * Met à jour l'atout du jeu.
     * @param atout le nouvel atout.
     * @see Jeu#atout
     */
    public void setAtout(famille atout) {
        this.atout = atout;
    }
    
    /**
     * Retourne le paquet de cartes du jeu.
     * @return le paquet de cartes du jeu.
     * @see Jeu#paquet
     */
    public ArrayList<Carte> getPaquet() {
        return this.paquet;
    }
    
    /**
     * Retourne les participants du jeu
     * @return les participants du jeu.
     * @see Jeu#joueurs
     */
    public ArrayList<Joueur> getJoueurs() {
        return this.joueurs;
    }
    
    /**
     * Retourne le dealer du jeu.
     * @return le dealer du jeu.
     * @see Jeu#dealer
     */
    public int getDealer() {
        return this.dealer;
    }
    
    /**
     * Met à jour le dealer du jeu.
     * @param dealer le nouveau dealer.
     * @see Jeu#dealer
     */
    public void setDealer(int dealer) {
        this.dealer = dealer;
    }
}