package ia;

import entitees.abstraites.Entitee;

/**
 * Created by celso on 28/04/17.
 */
public class IaRandom extends Ia {

    @Override
    public char tick(Entitee[][] map) {
        int random = 1 + (int) (Math.random() * 5);

        switch (random) {
            case 1:
                return 'h';
            case 2:
                return 'b';
            case 3:
                return 'd';
            case 4:
                return 'g';
            default:
                return 'a';
        }
    }

    @Override
    public void initialiserTry() {
        // TODO Auto-generated method stub

    }
}
