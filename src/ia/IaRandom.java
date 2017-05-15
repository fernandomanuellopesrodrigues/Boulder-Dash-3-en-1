package ia;

import java.util.Random;

import entitees.abstraites.Entitee;

/**
 * Created by celso on 28/04/17.
 */
public class IaRandom extends Ia {
	@Override
	public char direction(Entitee[][] map) {
		Random r = new Random();
		int random = 1 + r.nextInt(6);

		switch (random) {
		case 1:
			return 'h';
		case 2:
			return 'b';
		case 3:
			return 'd';
		case 4:
			return 'g';
		case 5:
			return 'p';
		default:
			return 'a';
		}
	}
}
