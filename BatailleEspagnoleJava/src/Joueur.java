
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Joueur {

    private String nomJoueur;
    private int nbPoints;
    private boolean aLaMain;
    private boolean estDealer;
    private ArrayList<Carte> cartesEnMain;

    /* Constructeurs */
    
    /**
     * <p>Constructeur par default de Joueur. </p>
     * A la construction d'un joueur anonyme, ses attributs sont mis par default.
     * @see Joueur#nomJoueur
     * @see Joueur#nbPoints
     * @see Joueur#aLaMain
     * @see Joueur#estDealer
     * @see Joueur#cartesEnMain
     */
    public Joueur() {
        this.nomJoueur = "unnamed";
        this.nbPoints = 0;
        this.aLaMain = false;
        this.estDealer = false;
        this.cartesEnMain = new ArrayList<>();
    }
    
    /**
     * <p>Constructeur de Joueur. </p>
     * A la construction d'un joueur, ses attributs sont initialisés.
     * @param nom Le nom du joueur a créer
     * @see Joueur#nomJoueur
     * @see Joueur#nbPoints
     * @see Joueur#aLaMain
     * @see Joueur#cartesEnMain
     */
    public Joueur(String nom) {
        this.nomJoueur = nom;
        this.nbPoints = 0;
        this.aLaMain = false;
        this.cartesEnMain = new ArrayList<>();
    }
    
    /* get / set / add / remove */
    
    /**
     * Retourne le nom du joueur.
     * @return le nom du joueur.
     * @see Joueur#nomJoueur
     */
    public String getNomJoueur() {
        return this.nomJoueur;
    }
    /**
     * Modifie le nom du joueur.
     * @param nomJoueur le nom du joueur.
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
     * Modifie le nom du joueur.
     * @param nbPoints le nombre de points du joueur.
     * @see Joueur#nbPoints
     */
    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
    /**
     * Ajoute des points au joueur.
     * @param nbPoints le nombre de points à ajouté.
     * @see Joueur#nbPoints
     */
    public void addPoints(int nbPoints) {
        this.nbPoints += nbPoints;
    }
    /**
     * Retourne le statut aLaMain du joueur.
     * @return le statut aLaMain du joueur.
     * @see Joueur#aLaMain
     */
    public boolean getMain() {
        return this.aLaMain;
    }
    /**
     * Modifie le statut aLaMain du joueur.
     * @param aLaMain le statut du joueur.
     * @see Joueur#aLaMain
     */
    public void setMain(boolean aLaMain) {
        this.aLaMain = aLaMain;
    }
    /**
     * Retourne le statut Dealer du joueur.
     * @return le statut Dealer du joueur.
     * @see Joueur#estDealer
     */
    public boolean getDealer() {
        return this.estDealer;
    }
    /**
     * Modifie le statut Dealer du joueur.
     * @param estDealer le statut du joueur.
     * @see Joueur#estDealer
     * @see Joueur#aLaMain
     */
    public void setDealer(boolean estDealer) {
        this.estDealer = aLaMain;
    }
    /**
     * Ajoute une carte au joueur.
     * @param c la carte à ajouter.
     * @see Joueur#cartesEnMain
     */
    public void addCarte(Carte c) {
        cartesEnMain.add(c);
    }
    /**
     * Affiche les cartes du joueur.
     * @see Joueur#cartesEnMain
     */
    public void afficherCartes() {
        System.out.println("Vos cartes en main : ");
        for(int i = 0; i < cartesEnMain.size(); i++) {
            System.out.println((i+1) + ") " + cartesEnMain.get(i).toString());
        }
    }
    /**
     * Retourne une ArrayList avec les cartes que le joueur à en main.
     * @return les cartes que le joueur à en main.
     * @see Joueur#cartesEnMain
     */
    public ArrayList<Carte> getCartes() {
        return this.cartesEnMain;
    }
    /**
     * Modifie les cartes que le joueur à en main.
     * @param cartes les cartes que le joueur à en main.
     * @see Joueur#cartesEnMain
     */
    public void setCartes(ArrayList<Carte> cartes) {
        this.cartesEnMain = cartes;
    }
    
    /* Fonctions */
    
    /**
     * Affiche toute les cartes que le joueur à en main.
     * @see Joueur#cartesEnMain
     */
    public void print() {
        System.out.print(nomJoueur);
        if (!cartesEnMain.isEmpty()) {
            System.out.print(" : " + cartesEnMain.size()+" cartes\n");
            for(Carte c : cartesEnMain) {
                System.out.println(". " + c.toString());
            }
            System.out.println("");
        } else {
            System.out.print(", ");
        }
    }
    
    /**
     * Retire une carte que le joueur à en main.
     * @return la carte à jouer.
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
     * Ajoute une carte dans les mains du joueur.
     * @param paquet la pioche encore disponible.
     * @see Joueur#cartesEnMain
     * @see Partie#paquet
     */
    public void piocher(ArrayList<Carte> paquet) {
        Carte c = paquet.get(0);
        paquet.remove(0);
        cartesEnMain.add(c);
    }
    
    /**
     * Retourne le choix du joueur pour l'atout proposé.
     * @param c la carte piochée dans le paquet restant.
     * @return le choix du joueur
     * @see Partie#paquet
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
     * Retourne le choix de l'atout par le joueur.
     * @return l'atout choisit par le joueur.
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
    
    /*public String toString() {
        String str = " . "+nomJoueur;
        if (cartesEnMain.size() != 0) {
            str += " : "+cartesEnMain.size()+" cartes\n";
            for(int i = 0; i < cartesEnMain.size(); i++) {
              str += cartesEnMain.get(i).toString()+"\n";
            }
        }
        return str;
    }*/
}
