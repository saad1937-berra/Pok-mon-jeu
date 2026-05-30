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

    public Pokemon() {
        this.numPokedex = 0;
        this.nom = "Missingno";
        this.type1 = Type.NORMAL;
        this.type2 = Type.SANS;
        this.pv = 1;
        this.pvMax = 1;
        this.att = 0;
        this.def = 0;
        this.vit = 0;
    }

    public Pokemon(int numPokedex, String nom, int type1, int type2, int pv, int att, int def, int vit) {
        this.numPokedex = numPokedex;
        this.nom = nom;
        this.type1 = type1;
        this.type2 = type2;
        this.pv = pv;
        this.pvMax = pv;
        this.att = att;
        this.def = def;
        this.vit = vit;
    }

    public Pokemon(int numPokedex, String nom, String cheminCSV) {
        this.numPokedex = numPokedex;
        this.nom = nom;
        try {
            BufferedReader br = new BufferedReader(new FileReader(cheminCSV));
            String line;
            br.readLine(); // sauter l'en-tête
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (Integer.parseInt(parts[0]) == numPokedex) {
                    this.type1 = Type.getIndiceType(parts[2]);
                    this.type2 = parts[3].isEmpty() ? Type.SANS : Type.getIndiceType(parts[3]);
                    this.pv    = Integer.parseInt(parts[4]);
                    this.pvMax = this.pv;
                    this.att   = Integer.parseInt(parts[5]);
                    this.def   = Integer.parseInt(parts[6]);
                    this.vit   = Integer.parseInt(parts[7]);
                    break;
                }
            }
            br.close();
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


    public void attaque(Pokemon adversaire) {
        // Déterminer qui attaque en premier selon la vitesse
        Pokemon premier = (this.vit >= adversaire.getVit()) ? this : adversaire;
        Pokemon second  = (premier == this) ? adversaire : this;

        System.out.println(premier.getNom() + " attaque en premier !");

        // === ATTAQUE DU PREMIER ===
        double efficacite = Type.getEfficacite(premier.getType1(), second.getType1());
        if (second.getType2() != Type.SANS) {
            efficacite *= Type.getEfficacite(premier.getType1(), second.getType2());
        }

        int degats = (int)((premier.getAtt() - second.getDef()) * efficacite);
        degats = Math.max(1, degats);

        if (efficacite == 0.0) {
            System.out.println("Ça n'affecte pas " + second.getNom() + "...");
            degats = 0;
        } else if (efficacite > 1.0) {
            System.out.println("C'est super efficace !");
        } else if (efficacite < 1.0) {
            System.out.println("Ce n'est pas très efficace...");
        }

        second.setPv(second.getPv() - degats);
        System.out.printf("%s inflige %d dégâts à %s (PV restants : %d/%d)%n",
            premier.getNom(), degats, second.getNom(), second.getPv(), second.getPvMax());

        // === CONTRE-ATTAQUE DU SECOND (si encore vivant) ===
        if (second.estVivant()) {
            System.out.println(second.getNom() + " contre-attaque !");

            double efficacite2 = Type.getEfficacite(second.getType1(), premier.getType1());
            if (premier.getType2() != Type.SANS) {
                efficacite2 *= Type.getEfficacite(second.getType1(), premier.getType2());
            }

            int degats2 = (int)((second.getAtt() - premier.getDef()) * efficacite2);
            degats2 = Math.max(1, degats2);

            if (efficacite2 == 0.0) {
                System.out.println("Ça n'affecte pas " + premier.getNom() + "...");
                degats2 = 0;
            } else if (efficacite2 > 1.0) {
                System.out.println("C'est super efficace !");
            } else if (efficacite2 < 1.0) {
                System.out.println("Ce n'est pas très efficace...");
            }

            premier.setPv(premier.getPv() - degats2);
            System.out.printf("%s inflige %d dégâts à %s (PV restants : %d/%d)%n",
                second.getNom(), degats2, premier.getNom(), premier.getPv(), premier.getPvMax());
        } else {
            System.out.println(second.getNom() + " est K.O. !");
        }
    }
}
