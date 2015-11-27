
/**
 * <b>Carte est la classe qui représente une carte. </b>
 * <p>Une carte est définie par :</p>
 * <ul>
 * <li>son type (2, 3, 4.. As).</li>
 * <li>sa famille (Bâton, Coupe, Or ou Epée).</li>
 * </ul>
 * <p>Cette classe implémente l'interface Comparable.</p>
 * 
 * @see famille
 * @see typeCarte
 * 
 * @author Aurélien MONNET-PAQUET, Antoine THEBAUD
 */

public class Carte implements Comparable<Carte> {

    /**
     * Le type de la carte (2, 3, 4.. As).
     * @see Carte#setType()
     * @see Carte#getType()
     */
    private typeCarte type;
    
    /**
     * La famille de la carte (Bâton, Coupe, Or ou Epée).
     * @see Carte#setFamille()
     * @see Carte#getFamille()
     */
    private famille nomFamille;

    /**
     * <p>Constructeur de Carte. </p>
     * A la construction d'un objet Carte, le type et la famille sont initialisés avec les valeurs passées en paramètres.
     * @param type le type de la carte.
     * @param nomFamille la famille de la carte.
     * @see Carte#nomFamille
     * @see Carte#type
     */
    public Carte(typeCarte type, famille nomFamille) {
        this.type = type;
        this.nomFamille = nomFamille;
    }
    
    /**
     * <p>Compare la carte avec une autre carte. </p>
     * Cette fonction est une implémentation de la fonction compareTo() de l'interface Comparable.
     * La comparaison se fait sur le type de la carte.
     * @param other la carte à confronter.
     * @return -1 si l'autre carte est plus forte, 1 si elle est moins forte, 0 si elle est de puissance égale
     * @see typeCarte
     */
    @Override
    public int compareTo(Carte other){
        if (this.type.getRank() > other.type.getRank()) {
            return 1;
        } else if (this.type.getRank() == other.type.getRank()) {
            return 0;
        } else {
            return -1;
        }
    }
    
    /**
     * Affiche la carte (son type et sa famille).
     */
    public void print() {
        System.out.println(this.type+" de "+this.nomFamille);
    }
    /**
     * Retourne les informations sur la carte sous forme de chaine de caractère.
     * @return les informations sur la carte (String)
     */
    public String toString() {
        return this.type+" de "+this.nomFamille;
    }
    
    /**
     * Retourne le type de la carte.
     * @return le type de la carte.
     * @see Carte#type
     */
    public typeCarte getType() {
        return this.type;
    }
    
    /**
     * Met à jour le type de la carte.
     * @param type le nouveau type.
     * @see Carte#type
     */
    public void setType(typeCarte type) {
        this.type = type;
    }
    
    /**
     * Retourne la famille de la carte.
     * @return la famille de la carte.
     * @see Carte#nomFamille
     */
    public famille getFamille() {
        return this.nomFamille;
    }
    
    /**
     * Met à jour la famille de la carte.
     * @param nomFamille la nouvelle famille.
     * @see Carte#nomFamille
     */
    public void setFamille(famille nomFamille) {
        this.nomFamille = nomFamille;
    }
}
