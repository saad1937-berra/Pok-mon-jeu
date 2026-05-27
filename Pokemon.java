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
        // TODO
    }

    public Pokemon(int numPokedex, String nom, int type1, int type2, int pv, int att, int def, int vit) {
        // TODO
    }

    public Pokemon(int numPokedex, String nom, String cheminCSV) {
        // TODO
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
