package loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.Amibe;
import entitees.Mur;
import entitees.MurEnTitane;
import entitees.MurMagique;
import entitees.Poussiere;
import entitees.Sortie;
import entitees.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;
import entitees.tickables.ennemis.Libellule;
import entitees.tickables.ennemis.Luciole;
import entiteesabstraites.Entitee;
import entiteesabstraites.Tickable;
import main.Partie;

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

	@Override
	public String toString() {
		String s = "Cave_time=" + cave_time + ", diamonds_required=" + diamonds_required + ", diamond_value="
				+ diamond_value + ", diamond_value_bonus=" + diamond_value_bonus + ", amoeba_time=" + amoeba_time
				+ ", magic_wall_time=" + magic_wall_time + "\n\n";

		 s += toStringNiveau();
		return toStringNiveau();
	}
	private String toStringNiveau(){
		String s="";
		for(int i=0; i<map[0].length; i++){
			for(int j=0; j<map.length; j++){
				Class l = map[j][i].getClass();
				if(l.equals(Rockford.class)){
					s+='P';
				}
				else if(l.equals(Mur.class)){
					s+='w';
				}
				else if(l.equals(Diamant.class)){
					s+='d';
				}
				else if(l.equals(Amibe.class)){
					s+='a';
				}
				else if(l.equals(Luciole.class)){
					s+='q';
				}
				else if(l.equals(Libellule.class)){
					s+='o';
				}
				else if(l.equals(MurEnTitane.class)){
					s+='W';
				}
				else if(l.equals(Pierre.class)){
					s+='r';
				}
				else if(l.equals(Poussiere.class)){
					s+='.';
				}
				else if(l.equals(Sortie.class)){
					s+='X';
				}
				else if(l.equals(MurMagique.class)){
					s+='M';
				}
				else if(l.equals(Vide.class)){
					s+=' ';
				}
			}
			s+="\n";
		}
		return s;
	}

	public void initialiserEntitees(Partie p) {
		for (int i = 0; i < map.length; i++) {
			for (int j = map[i].length - 1; j >= 0; j--) {
				map[i][j].initialiser(p);
			}
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Niveau other = (Niveau) obj;
		for (int i = 0; i < other.map.length; i++) {
			for (int j = 0; j < other.map[i].length; j++) {
				if (!other.map[i][j].equals(map[i][j])) {
					return false;
				}
			}
		}
		return true;
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

	public List<Tickable> getListeTickable(){
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