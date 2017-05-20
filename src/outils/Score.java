package outils;

import java.util.ArrayList;
import java.util.List;

import entitees.abstraites.Entitee;
import ia.IaEvolue;
import main.Partie;

/**
 * Classe servant a comparer et enregistrer un essai d'un niveau.
 * Enregistre le chemin pris dans un string ainsi que le score obtenu et
 * d'autres informations.
 *
 * @author Murloc
 */
public class Score implements Comparable<Score> {

    /**
     * Entier representant le score obtenu dans cet essai.
     */
    private final int score;

    /**
     * Entier representant le nombre de tours durant l'essai.
     */
    private final int parcours;

    /**
     * String representant le chemin pris lors de cet essai.
     */
    private String chemin;

    /**
     * Boolean indiquant si lors de cet essai Rockford a atteint la sortie.
     */
    private boolean fini;

    /**
     * Liste des diamants obtenus lors de cet essai, la cle est le tick durant
     * lequel le diamant a ete attrape.
     */
    private List<Paire<Integer, Long>> listDiamants = new ArrayList<>(10);


    private Entitee[][] mapFinParcours;

    /**
     * Constructeur Score.
     *
     * @param score Entier repr\u00E9sentant le score obtenu dans cet essai.
     * @param parcours Entier repr\u00E9sentant le nombre de tours durant l'essai.
     * @param listDiamants Liste des diamants obtenus lors de cet essai, la cl\u00E9 est le
     * tick durant lequel le diamant a \u00E9t\u00E9 attrap\u00E9.
     */
    public Score(final int score, final int parcours, final List<Paire<Integer, Long>> listDiamants) {
        this.score = score;
        this.parcours = parcours;
        this.listDiamants = listDiamants;
    }

    /**
     * Le compareTo de cet objet est base sur des criteres faits pour ameliorer
     * les essais.
     */
    @Override
    public int compareTo(final Score o) {
        if (isFini() && !o.isFini()) {
            return -1;
        } else if (!isFini() && o.isFini()) {
            return 1;
        }
        int score1 = 0;
        int score2 = 0;
        if (Partie.ia instanceof IaEvolue) {
            score1 += listDiamants.size() * 10 + (chemin.length() - parcours) / 1.5;
            score2 += o.listDiamants.size() * 10 + (o.chemin.length() - o.parcours) / 1.5;
        } else {
            score1 += listDiamants.size() * 100 + (chemin.length() - parcours) / 30;
            score2 += o.listDiamants.size() * 100 + (chemin.length() - parcours) / 30;
        }
        if (score2 > score1) {
            return 1;
        } else if (score2 < score1) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Renvoie la moyenne des objets dans la liste {@link Score#listDiamants}.
     *
     * @return La moyenne.
     */
    public float moyenne() {
        int somme = 0;
        if (!listDiamants.isEmpty()) {
            somme = listDiamants.get(0).getLeft();
        }
        for (int i = 1; i < listDiamants.size(); i++) {
            somme += listDiamants.get(i).getLeft() - listDiamants.get(i - 1).getLeft();
        }
        if (listDiamants.isEmpty()) {
            return 10000;
        }
        return somme / listDiamants.size();
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public boolean isFini() {
        return fini;
    }

    /**
     * Un setter.
     *
     * @param fini L'objet en question.
     */
    public void setFini(boolean fini) {
        this.fini = fini;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public List<Paire<Integer, Long>> getListDiamants() {
        return listDiamants;
    }

    public Entitee[][] getMapFinParcours() {
        return mapFinParcours;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getScore() {
        return score;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getParcours() {
        return parcours;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public String getChemin() {
        return chemin;
    }

    /**
     * Un setter.
     *
     * @param chemin L'objet en question.
     */
    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

}
