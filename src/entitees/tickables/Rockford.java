package entitees.tickables;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;
import main.Coeur;
import main.Partie;
import outils.SonToolKit;

import static entitees.abstraites.Entitee.Entitees.*;

import constantes.Constantes;

public class Rockford extends Tickable {

	private char ancienneDirection = 'd';
	private int nombreDeBombe = Constantes.NOMBRE_DE_BOMBES;
	private boolean bombeAPoser = false;

	public Rockford(int x, int y) {
		super(x, y);
		setDestructible(true);
		setDirection(' ');
		getDeplacementsPossibles().add(Poussiere);
		getDeplacementsPossibles().add(Diamant);
		getDeplacementsPossibles().add(Pierre);
		getDeplacementsPossibles().add(Sortie);
		getDeplacementsPossibles().add(Amibe);
		getDeplacementsPossibles().add(Libellule);
		getDeplacementsPossibles().add(Luciole);
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
				Partie.gererNiveau.setDemandeFin(true);
				return 1;
			}
			return 0;
		} else if (entitee.is(Amibe)) {
			mourir();
			return -1;
		} else if (entitee.is(Explosion)) {
			mourir();
			return -1;
		} else if (entitee.is(Libellule)) {
			mourir();
			return -1;
		} else if (entitee.is(Luciole)) {
			mourir();
			return -1;
		}
		return 1;
	}

	@Override
	public void tick() {
		setDirection(Partie.gererNiveau.getToucheClavier());
		checkCamouflage();
		if (enumeration == Rockford) {
			if(getDirection()=='B'){
				poserBombe();
			}
			else if (deplacement()) {
				checkBombe();
			}
		}
	}

	private void checkBombe() {
		if (bombeAPoser == true) {
			int x = 0, y = 0;
			if (getDirection() == 'h') {
				y = -1;
			} else if (getDirection() == 'd') {
				x = 1;
			} else if (getDirection() == 'g') {
				x = -1;
			} else if (getDirection() == 'b') {
				y = 1;
			}
			Partie.gererNiveau.getNiveau().getMap()[getX() - x][getY() - y] = new Bombe(getX() - x, getY() - y);
			bombeAPoser = false;
			nombreDeBombe--;
		}
	}

	protected void gererChute() {
		if (chute && placeLibre(getX(), getY() + 1)) {
			setDirection('b');
			seDeplacer();
		} else if (Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Vide)) {
			chute = true;
			gererChute();
		} else {
			if (!placeLibre(getX(), getY() + 1)) {
				chute = false;
			}
			glisser();
		}

	}

	private boolean deplacement() {
		if (getDirection() != ' ') {
			sons.jouerSon1("walk_earth.wav", 1);
			if (seDeplacer())
				return true;
		}
		return false;
	}

	public boolean mourir() {
		super.mourir();
		sons.jouerSon3("mortRockford.wav", 1);
		Partie.gererNiveau.setDemandeReset(true);
		return true;
	}

	public void checkCamouflage() {
			if (Partie.gererNiveau.getToucheClavier() == 'p' && enumeration == Rockford) {
				seCamoufler();
			} else if (enumeration == Pierre && Partie.gererNiveau.getToucheClavier() != 'p') {
				seDecamoufler();
			}
	}

	private void seCamoufler() {
		enumeration = Pierre;
		getDeplacementsPossibles().remove(Poussiere);
		getDeplacementsPossibles().remove(Diamant);
		getDeplacementsPossibles().remove(Sortie);
		getDeplacementsPossibles().add(Luciole);
		getDeplacementsPossibles().add(Libellule);
		getDeplacementsPossibles().add(Amibe);
	}

	private void seDecamoufler() {
		enumeration = Rockford;
		getDeplacementsPossibles().add(Poussiere);
		getDeplacementsPossibles().add(Diamant);
		getDeplacementsPossibles().add(Sortie);
		getDeplacementsPossibles().remove(Luciole);
		getDeplacementsPossibles().remove(Libellule);
		getDeplacementsPossibles().remove(Amibe);
	}

	public void poserBombe() {
		if (nombreDeBombe > 0) {
			bombeAPoser = true;
		}
	}

	public boolean camouflageActif() {
		return getEnumeration() == Entitee.Entitees.Pierre;
	}

	public char getAncienneDirection() {
		return ancienneDirection;
	}

	public void setAncienneDirection(char ancienneDirection) {
		this.ancienneDirection = ancienneDirection;
	}

}
