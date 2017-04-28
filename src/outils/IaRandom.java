package outils;

import java.util.Random;

/**
 * Created by celso on 28/04/17.
 */
public class IaRandom {

    public static char directionRandom() {
        Random r = new Random();
        int random = 1 + r.nextInt(4);

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
}
