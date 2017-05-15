package ia;

import entitees.abstraites.Entitee;

public abstract class Ia {

	protected boolean reset;

	public abstract char direction(Entitee[][] map);

	public void reset() {
		reset = true;
	}
}
