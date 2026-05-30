class Main {

    public static void main(String[] args) {
        Pokemon p1 = new Pokemon(25, "Pikachu", "pokedex_gen1.csv");
        Pokemon p2 = new Pokemon(6, "Dracaufeu", "pokedex_gen1.csv");

        System.out.println("=== COMBAT ===");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println("==============");

        while (p1.estVivant() && p2.estVivant()) {
            p1.attaque(p2);
            System.out.println("---");
        }

        if (p1.estVivant()) {
            System.out.println("\nVainqueur : " + p1.getNom() + " !");
        } else {
            System.out.println("\nVainqueur : " + p2.getNom() + " !");
        }
    }
}