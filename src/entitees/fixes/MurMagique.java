package entitees.fixes;

import entitees.abstraites.Entitee;
import main.Partie;
import static entitees.abstraites.Entitee.Entitees.*;

public class MurMagique extends Entitee {

	private int magicWallTime;

	public MurMagique(int x, int y, int magicWallTime) {
		super(x, y);
		this.magicWallTime = magicWallTime;
		setDestructible(true);
		enumeration = MurMagique;
	}

	public int getMagicWallTime() {
		return magicWallTime;
	}

	public void decrementerMagicWallTime() {
		magicWallTime--;
		if (magicWallTime <= 0) {
			mourir();
			Partie.gererNiveau.getNiveau().placerEntitee(new Mur(getX(), getY()));
		}
	}
	
}
