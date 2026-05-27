import java.util.Scanner;

class Jeu {

    public static Plateau initialiserPlateau(String cheminCSV) {
        // TODO
        return new Plateau();
    }

    public static void jouer(Plateau plateau) {
        // TODO
    }

    public static int verifierVictoire(Plateau plateau, Pokemon mewtwoJ1, Pokemon mewtwoJ2) {
        // TODO
        return 0;
    }

    public static void main(String[] args) {
        Plateau plateau = initialiserPlateau("pokedex_gen1.csv");
        jouer(plateau);
    }
}
