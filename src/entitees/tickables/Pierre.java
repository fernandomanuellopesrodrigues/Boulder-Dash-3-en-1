package entitees.tickables;

import static entitees.abstraites.Entitee.Entitees.Amibe;
import static entitees.abstraites.Entitee.Entitees.Explosion;
import static entitees.abstraites.Entitee.Entitees.Libellule;
import static entitees.abstraites.Entitee.Entitees.Luciole;
import static entitees.abstraites.Entitee.Entitees.MurMagique;
import static entitees.abstraites.Entitee.Entitees.Pierre;
import static entitees.abstraites.Entitee.Entitees.Rockford;
import static entitees.abstraites.Entitee.Entitees.Vide;

import java.util.ArrayList;
import java.util.List;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;
import main.Partie;

public class Pierre extends Tickable {
	private List<Entitees> deplacementsPossiblesChute = new ArrayList<Entitees>();

	public Pierre(int x, int y) {
		super(x, y);
		setDestructible(true);
		getDeplacementsPossibles().add(Rockford);
		getDeplacementsPossibles().add(Luciole);
		getDeplacementsPossibles().add(Libellule);
		getDeplacementsPossibles().add(Amibe);
		getDeplacementsPossibles().add(MurMagique);
		getDeplacementsPossibles().add(Explosion);
		deplacementsPossiblesChute.add(Vide);
		enumeration = Pierre;
	}

	public void tick() {
		if (!bloque || chute) {
			gererChute();
		}else{
			glisser();
		}
		bloquer();
	}

	@Override
	protected int contactAutreEntitee(Entitee entitee) {
		
		setDirection('b');
		if (entitee.is(Rockford)) {
			exploser(false);
			return 0;
		} else if (entitee.is(MurMagique)) {
			return 0;
		} else if (entitee.is(Libellule)) {
			exploser(true);
			return 0;
		} else if (entitee.is(Luciole)) {
			exploser(false);
			return 0;
		} else if (entitee.is(Amibe)) {
			exploser(true);
			return 0;
		} else if (entitee.is(Explosion)) {
			exploser(false);
			return 0;
		}
		return 1;

	}

	protected void exploser(boolean popDiamants) {
		//sons.jouerSon1("explosion.wav", 1);
		for (int i = -1; i < 2; i++) {
			for (int j = 0; j <= 2; j++) {
				explosion(i,j,popDiamants);
			}
		}
	}

	protected void bloquer() {
		bloque = !(placeLibreChute(getX(), getY() + 1));
	}

	protected boolean placeLibreChute(int x, int y) {
		for (Entitees e : deplacementsPossiblesChute) {
			if (Partie.gererNiveau.getNiveau().getMap()[x][y].getEnumeration().equals(e)) {
				return true;
			}
		}
		return false;
	}
}
