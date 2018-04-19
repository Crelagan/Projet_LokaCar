package fr.eni.jcannas2017.projet_lokacar.beans;

public class Utils {

    public static enum  enumCarburant{
        VIDE(""),
        ESSENCE("Essence"),
        DIESEL("Diesel"),
        GPL("GPL"),
        ELECTRIQUE("Electrique");

        private String valeur;

        private enumCarburant(String valeur) {
            this.valeur = valeur;
        }


        @Override
        public String toString() {
            return valeur;
        }
    }

    public static enum enumBoiteVitesse{
        VIDE(""),
        MANUELLE("Manuelle"),
        AUTOMATIQUE("Automatique");

        private String valeur;

        private enumBoiteVitesse(String valeur){this.valeur = valeur;}

        @Override
        public String toString() {
            return valeur;
        }
    }

    public static enum enumType{
        VIDE(""),
        URBAIN("urbain"),
        EXTRA_URBAIN("extra-urbain"),
        MIXTE("mixte");

        private String valeur;

        private enumType(String valeur){this.valeur = valeur;}

        @Override
        public String toString() {
            return valeur;
        }
    }

    public static enum enumTarif{
        VIDE(""),
        INF50("< 50€"),
        INF70("< 70€"),
        INF90("< 90€"),
        INF100("< 100€"),
        PLUS("> 100€");

        private String valeur;

        private enumTarif(String valeur){this.valeur = valeur;}

        @Override
        public String toString() {
            return valeur;
        }
    }
}
