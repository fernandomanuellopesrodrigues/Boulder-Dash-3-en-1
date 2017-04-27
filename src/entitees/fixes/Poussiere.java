package entitees.fixes;

import entitees.abstraites.Entitee;
import static entitees.abstraites.Entitee.Entitees.*;

public class Poussiere extends Entitee {

	public Poussiere(int x, int y) {
		super(x, y);
		setDestructible(true);
		enumeration = Poussiere;
	}
}