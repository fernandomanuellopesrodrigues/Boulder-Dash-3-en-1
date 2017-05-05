package constantes;

public class Constantes {
	public static final String NOMS = "\n\nCelso De Carvalho Rodrigues\nDany Br�geon\nLo�s Monet\nMaxime Poirier\n";
	public static final String HELP = "java -jar boulder_dash.jar -name affiche les noms et pr�noms des devs\n� java -jar boulder_dash.jar -h rappelle la liste des options du programme\n� java -jar boulder_dash.jar -lis ficher.bdcff lis et affiche les param�tres d�un fichierBDCFF (de description de niveaux boulder dash).\n� java -jar boulder_dash.jar -joue fichier.bdcff [-niveau N] offre la possibilit�de jouer sur la console de mani�re interactive dans les niveaux d�crits dans le fichier BDCFF. Le chemin utilis�par le joueur devra �tre sauvegard� dans un fichier au format .dash (voir exemple fourni). Un fichier BDCFF peutcontenir plusieurs niveaux, si l�option -niveau N est pass�e au programme alors il ne jouera que le niveau N,sinon il devra permettre de jouer tous les niveaux d�crits dans le fichier BDCFF, l�un apr�s l�autre.\n� java -jar boulder_dash.jar -cal strategie fichier.bdcff -niveau N calcule unchemin suivant une strat�gie et renvoie ce chemin dans un fichier au format .dash (voir exemple fourni).\n� java -jar boulder_dash.jar -rejoue fichier.dash fichier.bdcff -niveau Nrejoue dans la console une partie de boulder dash en appliquant les d�placements fournis dans le fichier au format.dash fourni.\n� java -jar boulder_dash.jar -simul N strategie strategie fichier.bdcff-niveau N �value les deux strat�gies en param�tre par simulation en lan�ant N parties et renvoie le scoremoyen, la longueur moyenne du chemin obtenu, et le temps moyen mis pour l�obtenir avec chacune des deuxstrat�gies.";
	public static final int FPS = 30;
	public static final int WIDTH_FENETRE = 850;
	public static final int HEIGHT_FENETRE = 650;
	public static final String TITRE_FENETRE = "Boulder Dash 3 en 1";
	public static final String CHEMIN_DOSSIER_SPRITES = "ressources/images";
	public static final String CHEMIN_DOSSIER_SONS = "ressources/sons";
	public static final double VITESSE_JEU_TEMPS_REEL = 1.3;
	public static final int NOMBRE_DE_SPRITES = 67;
	public static final int BOOM = 20;
	public static final int NOMBRE_DE_BOMBES = 5;
	public static final int SCORE_BONUS_PAR_BOMBE = 50;
}
