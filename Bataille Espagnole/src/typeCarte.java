public enum typeCarte {
    As("As", 11, 13),
    Deux("Deux", 0, 1),
    Trois("Trois", 10, 12),
    Quatre("Quatre", 0, 2),
    Cinq("Cinq", 0, 3),
    Six("Six", 0, 4),
    Sept("Sept", 0, 5),
    Huit("Huit", 0, 6),
    Neuf("Neuf", 0, 7),
    Dix("Dix", 0, 8),
    Cavalier("Cavalier", 2, 9),
    Dame("Dame", 3, 10),
    Roi("Roi", 4, 11);
    
    private final String name;
    private final int value;
    private final int rank;
    
    public int getValue() {
        return value;
    }
    public int getRank() {
        return rank;
    }
    
    private typeCarte(String name, int value, int rank) {
        this.name = name;
        this.value = value;
        this.rank = rank;
    }
}
