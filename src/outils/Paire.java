package outils;

/**
 * Classe permettant de sauvegarder des paires d'objets de n'importe quel type.
 *
 * @param <L> Objet 1.
 * @param <R> Objet 2.
 *
 * @author Murloc
 */
public class Paire<L, R> {

    /**
     * Objet 1.
     */
    private final L left;

    /**
     * Objet 2.
     */
    private final R right;

    /**
     * Constructeur Paire.
     *
     * @param left Objet 1.
     * @param right Objet 2.
     */
    public Paire(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Getter de l'objet 1.
     *
     * @return Obet 1.
     */
    public L getLeft() {
        return left;
    }

    /**
     * Getter de l'objet 2.
     *
     * @return Obet 2.
     */
    public R getRight() {
        return right;
    }
}
