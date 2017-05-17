package entitees.tickables;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;
import main.Constantes;
import main.Partie;

import static entitees.abstraites.Entitee.Entitees.Bombe;

public class Bombe extends Tickable {
	private int tempsRestantAvantExplosion = Constantes.BOOM;

	protected Bombe(int x, int y) {
		super(x, y);
		enumeration = Bombe;
		setDestructible(false);
		Partie.gererNiveau.ajouterTickable(this);
	}

	@Override
	protected int contactAutreEntitee(Entitee entitee) {
		return 0;
	}

	@Override
	public void tick() {
		if (tempsRestantAvantExplosion == 0 && !isMort()) {
			exploser(false);
		}
		tempsRestantAvantExplosion--;
	}

	public boolean mourir() {
		if (tempsRestantAvantExplosion > 0) {
			tempsRestantAvantExplosion = -1;
			exploser(false);			
		}
		return true;
	}

	public int getTempsRestantAvantExplosion() {
		return tempsRestantAvantExplosion;
	}
}
