package outils;

import java.util.ArrayList;
import java.util.List;

import entitees.abstraites.Entitee;

public class Score implements Comparable<Score> {

	private int score, parcours;
	private String chemin;
	private boolean fini;
	private List<Paire<Integer, Long>> listeDiamants = new ArrayList<Paire<Integer, Long>>();
	private Entitee[][] mapFinParcours;

	public Score(int score, int parcours, List<Paire<Integer, Long>> listeDiamants) {
		this.score = score;
		this.parcours = parcours;
		this.listeDiamants = listeDiamants;
	}

	public int getScore() {
		return score;
	}

	public int getParcours() {
		return parcours;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	@Override
	public int compareTo(Score o) {
		if (isFini() && !o.isFini()) {
			return -1;
		} else if (!isFini() && o.isFini()) {
			return 1;
		}
		int score1 = 0;
		int score2 = 0;
		//double moyenne1 = 0;
		//double moyenne2 = 0;
		
		/*if(moyenne2()<10){
			moyenne1 = 10;
		}
		else{
			moyenne1 = moyenne();
		}
		
		if(o.moyenne2()<10){
			moyenne2 = 10;
		}
		else{
			moyenne2 = o.moyenne();
		}*/
			
		//score1 += (listeDiamants.size()*10) + ((1/moyenne1)*Constantes.VALEUR_SCORE_MOYENNE);
		//score2 += (o.listeDiamants.size()*10) + ((1/moyenne2)*Constantes.VALEUR_SCORE_MOYENNE);
		
		//score1 += (listeDiamants.size()*100) + ((1/moyenne1)*Constantes.VALEUR_SCORE_MOYENNE) + (chemin.length()-parcours)/1.5;
		//score2 += (o.listeDiamants.size()*100) + ((1/moyenne2)*Constantes.VALEUR_SCORE_MOYENNE) + (o.chemin.length()-o.parcours)/1.5;
		score1 += (listeDiamants.size()*100);
		score2 += (o.listeDiamants.size()*100);
		if (score2 > score1) {
			return 1;
		} else if (score2 < score1) {
			return -1;
		} else {
			return 0;
		}
		/*if (o.getScore() > getScore()) {
			return 1;
		} else if (getScore() > o.getScore()) {
			return -1;
		} else {
			if(o.moyenne2() > moyenne2()){
				return -1;
			}else if(moyenne2() > o.moyenne2()){
				return 1;
			}else{
				if (o.getParcours() > getParcours()) {
					return 1;
				} else if (getParcours() > o.getParcours()) {
					return -1;
				} else {
					return 0;
				}
			}
			
		}*/
	}
	
	public float moyenne2() {
		int somme = 0;
		if(!listeDiamants.isEmpty()){
			somme = listeDiamants.get(0).getLeft();
		}
		for (int i=1; i<listeDiamants.size(); i++){
			somme+=(listeDiamants.get(i).getLeft()-listeDiamants.get(i-1).getLeft());
		}
		if(listeDiamants.isEmpty()){
			return 10000;
		}
		return somme/(listeDiamants.size());
	}

	public float moyenne() {
		int somme = 0;
		for (Paire<Integer, Long> p : listeDiamants){
			somme+=p.getLeft();
		}
		if(listeDiamants.isEmpty()){
			return 10000;
		}
		return somme/(listeDiamants.size()+1);
	}

	public boolean isFini() {
		return fini;
	}

	public void setFini(boolean fini) {
		this.fini = fini;
	}

	public List<Paire<Integer, Long>> getListeDiamants() {
		return listeDiamants;
	}
	public Entitee[][] getMapFinParcours() {
		return mapFinParcours;
	}

	public void setMapFinParcours(Entitee[][] mapFinParcours) {
		this.mapFinParcours = mapFinParcours;
	}
}
