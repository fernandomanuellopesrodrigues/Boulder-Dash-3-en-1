package entitees.fixes;

import entitees.abstraites.Entitee;
import static entitees.abstraites.Entitee.Entitees.*;

public class Sortie extends Entitee {
	public Sortie(int x, int y) {
		super(x, y);
		enumeration = Sortie;
	}
}
