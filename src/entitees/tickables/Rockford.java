package entitees.tickables;

import static entitees.abstraites.Entitee.Entitees.Amibe;
import static entitees.abstraites.Entitee.Entitees.Diamant;
import static entitees.abstraites.Entitee.Entitees.Explosion;
import static entitees.abstraites.Entitee.Entitees.Pierre;
import static entitees.abstraites.Entitee.Entitees.Poussiere;
import static entitees.abstraites.Entitee.Entitees.Rockford;
import static entitees.abstraites.Entitee.Entitees.Sortie;
import static entitees.abstraites.Entitee.Entitees.Vide;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;
import main.Coeur;
import main.Partie;

public class Rockford extends Tickable {

	private char ancienneDirection = 'd';

	public Rockford(int x, int y) {
		super(x, y);
		setDestructible(true);
		setDirection(' ');
		getDeplacementsPossibles().add(Poussiere);
		getDeplacementsPossibles().add(Diamant);
		getDeplacementsPossibles().add(Pierre);
		getDeplacementsPossibles().add(Sortie);
		getDeplacementsPossibles().add(Amibe);
		getDeplacementsPossibles().add(Explosion);
		enumeration = Rockford;
	}

	public void ramasserDiamant() {
		if (Partie.gererNiveau.getNbDiamants() >= Partie.gererNiveau.getNiveau().getDiamonds_required()) {
			Partie.gererNiveau
					.setScore(Partie.gererNiveau.getScore() + Partie.gererNiveau.getNiveau().getDiamond_value_bonus());
		} else {
			Partie.gererNiveau
					.setScore(Partie.gererNiveau.getScore() + Partie.gererNiveau.getNiveau().getDiamond_value());
		}
		Partie.gererNiveau.incrementerNbDiamants();
	}

	@Override
	protected int contactAutreEntitee(Entitee entitee) {
		if (entitee.is(Pierre)) {
			if (entitee.getX() - getX() > 0
					&& Partie.gererNiveau.getNiveau().getMap()[entitee.getX() + 1][entitee.getY()].is(Vide)) {
				((entitees.tickables.Pierre) entitee).setDirection('d');
				((entitees.tickables.Pierre) entitee).seDeplacer();
			} else if (entitee.getX() - getX() < 0
					&& Partie.gererNiveau.getNiveau().getMap()[entitee.getX() - 1][entitee.getY()].is(Vide)) {
				((entitees.tickables.Pierre) entitee).setDirection('g');
				((entitees.tickables.Pierre) entitee).seDeplacer();
			}
			return 0;
		} else if (entitee.is(Diamant)) {
			entitee.mourir();
			ramasserDiamant();
			return 1;
		} else if (entitee.is(Sortie)) {
			if (Partie.gererNiveau.getNbDiamants() >= Partie.gererNiveau.getNiveau().getDiamonds_required()) {
				Partie.gererNiveau.setFiniSuccess(true);
				Partie.finNiveau();
				return 1;
			}
			return 0;
		} else if (entitee.is(Amibe)) {
			mourir();
			return -1;
		} else if (entitee.is(Explosion)) {
			mourir();
			return -1;
		}
		return 1;
	}

	@Override
	public void tick() {
		deplacement();
	}

	private void deplacement() {
		setDirection(Partie.gererNiveau.getToucheClavier());
		if (getDirection() != ' ') {
			seDeplacer();
		}
	}

	public boolean mourir() {
		super.mourir();
		Partie.resetNiveau();
		return true;
	}

	public char getAncienneDirection() {
		return ancienneDirection;
	}

	public void setAncienneDirection(char ancienneDirection) {
		this.ancienneDirection = ancienneDirection;
	}

}
