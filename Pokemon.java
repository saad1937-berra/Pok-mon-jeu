import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Pokemon {

    private int numPokedex;
    private String nom;
    private int type1;
    private int type2;
    private int pv;
    private int pvMax;
    private int att;
    private int def;
    private int vit;

    // Constructeur vide — valeurs par défaut
    public Pokemon() {
        this.numPokedex = 0;
        this.nom = "Inconnu";
        this.type1 = Type.NORMAL;
        this.type2 = Type.SANS;
        this.pv = 1;
        this.pvMax = 1;
        this.att = 1;
        this.def = 1;
        this.vit = 1;
    }

    // Constructeur principal avec tous les paramètres
    public Pokemon(int numPokedex, String nom, int type1, int type2, int pv, int att, int def, int vit) {
        this.numPokedex = numPokedex;
        this.nom = nom;
        this.type1 = type1;
        this.type2 = type2;
        this.pv = pv;
        this.pvMax = pv;  // pvMax = pv initial
        this.att = att;
        this.def = def;
        this.vit = vit;
    }

    // Constructeur CSV : charge les stats depuis le fichier en cherchant numPokedex
    public Pokemon(int numPokedex, String nom, String cheminCSV) {
        this.numPokedex = numPokedex;
        this.nom = nom;
        // Valeurs par défaut en cas d'échec
        this.type1 = Type.NORMAL;
        this.type2 = Type.SANS;
        this.pv = 1; this.pvMax = 1;
        this.att = 1; this.def = 1; this.vit = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(cheminCSV))) {
            String ligne;
            br.readLine(); // sauter l'en-tête
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(",");
                if (parts.length < 8) continue;
                int num = Integer.parseInt(parts[0].trim());
                if (num == numPokedex) {
                    this.type1 = Type.getIndiceType(parts[2].trim());
                    this.type2 = Type.getIndiceType(parts[3].trim());
                    this.pv    = Integer.parseInt(parts[4].trim());
                    this.pvMax = this.pv;
                    this.att   = Integer.parseInt(parts[5].trim());
                    this.def   = Integer.parseInt(parts[6].trim());
                    this.vit   = Integer.parseInt(parts[7].trim());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lecture CSV : " + e.getMessage());
        }
    }

    public int getNumPokedex() { return numPokedex; }
    public String getNom()     { return nom; }
    public int getType1()      { return type1; }
    public int getType2()      { return type2; }
    public int getPv()         { return pv; }
    public int getPvMax()      { return pvMax; }
    public int getAtt()        { return att; }
    public int getDef()        { return def; }
    public int getVit()        { return vit; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPv(int pv)      { this.pv = pv; }

    // Vivant = PV strictement positifs
    public boolean estVivant() {
        return pv > 0;
    }

    @Override
    public String toString() {
        return String.format("%s (n°%d) | Type: %s/%s | PV: %d/%d | ATT: %d | DEF: %d | VIT: %d",
            nom, numPokedex,
            Type.getNomType(type1), Type.getNomType(type2),
            pv, pvMax, att, def, vit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pokemon)) return false;
        Pokemon autre = (Pokemon) obj;
        return this.numPokedex == autre.numPokedex;
    }

    // Calcul des dégâts avec efficacité de type
    // Formule : degats = (att / def) * efficacite(type1Att, type1Def)
    public void attaque(Pokemon adversaire) {
        // Efficacité du type1 attaquant contre les deux types du défenseur
        double efficacite1 = Type.getEfficacite(this.type1, adversaire.getType1());
        double efficacite2 = (adversaire.getType2() != Type.SANS)
                             ? Type.getEfficacite(this.type1, adversaire.getType2())
                             : 1.0;

        double multiplicateur = efficacite1 * efficacite2;

        // Affichage du message d'efficacité
        if (multiplicateur == 0.0) {
            System.out.println("Ça n'affecte pas " + adversaire.getNom() + "...");
        } else if (multiplicateur > 1.0) {
            System.out.println("C'est super efficace !");
        } else if (multiplicateur < 1.0) {
            System.out.println("Ce n'est pas très efficace...");
        }

        // Calcul des dégâts (minimum 1 si l'attaque touche)
        if (multiplicateur > 0.0) {
            int degats = (int) Math.max(1, (this.att / (double) adversaire.getDef()) * 10 * multiplicateur);
            int nouveauxPv = Math.max(0, adversaire.getPv() - degats);
            adversaire.setPv(nouveauxPv);
            System.out.printf("%s attaque %s — %d dégâts (PV: %d → %d)%n",
                this.nom, adversaire.getNom(), degats, adversaire.getPv() + degats, nouveauxPv);
        }
    }
}