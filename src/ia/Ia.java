package ia;

import entitees.abstraites.Entitee;

import static java.lang.Math.random;
import static main.Constantes.AUTRE;
import static main.Constantes.BAS;
import static main.Constantes.DROITE;
import static main.Constantes.GAUCHE;
import static main.Constantes.HAUT;

/**
 * @author celso
 */
public abstract class Ia {

    private boolean reset = true;

    public static char directionRandom() {
        final int random = 1 + (int) (random() * 5);
        char direction;
        switch (random) {
            case 1:
                direction = HAUT;
                break;
            case 2:
                direction = BAS;
                break;
            case 3:
                direction = DROITE;
                break;
            case 4:
                direction = GAUCHE;
                break;
            default:
                direction = AUTRE;
        }
        return direction;
    }

    public char direction(final Entitee[][] map) {
        if (reset) {
            initialiserTry();
            reset = false;
        }
        return tick(map);
    }

    public abstract char tick(Entitee[][] map);

    public abstract void initialiserTry();

    public void reset() {
        reset = true;
    }
}
