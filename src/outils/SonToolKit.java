package outils;

import main.Constantes;

/**
 * La classe SonToolKit sert à gérer les objets qui lancent des sons
 * {@link Sons}.
 * Elle dispose de 3 de ces objets et chacun de ces objets peut jouer un son
 * différent.
 *
 * @author celso
 */
public class SonToolKit {

    /**
     * Compteurs permettant de gérer les différents timers qui déclenchent les
     * sons.
     */
    private long compteur1, compteur2, compteur3, compteur4, compteur5, compteur6;

    /**
     * Objets qui jouent les sons.
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
    public void jouerSon1(String nom, int delaiEnCentiSecondes) {
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
    public void jouerSon2(String nom, int delaiEnCentiSecondes) {
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
    public void jouerSon3(String nom, int delaiEnCentiSecondes) {
        if (Constantes.SONS) {
            compteur5 = System.currentTimeMillis();
            if (compteur5 - compteur6 > delaiEnCentiSecondes * 10) {
                sons3.jouer(nom);
                compteur6 = System.currentTimeMillis();
            }
        }
    }

    /**
     * Arrête tout son qui serait en cours de diffusion sur l'objet sons1.
     */
    public void stopSon1() {
        sons1.stop();
    }

    /**
     * Arrête tout son qui serait en cours de diffusion sur l'objet sons2.
     */
    public void stopSon2() {
        sons2.stop();
    }

    /**
     * Arrête tout son qui serait en cours de diffusion sur l'objet sons3.
     */
    public void stopSon3() {
        sons3.stop();
    }

    /**
     * Arrête tout son qui serait en cours de diffusion sur tous les objets
     * sons.
     */
    public void stopAll() {
        stopSon1();
        stopSon2();
        stopSon3();
    }
}
