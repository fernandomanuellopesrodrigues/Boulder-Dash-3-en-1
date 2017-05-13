package loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Entitee;
import entitees.abstraites.Entitee.Entitees;
import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import entitees.fixes.Mur;
import entitees.fixes.MurEnTitane;
import entitees.fixes.MurMagique;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Explosion;
import entitees.tickables.Libellule;
import entitees.tickables.Luciole;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;

public class Niveau implements Cloneable {

	private int caveDelay;
	private int cave_time;
	private int diamonds_required;
	private int diamond_value;
	private int diamond_value_bonus;
	private int amoeba_time;
	private int magic_wall_time;
	private Entitee[][] map;
	private Rockford rockford;

	public Niveau(Entitee[][] map, int caveDelay, int cave_time, int diamonds_required, int diamond_value,
			int diamond_value_bonus, int amoeba_time, int magic_wall_time) {
		this.caveDelay = caveDelay;
		this.map = map;
		this.cave_time = cave_time;
		this.diamonds_required = diamonds_required;
		this.diamond_value = diamond_value;
		this.diamond_value_bonus = diamond_value_bonus;
		this.amoeba_time = amoeba_time;
		this.magic_wall_time = magic_wall_time;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].getClass() == Rockford.class) {
					this.rockford = (Rockford) this.map[i][j];
				}
			}
		}
	}

	public Rockford getRockford() {
		return rockford;
	}

	public Sortie getSortie() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].getClass() == Sortie.class) {
					return (Sortie) map[i][j];
				}
			}
		}
		return null;
	}

	public boolean testEntitee(int x, int y, Entitees enumeration) {
		if (map[x][y].getEnumeration().equals(enumeration)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Amibe> retourneListeDesAmibes() {
		List<Amibe> listeAmibes = new ArrayList<Amibe>();
		for (int i = 0; i < map.length; i++) {
			for (int j = map[i].length - 1; j >= 0; j--) {
				if ((map[i][j]).getClass().equals(Amibe.class)) {
					listeAmibes.add(((Amibe) (map[i][j])));
				}
			}
		}
		return listeAmibes;
	}

	public Niveau clone() {
		Entitee[][] newmap = new Entitee[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				newmap[i][j] = map[i][j].clone();
			}
		}
		return new Niveau(newmap, caveDelay, cave_time, diamonds_required, diamond_value, diamond_value_bonus,
				amoeba_time, magic_wall_time);
	}

	public List<Tickable> getListeTickable() {
		List<Tickable> listeTickable = new ArrayList<Tickable>();
		for (int i = 0; i < getMap().length; i++) {
			for (int j = getMap()[i].length - 1; j >= 0; j--) {
				if (getMap()[i][j] instanceof Tickable) {
					listeTickable.add((Tickable) getMap()[i][j]);
				}
			}
		}
		Collections.sort(listeTickable);
		return listeTickable;
	}

	
	public int getCave_time() {
		return cave_time;
	}

	public int getDiamonds_required() {
		return diamonds_required;
	}

	public int getDiamond_value() {
		return diamond_value;
	}

	public int getDiamond_value_bonus() {
		return diamond_value_bonus;
	}

	public int getAmoeba_time() {
		return amoeba_time;
	}

	public int getMagic_wall_time() {
		return magic_wall_time;
	}

	public Entitee[][] getMap() {
		return map;
	}

	public int getCaveDelay() {
		return caveDelay;
	}

	public void setCave_time(int cave_time) {
		this.cave_time = cave_time;
	}
}