package main;

public class Constantes {

    // String affich� quand on entre en argument "-noms".
    public static final String NOMS = "\n\nCelso De Carvalho Rodrigues\nDany Brégeon\nLoïs Monet\nMaxime Poirier\n";

    // String affich� quand on entre en argument "-help".
    public static final String HELP =
      "\n\n\"-name\" : Affiche les noms et prénoms des devs\n\n\"-lis ficher.bdcff\" : Lis et affiche les paramètres " +
      "d'un fichierBDCFF\n\n\"-joue fichier.bdcff [-niveau N]\" : Permet de jouer aux niveaux décrits dans le fichier" +
      " BDCFF. Si l'option -niveau N est passée au programme alors ne joue que le niveau N, sinon lance tous les " +
      "niveaux, l'un après l'autre.\n\n\"-cal strategie fichier.bdcff -niveau N\" : Calcule un chemin suivant une " +
      "stratégie et renvoie ce chemin dans un fichier au format .dash.\n\n\"-rejoue fichier.dash fichier.bdcff " +
      "-niveau N\" : Rejoue dans une partie de boulder dash en appliquant les déplacements fournis dans le fichier au" +
      " format.dash fourni.\n\n\"-simul N strategie strategie fichier.bdcff-niveau N\" : Evalue les deux stratégies " +
      "en paramètre par simulation en lançant N parties et renvoie le score moyen, la longueur moyenne du chemin " +
      "obtenu, et le temps moyen mis pour l'obtenir avec chacune des deux stratégies.\n";

    // Entier permettant de choisir le nombre d'images par seconde en mode
    // graphique.
    public static final int FPS = 30;

    // Largeur en pixels par d�faut de la fen�tre.
    public static final int WIDTH_FENETRE = 850;

    // Hauteur en pixels par d�faut de la fen�tre.
    public static final int HEIGHT_FENETRE = 650;

    // Titre de la fen�tre.
    public static final String TITRE_FENETRE = "Boulder Dash 3 en 1";

    // Chemin relatif du dossier contenant les sprites du jeu.
    public static final String CHEMIN_DOSSIER_SPRITES = "ressources/images";

    // Chemin relatif du dossier contenant les sons du jeu.
    public static final String CHEMIN_DOSSIER_SONS = "ressources/sons/";

    // Entier indiquant la vitesse globale du jeu en mode temps r�el.
    public static final double VITESSE_JEU_TEMPS_REEL = 1.3;

    // Entier indiquant le nombre de sprites existants.
    public static final int NOMBRE_DE_SPRITES = 67;

    // Nombre de ticks entre le placement d'une bombe et son explosion.
    public static final int BOOM = 20;

    // Nombre de bombes dont dispose le joueur � chaque partie.
    public static final int NOMBRE_DE_BOMBES = 5;

    // Score bonus par bombe attribu� au joueur � chaque fin de niveau.
    public static final int SCORE_BONUS_PAR_BOMBE = 0;

    // Bool�en sevant � activer l'affichage des FPS sur la fen�tre.
    public static final boolean SYSOUT_FPS = true;

    // Bool�en sevant � activer l'affichage des TPS sur la fen�tre.
    public static final boolean SYSOUT_TPS = true;

    // Bool�en servant � activer les sons.
    public static final boolean SONS = true;

	/*
     * Constantes de l'ia g�n�tique.
	 */

    /**
     *
     */
    public static final int NOMBRE_DE_TRY_GENERATION     = 100;
    public static final int POURCENTAGE_DE_SURVIVANTS    = 20;
    public static final int POURCENTAGE_DE_CROISEMENTS   = 0;
    public static final int POURCENTAGE_DE_MUTATIONS     = 80;
    public static final int POURCENTAGE_DES_SELECTIONNES = 30;
    public static final int POURCENTAGE_DE_ALEATOIRE     = 0;
    public static final int VALEUR_SCORE_MOYENNE         = 750;
}
