
import java.util.ArrayList;

public class Partie {

    private int nbMaxPoints;
    private int nbJeuxMax;
    private Joueur vainqueur;
    private final ArrayList<Jeu> jeux;
    private final ArrayList<Joueur> participants;
    private final ArrayList<Carte> paquet;

    /* Constructeurs */
    
    public Partie() {
        this.vainqueur = null;
        this.jeux = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.paquet = creerPaquet();
        this.nbMaxPoints = 300;
        this.nbJeuxMax = 10;
    }
    
    public Partie(int maxPoints, int nbJeuxMax) {
        this();
        this.nbMaxPoints = maxPoints;
        this.nbJeuxMax = nbJeuxMax;
    }
    
    /* get / set / add / remove */
        
    // maxPoints
    public int getMaxPoints() {
        return this.nbMaxPoints;
    }
    public void setMaxPoints(int maxPoints) {
        this.nbMaxPoints = maxPoints;
    }
    // vainqueur
    public Joueur getVainqueur() {
        return this.vainqueur;
    }
    public void setVainqueur(Joueur vainqueur) {
        this.vainqueur = vainqueur;
    }
    // jeux
    public void nouveauJeu() {
        jeux.add(new Jeu(paquet, participants));
        Jeu jeuCourant = jeux.get(jeux.size()-1);
        jeuCourant.lancer();
    }
    public void removeJeu(Jeu j) {
        this.jeux.remove(j);
    }
    public Jeu jeuEnCours() {
        return this.jeux.get(this.jeux.size()-1);
    }
    // participants
    public void addJoueur(Joueur j) {
        if(this.participants.size() == 4) {
            System.out.println("Impossible d'ajouter " + j.getNomJoueur());
            System.out.println("(le nombre limite de joueur a été atteint)");
            return;
        }
        this.participants.add(j);
    }
    public void removeJoueur(Joueur j) {
        this.participants.remove(j);
    }
    public ArrayList<Joueur> getParticipants() {
        return this.participants;
    }
    // paquet
    public ArrayList<Carte> getPaquet() {
        return this.paquet;
    }
    
    /* Fonctions */
    
    public void lancer() {
        
        System.out.println("Lancement de la partie...\n");
        int nbJeu = 0;
        while(!victoire(nbJeu)) {
            System.out.println("===================[ Jeu n°"+(nbJeu+1)+" ]==================\n");
            nouveauJeu();
            System.out.println("================================================\n");
            nbJeu++;
        }
        determinerVainqueur();
    }
    
    public boolean victoire(int nbPartieActuel) {
        //condition 1 : nombre max de partie atteint
        if (nbJeuxMax != 0 && nbPartieActuel == nbJeuxMax) return true;
        
        if(nbMaxPoints != 0) {
            for(Joueur j : participants) {
                if (j.getNbPoints() >= nbMaxPoints) return true;
            }
        }
        return false;
    }
    
    public void determinerVainqueur() {
        Joueur vainqueur = participants.get(0);
        for(Joueur j : participants) {
            if (j.getNbPoints() >= vainqueur.getNbPoints()) {
                vainqueur = j;
            }
        }
        System.out.println(vainqueur.getNomJoueur()+" a gagné la partie avec " + vainqueur.getNbPoints() + " points !");
        
    }
        
    public ArrayList<Carte> creerPaquet() {
        //création des cartes (dans l'ordre)
        ArrayList paquetRangé = new ArrayList<Carte>();
        
        for (famille couleur : famille.values()) {
            for (typeCarte valeur : typeCarte.values()) {
                paquetRangé.add(new Carte(valeur, couleur));
            }
        }
        return paquetRangé;
    }
    
    public void print() {
        System.out.println("-------[ Partie courante ]-------");
        System.out.println("- " + participants.size() + " participants :");
        for(Joueur j : participants) {
            j.print();
        }
        System.out.println();
        System.out.println("- Nombre de points à atteindre : "+nbMaxPoints);
        System.out.println("- "+(vainqueur == null ? "Partie en cours" : "Vainqueur de la partie : "+vainqueur));
        System.out.println("---------------------------------\n");
    }
    
    /*public String toString() {
        String str = "-------[ Partie courante ]-------\n\n";
        
        str += "-> " + participants.size() + " participants :\n\n";
        for(int i = 0; i < participants.size(); i++) {
            str += participants.get(i).toString();
        }
        str += "\nNombre de points à atteindre : "+maxPoints+"\n";
        str += "\n"+(vainqueur == null ? "Partie en cours" : "Vainqueur de la partie : "+vainqueur)+"\n";
        
        return str;
    }*/
}
