package ia;

import entitees.abstraites.Entitee;

public class IaDirective extends Ia {

	@Override
	public char direction(Entitee[][] map) {
		
		if (reset) {

			reset = false;
		}
		return 0;
	}

}
