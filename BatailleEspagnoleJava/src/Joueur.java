
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * <b>Joueur est la classe représentant un joueur de bataille espagnole.</b>
 * <p>Un joueur prend part à une partie de bataille espagnole et participe aux
 * jeux qu'elle contient. Il caractérisé par les informations suivantes :</p>
 * <ul>
 * <li>Son nom.</li>
 * <li>Le nombre de points qu'il a gagné.</li>
 * <li>un booléen indiquant si c'est ce joueur qui a actuellement la main ou non.</li>
 * <li>un booléen indiquant si c'est ce joueur qui est actuellement le dealer ou non.</li>
 * <li>une liste de cartes (les cartes qu'il a en main).</li>
 * </ul>
 * 
 * @see Partie
 * @see Jeu
 * @see Carte
 * 
 * @author Aurélien MONNET-PAQUET, Antoine THEBAUD
 */

public class Joueur {

    /**
     * Le nom du joueur.
     * @see Joueur#getNom()
     * @see Joueur#setNom()
     */
    private String nomJoueur;
    
    /**
     * Le nombre de points.
     * @see Joueur#getPoints()
     * @see Joueur#setPoints()
     * @see Joueur#addPoints()
     */
    private int nbPoints;
    
    /**
     * Booléen indiquant au joueur si il a actuellement la main ou non.
     * @see Joueur#getMain()
     * @see Joueur#setMain()
     */
    private boolean aLaMain;
    
    /**
     * Booléen indiquant au joueur si il est actuellement le dealer.
     * @see Joueur#setDealer()
     * @see Joueur#getDealer()
     */
    private boolean estDealer;
    
    /**
     * La liste des cartes en main.
     * @see Joueur#getCartes()
     * @see Joueur#setCartes()
     * @see Joueur#addCarte()
     * @see Joueur#jouerCarte()
     */
    private ArrayList<Carte> cartesEnMain;

    /**
     * <p>Constructeur de Joueur. </p>
     * A la construction d'un objet Joueur, le nom passé en paramètre est attribué au joueur.
     * Les autres attributs sont initialisés à 0 (false pour les booléens).
     * @param nom le nom à attribuer au joueur créé.
     * @see Joueur#nomJoueur
     * @see Joueur#nbPoints
     * @see Joueur#aLaMain
     * @see Joueur#estDealer
     * @see Joueur#cartesEnMain
     */
    public Joueur(String nom) {
        this.nomJoueur = nom;
        this.nbPoints = 0;
        this.aLaMain = false;
        this.estDealer = false;
        this.cartesEnMain = new ArrayList<>();
    }
    
    /**
     * <p>Joue une carte parmi celles en main.</p>
     * L'index (dans la liste) de la carte à jouer est saisi au clavier. Les
     * index invalides sont gérés. La carte jouée est envoyée à l'appelant.
     * @return la carte jouée.
     * @see Joueur#cartesEnMain
     */
    public Carte jouerCarte() {
        //choix de la carte à jouer
        afficherCartes();
        System.out.println("Quelle carte voulez-vous jouer ?");
        
        Scanner sc = new Scanner(System.in);
        String str = "";
        boolean valid = false;
        while (!valid) {
            try {
                str = sc.nextLine();
                if(Integer.parseInt(str) > cartesEnMain.size()) throw new Exception();
                valid = true;
            } catch (Exception e) {
                System.out.println("(Vous devez saisir un index de carte valide !)");
            }
        }
        //la carte jouée est retirée de la main du joueur
        int index = Integer.parseInt(str) - 1;
        Carte carteJouee = cartesEnMain.get(index);
        cartesEnMain.remove(index);
        
        return carteJouee;
    }
    
    /**
     * <p>Pioche une carte dans le paquet passé en paramètre.</p>
     * @param paquet le paquet dans lequel piocher.
     * @see Joueur#cartesEnMain
     */
    public void piocher(ArrayList<Carte> paquet) {
        Carte c = paquet.get(0);
        paquet.remove(0);
        cartesEnMain.add(c);
    }
    
    /**
     * <p>Demande au joueur si la carte en paramètre lui convient comme couleur d'atout.</p>
     * @param c la carte proposée comme atout.
     * @return vrai ou faux selon si le joueur a accepté l'atout ou non.
     * @see Jeu#atout
     */
    public boolean accepteAtout(Carte c) {
        System.out.println(nomJoueur + ", est-ce-que " + c.getFamille() + " vous convient comme atout ?");
        afficherCartes();
        
        Scanner sc = new Scanner(System.in);
        String str;
        do {
            System.out.println("Vous devez saisir oui(o) ou non(n) : ");
            str = sc.nextLine();
        } while (!str.toUpperCase().equals("O") && !str.toUpperCase().equals("N"));
        
        return (str.toUpperCase().equals("O") ? true : false);
    }
    
    /**
     * <p>Demande au joueur de choisir une couleur d'atout.</p>
     * Le joueur a le choix entre les 4 couleurs. Il peut aussi passer son tour.
     * @return une String indiquant la couleur choisie.
     * @see Jeu#atout
     */
    public String choisirAtout() {
        System.out.println(nomJoueur+", quel atout choisissez-vous ?");
        Scanner sc = new Scanner(System.in);
        String str;
        do {
            System.out.println("Vous devez choisir entre \n- Coupe(C)\n- Bâton(B)\n- Or(O)\n- Epée(E)\n- Passer votre tour(P): ");
            str = sc.nextLine();
        } while (!str.toUpperCase().equals("C")
            && !str.toUpperCase().equals("B")
            && !str.toUpperCase().equals("O")
            && !str.toUpperCase().equals("E")
            && !str.toUpperCase().equals("P"));
        return str.toUpperCase();
    }
    
    /**
     * Affiche les cartes du joueur.
     */  
    public void afficherCartes() {
        System.out.println("Vos cartes en main : ");
        for(int i = 0; i < cartesEnMain.size(); i++) {
            System.out.println((i+1) + ") " + cartesEnMain.get(i).toString());
        }
    }
    
    /**
     * Retourne le nom du joueur.
     * @return le nom du joueur.
     * @see Joueur#nomJoueur
     */
    public String getNomJoueur() {
        return this.nomJoueur;
    }
    
    /**
     * Met à jour le nom du joueur.
     * @param nomJoueur le nouveau nom du joueur.
     * @see Joueur#nomJoueur
     */
    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }
    
    /**
     * Retourne le nombre de points du joueur.
     * @return le nombre de points du joueur.
     * @see Joueur#nbPoints
     */
    public int getNbPoints() {
        return this.nbPoints;
    }
    
    /**
     * Met à jour le nombre de points du joueur.
     * @param nbPoints le nouveau nombre de points du joueur.
     * @see Joueur#nbPoints
     */
    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
    
    /**
     * Ajoute un certain nombre de points au joueur.
     * @param nbPoints le nombre de points à ajouter au joueur.
     * @see Joueur#nbPoints
     */
    public void addPoints(int nbPoints) {
        this.nbPoints += nbPoints;
    }
    
    /**
     * Retourne vrai ou faux selon si le joueur a la main ou non.
     * @return vrai ou faux selon si le joueur a la main ou non.
     * @see Joueur#aLaMain
     */
    public boolean getMain() {
        return this.aLaMain;
    }
    
    /**
     * Donne ou retire la main au joueur.
     * @param aLaMain vrai si on veut donner la main au joueur, faux si on veut la lui retirer.
     * @see Joueur#aLaMain
     */
    public void setMain(boolean aLaMain) {
        this.aLaMain = aLaMain;
    }
    
    /**
     * Retourne vrai ou faux selon si le joueur est le dealer du jeu courant.
     * @return vrai ou faux selon si le joueur est le dealer du jeu courant.
     * @see Joueur#estDealer
     */
    public boolean getDealer() {
        return this.estDealer;
    }
    
    /**
     * Défini le joueur comme dealer ou lui enlève ce statut.
     * @param estDealer vrai si on veut définir le joueur comme dealer, faux si
     * on veut lui enlever ce statut.
     * @see Joueur#estDealer
     */
    public void setDealer(boolean estDealer) {
        this.estDealer = estDealer;
    }
   
    /**
     * Retourne la liste des cartes du joueur.
     * @return la liste des cartes du joueur.
     * @see Joueur#cartesEnMain
     */
    public ArrayList<Carte> getCartes() {
        return this.cartesEnMain;
    }
    
    /**
     * Met à jour la liste des cartes dans la main du joueur.
     * @param cartes la nouvelle liste de cartes du joueur.
     * @see Joueur#cartesEnMain
     */
    public void setCartes(ArrayList<Carte> cartes) {
        this.cartesEnMain = cartes;
    }
    
    /**
     * Ajouter une carte dans la main du joueur.
     * @param c la nouvelle carte à ajouter.
     * @see Joueur#cartesEnMain
     */
    public void addCarte(Carte c) {
        cartesEnMain.add(c);
    }
}
