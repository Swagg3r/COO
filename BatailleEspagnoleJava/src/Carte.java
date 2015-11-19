public class Carte implements Comparable<Carte>{

    private typeCarte type;
    private famille nomFamille;

    /* Constructeur */
    
    public Carte(typeCarte type, famille nomFamille) {
        this.type = type;
        this.nomFamille = nomFamille;
    }

    /* get / set / add / remove */
    
    // type
    public typeCarte getType() {
        return this.type;
    }
    public void setType(typeCarte type) {
        this.type = type;
    }
    // nomFamille
    public famille getFamille() {
        return this.nomFamille;
    }
    public void setFamille(famille nomFamille) {
        this.nomFamille = nomFamille;
    }
    
    /* Fonctions */
    
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
    
    public void print() {
        System.out.println(this.type+" de "+this.nomFamille);
    }
    
    public String toString() {
        return this.type+" de "+this.nomFamille;
    }
}
