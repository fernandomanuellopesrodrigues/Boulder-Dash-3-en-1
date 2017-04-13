package constantes;

public class Constantes {
	public static final String NOMS = "\n\nCelso De Carvalho Rodrigues\nDany Brégeon\nLoïs Monet\nMaxime Poirier\n";
	public static final String HELP = "java -jar boulder_dash.jar -name affiche les noms et prénoms des devs\n— java -jar boulder_dash.jar -h rappelle la liste des options du programme\n— java -jar boulder_dash.jar -lis ficher.bdcff lis et affiche les paramètres d’un fichierBDCFF (de description de niveaux boulder dash).\n— java -jar boulder_dash.jar -joue fichier.bdcff [-niveau N] offre la possibilitéde jouer sur la console de manière interactive dans les niveaux décrits dans le fichier BDCFF. Le chemin utilisépar le joueur devra être sauvegardé dans un fichier au format .dash (voir exemple fourni). Un fichier BDCFF peutcontenir plusieurs niveaux, si l’option -niveau N est passée au programme alors il ne jouera que le niveau N,sinon il devra permettre de jouer tous les niveaux décrits dans le fichier BDCFF, l’un après l’autre.\n— java -jar boulder_dash.jar -cal strategie fichier.bdcff -niveau N calcule unchemin suivant une stratégie et renvoie ce chemin dans un fichier au format .dash (voir exemple fourni).\n— java -jar boulder_dash.jar -rejoue fichier.dash fichier.bdcff -niveau Nrejoue dans la console une partie de boulder dash en appliquant les déplacements fournis dans le fichier au format.dash fourni.\n— java -jar boulder_dash.jar -simul N strategie strategie fichier.bdcff-niveau N évalue les deux stratégies en paramètre par simulation en lançant N parties et renvoie le scoremoyen, la longueur moyenne du chemin obtenu, et le temps moyen mis pour l’obtenir avec chacune des deuxstratégies.";
	// valeur entre 25 et +infini
	public static final int FPS = 30;
	public static final int WIDTH_FENETRE = 1000;
	public static final int HEIGHT_FENETRE = 1000;
	public static final String TITRE_FENETRE = "Boulder Dash 3 en 1";
	public static final String CHEMIN_DOSSIER_SPRITES = "images";
}
