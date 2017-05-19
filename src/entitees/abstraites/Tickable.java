package entitees.abstraites;

import static entitees.abstraites.Entitee.Entitees.Diamant;
import static entitees.abstraites.Entitee.Entitees.Mur;
import static entitees.abstraites.Entitee.Entitees.MurMagique;
import static entitees.abstraites.Entitee.Entitees.Pierre;
import static entitees.abstraites.Entitee.Entitees.Vide;

import java.util.ArrayList;
import java.util.List;

import entitees.fixes.Amibe;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Explosion;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;
import main.Coeur;
import main.Partie;

public abstract class Tickable extends Entitee implements Comparable<Tickable> {
	protected boolean chute;
	private char direction;
	private List<Entitees> deplacementsPossibles = new ArrayList<Entitees>();
	private boolean actionEffectue;
	protected boolean bloque = true;

	protected Tickable(int x, int y) {
		super(x, y);
		deplacementsPossibles.add(Vide);
	}

	// return -1 si l'autre entitee nous mange,0 si deplacement impossible,1si
	// on mange l'entitee
	protected abstract int contactAutreEntitee(Entitee entitee);

	public abstract void tick();

	public boolean seDeplacer() {
		int x = 0;
		int y = 0;
		if (direction == 'h') {
			y = -1;
		} else if (direction == 'd') {
			x = 1;
		} else if (direction == 'g') {
			x = -1;
		} else if (direction == 'b') {
			y = 1;
		}
		if (placeLibre(getX() + x, getY() + y) && direction != ' ') {
			int contact = contactAutreEntitee(Partie.gererNiveau.getNiveau().getMap()[getX() + x][getY() + y]);
			if (contact == 1) {
				Partie.gererNiveau.getNiveau().getMap()[getX() + x][getY() + y].mourir();
				Partie.gererNiveau.getNiveau().getMap()[getX()][getY()] = new Vide(getX(), getY());
				setX(getX() + x);
				setY(getY() + y);
				Partie.gererNiveau.getNiveau().getMap()[getX()][getY()] = this;
				return true;
			} else if (contact == -1) {
				mourir();
			}
		}
		return false;
	}

	protected void glisser() {
		if (Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Pierre)
				|| Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Diamant)
				|| Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Mur)
				|| Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, MurMagique)) {
			if (Partie.gererNiveau.getNiveau().testEntitee(getX() + 1, getY(), Vide)
					&& Partie.gererNiveau.getNiveau().testEntitee(getX() + 1, getY() + 1, Vide)) {
				direction = 'd';
				seDeplacer();
			} else if (Partie.gererNiveau.getNiveau().testEntitee(getX() - 1, getY(), Vide)
					&& Partie.gererNiveau.getNiveau().testEntitee(getX() - 1, getY() + 1, Vide)) {
				direction = 'g';
				seDeplacer();
			}
		}
	}

	protected void gererChute() {
		if (chute && placeLibre(getX(), getY() + 1)) {
			direction = 'b';
			seDeplacer();
		} else if (Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Vide)
				|| (Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, MurMagique)
						&& Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 2, Vide))) {

			chute = true;
			gererChute();
		} else {
			if (!placeLibre(getX(), getY() + 1)) {
				if (enumeration == Pierre) {
					sons.jouerSon1("stone.wav", 1);
				}
				chute = false;
			}
			glisser();
		}

	}

	protected void exploser(boolean popDiamants) {
		if (popDiamants)
			sons.jouerSon1("explosionDiamant.wav", 1);
		else
			sons.jouerSon1("explosion.wav", 1);
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				explosion(i, j, popDiamants);
			}
		}
	}

	protected void explosion(int i, int j, boolean popDiamants) {
		if (Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j].mourir()) {
			if (popDiamants)
				Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j] = new Diamant(getX() + i, getY() + j);
			else {
				if (Coeur.graphique)
					Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j] = new Explosion(getX() + i,
							getY() + j);
			}
			if (Coeur.graphique || popDiamants)
				Partie.gererNiveau
						.ajouterTickable((Tickable) Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j]);
		}
	}

	public boolean placeLibre(int x, int y) {
		for (Entitees e : deplacementsPossibles) {
			if (Partie.gererNiveau.getNiveau().getMap()[x][y].getEnumeration().equals(e)) {
				return true;
			}
		}
		return false;
	}

	public int compareTo(Tickable t) {
		return t.getNumeroPriorite() - getNumeroPriorite();
	}

	public int getNumeroPriorite() {
		if (getClass().equals(Rockford.class)) {
			return 5;
		} else if (this instanceof Ennemi) {
			return 4;
		} else if (getClass().equals(Pierre.class) || getClass().equals(Diamant.class)) {
			return 3;
		} else if (getClass().equals(Amibe.class)) {
			return 2;
		} else {
			return 1;
		}
	}

	protected void bloquer() {
		bloque = !(placeLibre(getX() + 1, getY()) || placeLibre(getX(), getY() + 1) || placeLibre(getX(), getY() - 1)
				|| placeLibre(getX() - 1, getY()));
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public boolean isActionEffectue() {
		return actionEffectue;
	}

	public void setActionEffectue(boolean actionEffectue) {
		this.actionEffectue = actionEffectue;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public List<Entitees> getDeplacementsPossibles() {
		return deplacementsPossibles;
	}

	public void setChute(boolean chute) {
		this.chute = chute;
	}
}
