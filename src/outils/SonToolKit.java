package outils;

import main.Constantes;

/**
 * La classe SonToolKit sert \u00E0 g\u00E9rer les objets qui lancent des SONS
 * {@link Sons}.
 * Elle dispose de 3 de ces objets et chacun de ces objets peut jouer un son
 * diff\u00E9rent.
 *
 * @author celso
 */
public class SonToolKit {

    private long compteur1;
    private long compteur2;
    private long compteur3;
    private long compteur4;
    private long compteur5;
    private long compteur6;

    /**
     * Objets qui jouent les SONS.
     */
    private final Sons sons1 = new Sons();
    private final Sons sons2 = new Sons();
    private final Sons sons3 = new Sons();

    /**
     * Cette méthode lance la méthode {@link Sons#jouer(String)} qui permet de
     * jouer son.
     * Mais elle permet d'éviter le spam de son, ainsi si on met en deuxième
     * paramètre 100, alors le son ne pourra pas être jouer avant une seconde
     * même si on rapelle cette méthode.
     * Une fois que la méthode joue un son, elle ne peut pas en jouer un autre.
     *
     * @param nom Nom du fichier sonore.
     * @param delaiEnCentiSecondes Délait avant de rejouer ce son.
     */
    private void jouerSon1(final String nom, final int delaiEnCentiSecondes) {
        if (Constantes.SONS) {
            compteur1 = System.currentTimeMillis();
            if (compteur1 - compteur2 > delaiEnCentiSecondes * 10) {
                sons1.jouer(nom);
                compteur2 = System.currentTimeMillis();
            }
        }
    }

    /**
     * Cette méthode lance la méthode {@link Sons#jouer(String)} qui permet de
     * jouer son.
     * Mais elle permet d'éviter le spam de son, ainsi si on met en deuxième
     * paramètre 100, alors le son ne pourra pas être jouer avant une seconde
     * même si on rapelle cette méthode.
     * Une fois que la méthode joue un son, elle ne peut pas en jouer un autre.
     *
     * @param nom Nom du fichier sonore.
     */
    private void jouerSon2(final String nom, final int delaiEnCentiSecondes) {
        if (Constantes.SONS) {
            compteur3 = System.currentTimeMillis();
            if (compteur3 - compteur4 > delaiEnCentiSecondes * 10) {
                sons2.jouer(nom);
                compteur4 = System.currentTimeMillis();
            }
        }
    }

    /**
     * Cette méthode lance la méthode {@link Sons#jouer(String)} qui permet de
     * jouer son.
     * Mais elle permet d'éviter le spam de son, ainsi si on met en deuxième
     * paramètre 100, alors le son ne pourra pas être jouer avant une seconde
     * même si on rapelle cette méthode.
     * Une fois que la méthode joue un son, elle ne peut pas en jouer un autre.
     *
     * @param nom Nom du fichier sonore.
     */
    private void jouerSon3(final String nom, final int delaiEnCentiSecondes) {
        if (Constantes.SONS) {
            compteur5 = System.currentTimeMillis();
            if (compteur5 - compteur6 > delaiEnCentiSecondes * 10) {
                sons3.jouer(nom);
                compteur6 = System.currentTimeMillis();
            }
        }
    }

    public void jouerSonAmoeba() {
        jouerSon1("amoeba.wav", 965);
    }

    public void jouerSonExplosionDiamant() {
        jouerSon3("explosionDiamant.wav", 10);
    }

    public void jouerSonStone() {
        jouerSon1("stone.wav", 1);
    }

    public void jouerSonExplosion() {
        jouerSon1("explosion.wav", 1);
    }

    public void jouerSonWalkEart() {
        jouerSon1("walk_earth.wav", 1);
    }

    public void jouerSonMortRockford() {
        jouerSon3("mortRockford.wav", 1);
    }

    /**
     * Arr\u00EAte tout son qui serait en cours de diffusion sur l'objet sons1.
     */
    public void stopSon1() {
        sons1.stop();
    }

    /**
     * Arr\u00EAte tout son qui serait en cours de diffusion sur l'objet sons2.
     */
    private void stopSon2() {
        sons2.stop();
    }

    /**
     * Arr\u00EAte tout son qui serait en cours de diffusion sur l'objet sons3.
     */
    private void stopSon3() {
        sons3.stop();
    }

    /**
     * Arr\u00EAte tout son qui serait en cours de diffusion sur tous les objets
     * SONS.
     */
    public void stopAll() {
        stopSon1();
        stopSon2();
        stopSon3();
    }
}
