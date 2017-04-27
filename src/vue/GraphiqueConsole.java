package vue;

import java.util.List;

import loader.Niveau;

public class GraphiqueConsole {

	public static void afficher(Niveau niveau) {

	}

	public static void afficherScoreUnNiveau(int niveau, int score) {
		System.out.println("FIN DU JEU , SCORE DU NIVEAU " + niveau + " : " + score + ".\n");
	}

	public static void afficherScoreTousLesNiveaux(List<Integer> scores) {
		System.out.println("FIN DU JEU , SCORE DES NIVEAUX : \n");
		for (int i = 0; i < scores.size(); i++) {
			System.out.println("                         Niveau " + (i + 1) + " : " + scores.get(i) + "\n");
		}
		System.out.println("\n");
	}
}
/*	private String toStringNiveau(){
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
}*/