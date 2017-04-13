package loader;

import java.util.ArrayList;
import java.util.List;

public class EnsembleDeNiveaux {

	private List<Niveau> niveaux;
	private int nbNiveaux;

	public EnsembleDeNiveaux(int nombre_de_niveaux) {
		this.niveaux = new ArrayList<Niveau>();
		this.nbNiveaux = nombre_de_niveaux;
	}

	public boolean ajouterNiveau(Niveau niveau) {
		return niveaux.add(niveau);
	}

	public int getNombre_de_niveaux() {
		return nbNiveaux;
	}

	public List<Niveau> getNiveaux() {
		return niveaux;
	}

	public String toString() {
		String s = "";
		for (Niveau niveau : niveaux) {
			s += niveau;
			s += "\n_____________________\n\n";
		}
		return s;
	}

	public void setNbNiveaux(int nbNiveaux) {
		this.nbNiveaux = nbNiveaux;
	}
}
