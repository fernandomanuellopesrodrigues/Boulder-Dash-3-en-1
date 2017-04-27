package entitees.tickables;

import static entitees.abstraites.Entitee.Entitees.Amibe;
import static entitees.abstraites.Entitee.Entitees.Explosion;
import static entitees.abstraites.Entitee.Entitees.Libellule;
import static entitees.abstraites.Entitee.Entitees.Luciole;
import static entitees.abstraites.Entitee.Entitees.MurMagique;
import static entitees.abstraites.Entitee.Entitees.Pierre;
import static entitees.abstraites.Entitee.Entitees.Rockford;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;
public class Pierre extends Tickable {

	public Pierre(int x, int y) {
		super(x, y);
		setDestructible(true);
		getDeplacementsPossibles().add(Rockford);
		getDeplacementsPossibles().add(Luciole);
		getDeplacementsPossibles().add(Libellule);
		getDeplacementsPossibles().add(Amibe);
		getDeplacementsPossibles().add(MurMagique);
		getDeplacementsPossibles().add(Explosion);
		enumeration=Pierre;
	}

	public void tick() {
		gererChute();
	}

	@Override
	protected int contactAutreEntitee(Entitee entitee) {
		setDirection('b');
		if (entitee.is(Rockford)) {
			seDeplacer();
			exploser(false);
			return 0;
		} else if (entitee.is(MurMagique)) {
			// ((MurMagique) entitee).traverser();
		} else if (entitee.is(Libellule)) {
			seDeplacer();
			exploser(false);
			return 0;
		} else if (entitee.is(Luciole)) {
			seDeplacer();
			exploser(true);
			return 0;
		} else if (entitee.is(Amibe)) {
			seDeplacer();
			exploser(true);
			return 0;
		} else if (entitee.is(Explosion)) {
			seDeplacer();
			exploser(false);
			return 0;
		}
		return 1;

	}

}
