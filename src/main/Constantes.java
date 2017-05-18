package main;

public class Constantes {

	// String affiché quand on entre en argument "-noms".
	public static final String NOMS = "\n\nCelso De Carvalho Rodrigues\nDany Brï¿½geon\nLoï¿½s Monet\nMaxime Poirier\n";

	// String affiché quand on entre en argument "-help".
	public static final String HELP = "java -jar boulder_dash.jar -name affiche les noms et prï¿½noms des devs\nï¿½ java -jar boulder_dash.jar -h rappelle la liste des options du programme\nï¿½ java -jar boulder_dash.jar -lis ficher.bdcff lis et affiche les paramï¿½tres dï¿½un fichierBDCFF (de description de niveaux boulder dash).\nï¿½ java -jar boulder_dash.jar -joue fichier.bdcff [-niveau N] offre la possibilitï¿½de jouer sur la console de maniï¿½re interactive dans les niveaux dï¿½crits dans le fichier BDCFF. Le chemin utilisï¿½par le joueur devra ï¿½tre sauvegardï¿½ dans un fichier au format .dash (voir exemple fourni). Un fichier BDCFF peutcontenir plusieurs niveaux, si lï¿½option -niveau N est passï¿½e au programme alors il ne jouera que le niveau N,sinon il devra permettre de jouer tous les niveaux dï¿½crits dans le fichier BDCFF, lï¿½un aprï¿½s lï¿½autre.\nï¿½ java -jar boulder_dash.jar -cal strategie fichier.bdcff -niveau N calcule unchemin suivant une stratï¿½gie et renvoie ce chemin dans un fichier au format .dash (voir exemple fourni).\nï¿½ java -jar boulder_dash.jar -rejoue fichier.dash fichier.bdcff -niveau Nrejoue dans la console une partie de boulder dash en appliquant les dï¿½placements fournis dans le fichier au format.dash fourni.\nï¿½ java -jar boulder_dash.jar -simul N strategie strategie fichier.bdcff-niveau N ï¿½value les deux stratï¿½gies en paramï¿½tre par simulation en lanï¿½ant N parties et renvoie le scoremoyen, la longueur moyenne du chemin obtenu, et le temps moyen mis pour lï¿½obtenir avec chacune des deuxstratï¿½gies.";

	// Entier permettant de choisir le nombre d'images par seconde en mode graphique.
	public static final int FPS = 30;

	// Largeur en pixels par défaut de la fenêtre.
	public static final int WIDTH_FENETRE = 850;

	// Hauteur en pixels par défaut de la fenêtre.
	public static final int HEIGHT_FENETRE = 650;

	// Titre de la fenêtre.
	public static final String TITRE_FENETRE = "Boulder Dash 3 en 1";

	// Chemin relatif du dossier contenant les sprites du jeu.
	public static final String CHEMIN_DOSSIER_SPRITES = "ressources/images";

	// Chemin relatif du dossier contenant les sons du jeu.
	public static final String CHEMIN_DOSSIER_SONS = "ressources/sons/";

	// Entier indiquant la vitesse globale du jeu en mode temps réel.
	public static final double VITESSE_JEU_TEMPS_REEL = 1.3;

	// Entier indiquant le nombre de sprites existants.
	public static final int NOMBRE_DE_SPRITES = 67;

	// Nombre de ticks entre le placement d'une bombe et son explosion.
	public static final int BOOM = 20;

	// Nombre de bombes dont dispose le joueur à chaque partie.
	public static final int NOMBRE_DE_BOMBES = 5;

	// Score bonus par bombe attribué au joueur à chaque fin de niveau.
	public static final int SCORE_BONUS_PAR_BOMBE = 0;

	// Booléen sevant à activer l'affichage des FPS sur la fenêtre.
	public static final boolean SYSOUT_FPS = true;

	// Booléen sevant à activer l'affichage des TPS sur la fenêtre.
	public static final boolean SYSOUT_TPS = true;

	// Booléen servant à activer les sons.
	public static final boolean SONS = false;

	/*
	 * Constantes de l'ia génétique.
	 */
	public static final int NOMBRE_DE_TRY_GENERATION = 200;
	public static final int POURCENTAGE_DE_SURVIVANTS = 10;
	public static final int POURCENTAGE_DE_CROISEMENTS = 0;
	public static final int POURCENTAGE_DE_MUTATIONS = 90;
	public static final int POURCENTAGE_DES_SELECTIONNES = 25;
	public static final int POURCENTAGE_DE_ALEATOIRE = 0;
	public static final int VALEUR_SCORE_MOYENNE = 750;
}
