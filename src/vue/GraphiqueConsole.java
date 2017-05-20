package vue;

import java.util.List;
import java.util.Objects;

import entitees.abstraites.Entitee;
import entitees.fixes.Amibe;
import entitees.fixes.Mur;
import entitees.fixes.MurEnTitane;
import entitees.fixes.MurMagique;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.tickables.Diamant;
import entitees.tickables.Libellule;
import entitees.tickables.Luciole;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;
import loader.Niveau;
import main.Partie;

import static java.lang.System.out;

/**
 * La classe GraphiqueConsole n'est jamais instanci�e, elle sert uniquement �
 * stocker des methodes en rapport avec l'affichage du jeu en mode console.
 *
 * @author Murloc
 */
public final class GraphiqueConsole {

    private GraphiqueConsole() {}

    /**
     * Affiche un niveau en mode console.
     *
     * @param niveau Le niveau a afficher.
     */
    public static void afficher(final Niveau niveau) {
        // Get la map du niveau.
        final Entitee[][] map = niveau.getMap();

		/*
         * Initialise un string auquel va etre concatene la representation du
		 * niveau, ce string sera affiche a la fin de la methode.
		 */
        StringBuilder builder = new StringBuilder(10);

        // Concatene beaucoup de retours a la ligne pour un meilleur rendu.
        for (int i = 0; i <= 30; i++) {
            builder.append('\n');
        }

		/*
         * Concatene diverses informations.
		 */
        builder.append("Diamants : ")
         .append(Partie.gererNiveau.getNbDiamants())
         .append("/")
         .append(Partie.gererNiveau.getNiveau().getDiamondsRequired())
         .append("\n")
         .append("Score : ")
         .append(Partie.gererNiveau.getScore())
         .append("			Temps restant : ")
         .append(Partie.gererNiveau.getTempsRestant())
         .append(" 		")
         .append("Niveau : ")
         .append(Partie.niveau)
         .append("/")
         .append(Partie.ensembleDeNiveau.getNombreDeNiveaux())
         .append("\n")
         .append("Points/diamant : ")
         .append(Partie.gererNiveau.getNiveau().getDiamondValue())
         .append("	  (diamants bonus) : ")
         .append(Partie.gererNiveau.getNiveau().getDiamondValueBonus())
         .append("\n\n");

        // Parcours de toutes les entitees de la map.
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                // Concatene un caractere specifique suivant l'entitee.
                builder.append(getCharDeEntitee(map[j][i]));
            }
            builder.append('\n');
        }
        // Affiche le string.
        out.println(builder);
    }

    /**
     * Retourne un caractere speficique suivant l'entitee en parametre.
     *
     * @param entitee L'entitee dont on veut le caractere.
     *
     * @return Le caractere propre a l'entitee. Renvoie ' ' si l'entitee est
     * inconnue (ou si c'est l'entitee Vide).
     */
    private static char getCharDeEntitee(final Entitee entitee) {
        // Get la classe de l'entitee.
        Class<? extends Entitee> classz = entitee.getClass();

        // Cree le char qui va etre renvoye.
        char s = ' ';

		/*
         * Compare la classe avec les classes des entitees, puis affecte le char
		 * correspondant au string.
		 */
        if (Objects.equals(classz, Rockford.class)) {
            s = 'P';
        } else if (Objects.equals(classz, Mur.class)) {
            s = 'w';
        } else if (Objects.equals(classz, Diamant.class)) {
            s = 'd';
        } else if (Objects.equals(classz, Amibe.class)) {
            s = 'a';
        } else if (Objects.equals(classz, Luciole.class)) {
            s = 'q';
        } else if (Objects.equals(classz, Libellule.class)) {
            s = 'o';
        } else if (Objects.equals(classz, MurEnTitane.class)) {
            s = 'W';
        } else if (Objects.equals(classz, Pierre.class)) {
            s = 'r';
        } else if (Objects.equals(classz, Poussiere.class)) {
            s = '.';
        } else if (Objects.equals(classz, Sortie.class)) {
            if (Sortie.isOuvert()) {
                s = 'X';
            } else {
                s = 'W';
            }
        } else if (Objects.equals(classz, MurMagique.class)) {
            s = 'M';
        }
        return s;
    }

    /**
     * Affiche les scores � la fin d'une suite de niveaux.
     *
     * @param scores La liste des scores � afficher.
     */
    public static void afficherScoreTousLesNiveaux(List<Integer> scores) {
        out.println("FIN DU JEU , SCORE DES NIVEAUX : \n");
        for (int i = 0; i < scores.size(); i++) {
            out.println("                         Niveau " + (i + 1) + " : " + scores.get(i) + "\n");
        }
        out.println("\n");
    }

}
