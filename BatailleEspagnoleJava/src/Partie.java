
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <b>Partie est la classe représentant une partie de bataille espagnole. </b>
 * <p>Une partie est enchaînement de jeu qui se termine lorsque la condition de victoire de la partie est atteinte.
 * Elle est caractérisée par les informations suivantes :</p>
 * <ul>
 * <li>Un nombre maximum de points à atteindre.
 * Ce paramètre est désactivé si l'utilisateur a souhaité terminer la partie en un certain nombre de jeux.</li>
 * <li>Un nombre maximum de jeux.
 * Ce paramètre est désactivé si l'utilisateur a souhaité terminer la partie lorqu'un certain nombre de points est atteint.</li>
 * <li>Un vainqueur (le joueur ayant le plus de points au moment de l'arrêt de la partie).</li>
 * <li>Une liste de jeux.</li>
 * <li>Une liste de joueurs, les participants.</li>
 * <li>Un paquet de cartes.</li>
 * </ul>
 * 
 * @see Jeu
 * @see Carte
 * @see Joueur
 * 
 * @author Aurélien MONNET-PAQUET, Antoine THEBAUD
 */
public class Partie {

    /**
     * <p>Le nombre maximum de points à atteindre. </p>
     * Ce paramètre est désactivé si l'utilisateur a souhaité terminer la partie en un certain nombre de jeux.
     * @see Partie#getMaxPoints()
     * @see Partie#setMaxPoints()
     */
    
    private int nbMaxPoints;
    /**
     * <p>Le nombre de jeux que comporte la partie. </p>
     * Ce paramètre est désactivé si l'utilisateur a souhaité terminer la partie lorqu'un certain nombre de points est atteint.
     * @see Partie#getNbJeux()
     * @see Partie#setNbJeux()
     */
    
    private int nbJeuxMax;
    /**
     * Le vainqueur de la partie. La valeur de cet attribut est déterminé à la fin de la partie.
     * @see Partie#getVainqueur()
     * @see Partie#setVainqueur()
     */
   
    private Joueur vainqueur;
    /**
     * La liste de jeux que la partie contient.
     * @see Partie#getJeux()
     * @see Partie#setJeux()
     */
    private ArrayList<Jeu> jeux;
    
    /**
     * La liste des joueurs de la partie.
     * @see Partie#getParticipants()
     * @see Partie#setParticipants()
     */
    private ArrayList<Joueur> participants;
    
    /**
     * Le paquet de carte de la partie.
     * @see Partie#getPaquet()
     * @see Partie#setPaquet()
     */
    private ArrayList<Carte> paquet;

    /**
     * <p>Constructeur de Partie. </p>
     * A la construction d'un objet Partie, la condition de victoire est initialisée suivant le mode.
     * <ul>
     * <li>mode POINTS : l'attribut nbMaxPoints est initialisé avec la valeur saisie par l'utilisateur.</li>
     * <li>mode JEUX : l'attribut nbJeux est initialisé avec la valeur saisie par l'utilisateur.</li>
     * <li>mode DEFAUT : l'attribut nbMaxPoints est initialisé avec la valeur 200.</li>
     * </ul>
     * <p>Le paquet de carte est créé mais la liste des jeux et la liste de joueur ne sont pas initialisées ici.</p>
     * @param value La valeur du paramètre de victoire (points ou jeux)
     * @param mode le mode de la partie, qui détermine la condition de victoire
     * @see Partie#nbMaxPoints
     * @see Partie#nbJeuxMax
     * @see Partie#jeux
     * @see Partie#participants
     * @see Partie#paquet
     */
    public Partie(int value, String mode) {
        this.vainqueur = null;
        this.jeux = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.paquet = creerPaquet();
        
        if(mode.toUpperCase().equals("P")) {
            this.nbMaxPoints = value;
        } else if(mode.toUpperCase().equals("J")) {
            this.nbJeuxMax = value;
        } else if(mode.toUpperCase().equals("D")) {
            this.nbMaxPoints = 200;
        }
    }
    
    /**
     * Créé le paquet de cartes de la partie. Les cartes ne sont pas mélangées.
     * @return Un paquet de cartes rangé.
     * @see Partie#paquet
     */
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
    
    /**
     * <p>Créé la liste des joueurs qui prennent part à la partie. </p>
     * Cette fonction interdit d'avoir 2 joueurs du même nom, ainsi que d'avoir moins de 2 joueurs ou plus de 4.
     * @see Partie#participants
     */
    public void creerJoueurs() {
        
        String nomJoueur;
        Scanner sc = new Scanner(System.in);
        while(participants.size() < 4) {
            System.out.println("-> Saisir un nom de joueur à ajouter à la partie :");
            System.out.println("(Saisir \"stop\" pour arrêter la saisie)");
            nomJoueur = sc.nextLine();
            try {
                //exception : pas assez de joueurs
                if(nomJoueur.toUpperCase().equals("STOP")) {
                    if(participants.size() <= 1) {
                        throw new Exception("Impossible d'arrêter la saisie : il faut au moins deux joueurs");
                    } else break;
                }
                //exception : joueur déjà existant
                for (Joueur j : participants) {
                    if(j.getNomJoueur().equals(nomJoueur)) {
                        throw new Exception("Impossible d'ajouter " + nomJoueur + " : ce nom est déjà pris pas un autre joueur");
                    }
                }
                this.participants.add(new Joueur(nomJoueur));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * <p>Lance la partie et la déroule jusqu'à son terme. </p>
     * les jeux s'enchaînent tant que la condition d'arrêt n'a pas été atteinte.
     * Le vainqueur est déterminé lorsque la partie s'achève.
     * @see Partie#vainqueur
     */
    public void lancer() {
        
        System.out.println("Lancement de la partie...\n");
        int nbJeu = 0;
        while(!victoire(nbJeu)) {
            System.out.println("===================[ Jeu n°"+(nbJeu+1)+" ]==================\n");
            if(nbJeu != 0) {
                System.out.println("Score actuel des joueurs :");
                for(Joueur j : participants) {
                    System.out.println(j.getNomJoueur()+ " : " + j.getNbPoints());
                }
                System.out.println();
            }
            nouveauJeu();
            System.out.println("================================================\n");
            nbJeu++;
        }
        determinerVainqueur();
    }
    
    /**
     * <p>Détermine si la condition de victoire est atteinte. </p>
     * cette fonction évalue nbJeuxMax ou nbPoints selon le mode choisi par l'utilisateur à l'origine.
     * @param nbJeuxActuel le nombre de jeu actuel effectué dans la partie.
     * @return vrai ou faux selon si la condition de victoire est satisfaite
     * @see Partie#nbJeuxMax
     * @see Partie#nbMaxPoints
     */
    public boolean victoire(int nbJeuxActuel) {
        //condition 1 : nombre max de partie atteint
        if (nbJeuxMax != 0 && nbJeuxActuel == nbJeuxMax) return true;
        
        if(nbMaxPoints != 0) {
            for(Joueur j : participants) {
                if (j.getNbPoints() >= nbMaxPoints) return true;
            }
        }
        return false;
    }
    
    /**
     * Identifie le vainqueur de la partie.
     * @see Partie#participants
     * @see Partie#vainqueur
     */
    public void determinerVainqueur() {
        Joueur vainqueur = participants.get(0);
        for(Joueur j : participants) {
            if (j.getNbPoints() >= vainqueur.getNbPoints()) {
                vainqueur = j;
            }
        }
        System.out.println(vainqueur.getNomJoueur()+" a gagné la partie avec " + vainqueur.getNbPoints() + " points !");   
    }
    
    /**
     * Créé un nouveau jeu et le lance.
     * Cette fonction est appellée à chaque tour de la boucle dans Partie.lancer().
     */
    public void nouveauJeu() {
        jeux.add(new Jeu(paquet, participants));
        Jeu jeuCourant = jeux.get(jeux.size()-1);
        jeuCourant.lancer();
    }
    
    /**
     * Retourne le nombre maximum de points à atteindre.
     * @return le nombre maximum de points à atteindre (paramètre désactivé si il vaut 0).
     * @see Partie#nbMaxPoints
     */
    public int getMaxPoints() {
        return this.nbMaxPoints;
    }
    
    /**
     * Met à jour le nombre maximum de points à atteindre.
     * @param maxPoints le nouveau nombre maximum de points à atteindre.
     * @see Partie#nbMaxPoints
     */
    public void setMaxPoints(int maxPoints) {
        this.nbMaxPoints = maxPoints;
    }
    
    /**
     * Retourne le nombre de jeux que comporte la partie.
     * @return le nombre maximum de jeux que comporte la partie.
     * @see Partie#nbJeuxMax
     */
    public int getNbJeux() {
        return this.nbJeuxMax;
    }
    
    /**
     * Met à jour le de jeux que comporte la partie.
     * @param nbJeuxMax le nouveau nombre maximum de jeux de la partie.
     * @see Partie#nbJeuxMax
     */
    public void setNbJeux(int nbJeuxMax) {
        this.nbJeuxMax = nbJeuxMax;
    }
    
    /**
     * Retourne le vainqueur de la partie.
     * @return le vainqueur de la partie.
     * @see Partie#vainqueur
     */
    public Joueur getVainqueur() {
        return this.vainqueur;
    }
    
    /**
     * Met à jour le vainqueur de la partie.
     * @param vainqueur le nouveau vainqueur de la partie.
     * @see Partie#vainqueur
     */
    public void setVainqueur(Joueur vainqueur) {
        this.vainqueur = vainqueur;
    }
    
    /**
     * Retourne la liste des jeux de la partie.
     * @return la liste des jeux de la partie.
     * @see Partie#jeux
     */
    public ArrayList<Jeu> getJeux() {
        return this.jeux;
    }
    
    /**
     * Met à jour la liste des jeux de la partie.
     * @param jeux la nouvelle liste de jeux de la partie.
     * @see Partie#jeux
     */
    public void setJeux(ArrayList<Jeu> jeux) {
        this.jeux = jeux;
    }
    
    /**
     * Retourne la liste des joueurs de la partie.
     * @return la liste des joueurs de la partie.
     * @see Partie#participants
     */
    public ArrayList<Joueur> getParticipants() {
        return this.participants;
    }
    
    /**
     * Met à jour la liste des joueurs de la partie.
     * @param joueurs la nouvelle liste de joueurs de la partie.
     * @see Partie#participants
     */
    public void setParticipants(ArrayList<Joueur> joueurs) {
        this.participants = joueurs;
    }
    
    /**
     * Retourne le paquet de cartes de la partie.
     * @return le paquet de cartes de la partie.
     * @see Partie#paquet
     */
    public ArrayList<Carte> getPaquet() {
        return this.paquet;
    }
    
    /**
     * Met à jour le paquet de cartes de la partie.
     * @param paquet le nouveau paquet de cartes de la partie.
     * @see Partie#paquet
     */
    public void setPaquet(ArrayList<Carte> paquet) {
        this.paquet = paquet;
    }
}
