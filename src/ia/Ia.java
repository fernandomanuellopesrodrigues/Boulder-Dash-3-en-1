package ia;

import entitees.abstraites.Entitee;

/**
 * @author celso
 */
public abstract class Ia {

    protected boolean reset = true;

    public static char directionRandom() {
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

    public char direction(Entitee[][] map) {
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
