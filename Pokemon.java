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
        // TODO
        return false;
    }

    @Override
    public String toString() {
        // TODO
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        // TODO
        return false;
    }

    public void attaque(Pokemon adversaire) {
        // TODO
    }
}
