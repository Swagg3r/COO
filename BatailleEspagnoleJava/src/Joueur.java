
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
    
    public Joueur() {
        this.nomJoueur = "unnamed";
        this.nbPoints = 0;
        this.aLaMain = false;
        this.estDealer = false;
        this.cartesEnMain = new ArrayList<>();
    }
    
    public Joueur(String nom) {
        this.nomJoueur = nom;
        this.nbPoints = 0;
        this.aLaMain = false;
        this.cartesEnMain = new ArrayList<>();
    }
    
    /* get / set / add / remove */
    
    // nomJoueur
    public String getNomJoueur() {
        return this.nomJoueur;
    }
    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }
    // nbPoints
    public int getNbPoints() {
        return this.nbPoints;
    }
    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
    public void addPoints(int nbPoints) {
        this.nbPoints += nbPoints;
    }
    // aLaMain
    public boolean getMain() {
        return this.aLaMain;
    }
    public void setMain(boolean aLaMain) {
        this.aLaMain = aLaMain;
    }
    // estDealer
    public boolean getDealer() {
        return this.estDealer;
    }
    public void setDealer(boolean estDealer) {
        this.estDealer = aLaMain;
    }
    // cartesEnMain
    public void addCarte(Carte c) {
        cartesEnMain.add(c);
    }
    public void afficherCartes() {
        System.out.println("Vos cartes en main : ");
        for(int i = 0; i < cartesEnMain.size(); i++) {
            System.out.println((i+1) + ") " + cartesEnMain.get(i).toString());
        }
    }
    public ArrayList<Carte> getCartes() {
        return this.cartesEnMain;
    }
    public void setCartes(ArrayList<Carte> cartes) {
        this.cartesEnMain = cartes;
    }
    
    /* Fonctions */
    
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
    
    public void piocher(ArrayList<Carte> paquet) {
        Carte c = paquet.get(0);
        paquet.remove(0);
        cartesEnMain.add(c);
    }
    
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
