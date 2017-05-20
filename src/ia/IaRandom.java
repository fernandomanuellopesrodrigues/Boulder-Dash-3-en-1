package ia;

import entitees.abstraites.Entitee;

import static java.lang.Math.random;
import static main.Constantes.AUTRE;
import static main.Constantes.BAS;
import static main.Constantes.DROITE;
import static main.Constantes.GAUCHE;
import static main.Constantes.HAUT;

/**
 * Created by celso on 28/04/17.
 */
public class IaRandom extends Ia {

    @Override
    public char tick(final Entitee[][] map) {
        final int random = 1 + (int) (random() * 5);
        char direction = AUTRE;
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
        }
        return direction;
    }

    @Override
    public void initialiserTry() {
        // TODO Auto-generated method stub

    }
}
