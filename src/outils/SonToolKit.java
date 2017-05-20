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

    private long compteur2;
    private long compteur6;

    /**
     * Objets qui jouent les SONS.
     */
    private final Sons sons1 = new Sons();
    private final Sons sons2 = new Sons();
    private final Sons sons3 = new Sons();

    /**
     * Cette m\u00E9thode lance la m\u00E9thode {@link Sons#jouer(String)} qui permet de
     * jouer son.
     * Mais elle permet d'\u00E9viter le spam de son, ainsi si on met en deuxi\u00E8me
     * param\u00E8tre 100, alors le son ne pourra pas \u00EAtre jouer avant une seconde
     * m\u00EAme si on rapelle cette m\u00E9thode.
     * Une fois que la m\u00E9thode joue un son, elle ne peut pas en jouer un autre.
     *  */
    public void jouerSonAmoeba() {
        if (Constantes.SONS) {
            /*
      Compteurs permettant de g\u00E9rer les diff\u00E9rents timers qui d\u00E9clenchent les
      SONS.
     */
            final long compteur1 = System.currentTimeMillis();
            if (compteur1 - compteur2 > 965 * 10) {
                sons1.jouer("amoeba.wav");
                compteur2 = System.currentTimeMillis();
            }
        }
    }

    /**
     * Cette m\u00E9thode lance la m\u00E9thode {@link Sons#jouer(String)} qui permet de
     * jouer son.
     * Mais elle permet d'\u00E9viter le spam de son, ainsi si on met en deuxi\u00E8me
     * param\u00E8tre 100, alors le son ne pourra pas \u00EAtre jouer avant une seconde
     * m\u00EAme si on rapelle cette m\u00E9thode.
     * Une fois que la m\u00E9thode joue un son, elle ne peut pas en jouer un autre.
     *
     */
    public void jouerSonExplosionDiamant() {
        if (Constantes.SONS) {
            final long compteur5 = System.currentTimeMillis();
            if (compteur5 - compteur6 > 1 * 10) {
                sons3.jouer("explosionDiamant.wav");
                compteur6 = System.currentTimeMillis();
            }
        }
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
