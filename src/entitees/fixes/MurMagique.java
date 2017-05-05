package entitees.fixes;

import static entitees.abstraites.Entitee.Entitees.Diamant;
import static entitees.abstraites.Entitee.Entitees.MurMagique;
import static entitees.abstraites.Entitee.Entitees.Pierre;
import static entitees.abstraites.Entitee.Entitees.Vide;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;
import entitees.tickables.Diamant;
import entitees.tickables.Pierre;
import main.Partie;

public class MurMagique extends Tickable {

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
			Partie.gererNiveau.getNiveau().getMap()[getX()][getY()] = new Mur(getX(), getY());
		}
	}

	public boolean traverser() {
		if (Partie.gererNiveau.getNiveau().getMap()[getX()][getY() + 1].is(Vide)) {
			if (Partie.gererNiveau.getNiveau().getMap()[getX()][getY() - 1].is(Diamant)) {
				Partie.gererNiveau.getNiveau().getMap()[getX()][getY() - 1].mourir();
				Partie.gererNiveau.getNiveau().getMap()[getX()][getY() + 1] = new Pierre(getX(), getY() + 1);
				Partie.gererNiveau
						.ajouterTickable((Tickable) Partie.gererNiveau.getNiveau().getMap()[getX()][getY() + 1]);
				decrementerMagicWallTime();
				return true;
			} else if (Partie.gererNiveau.getNiveau().getMap()[getX()][getY() - 1] instanceof Pierre) {
				Partie.gererNiveau.getNiveau().getMap()[getX()][getY() - 1].mourir();
				Partie.gererNiveau.getNiveau().getMap()[getX()][getY() + 1] = new Diamant(getX(), getY() + 1);
				Partie.gererNiveau
						.ajouterTickable((Tickable) Partie.gererNiveau.getNiveau().getMap()[getX()][getY() + 1]);
				((Tickable) Partie.gererNiveau.getNiveau().getMap()[getX()][getY() + 1]).setChute(true);
				decrementerMagicWallTime();
				return true;
			}
		}
		return false;

	}

	@Override
	protected int contactAutreEntitee(Entitee entitee) {
		return 0;
	}

	@Override
	public void tick() {
		traverser();
	}

}
