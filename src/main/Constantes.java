package main;

public interface Constantes {

    // String affich\u00E9 quand on entre en argument \"-noms\".
    String NOMS = "\n\nCelso De Carvalho Rodrigues\nDany Brégeon\nLoïs Monet\nMaxime Poirier\n";

    // String affich\u00E9 quand on entre en argument \"-help\".
    String HELP =
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
    int FPS = 30;

    // Largeur en pixels par d\u00E9faut de la fen\u00E9tre.
    int WIDTH_FENETRE = 850;

    // Hauteur en pixels par d\u00E9faut de la fen\u00E9tre.
    int HEIGHT_FENETRE = 650;

    // Titre de la fen\u00E9tre.
    String TITRE_FENETRE = "Boulder Dash 3 en 1";

    // Chemin relatif du dossier contenant les sprites du jeu.
    String CHEMIN_DOSSIER_SPRITES = "ressources/images";

    // Chemin relatif du dossier contenant les sons du jeu.
    String CHEMIN_DOSSIER_SONS = "ressources/sons/";

    // Entier indiquant la vitesse globale du jeu en mode temps r\u00E9el.
    double VITESSE_JEU_TEMPS_REEL = 1.3;

    // Entier indiquant le nombre de sprites existants.
    int NOMBRE_DE_SPRITES = 67;

    // Nombre de ticks entre le placement d'une bombe et son explosion.
    int BOOM = 20;

    // Nombre de bombes dont dispose le joueur \u00E0 chaque partie.
    int NOMBRE_DE_BOMBES = 5;

    // Score bonus par bombe attribu\u00E9 au joueur \u00E0 chaque fin de niveau.
    int SCORE_BONUS_PAR_BOMBE = 0;

    // Booleen sevant \u00E0 activer l'affichage des FPS sur la fen\u00E9tre.
    boolean SYSOUT_FPS = true;

    // Booleen sevant \u00E0 activer l'affichage des TPS sur la fen\u00E9tre.
    boolean SYSOUT_TPS = true;

    // Booleen servant \u00E0 activer les sons.
    boolean SONS = true;

	/*
     * Constantes de l'ia g\u00E9n\u00E9tique.
	 */

    /**
     *
     */
    int NOMBRE_DE_TRY_GENERATION     = 100;
    int POURCENTAGE_DE_SURVIVANTS    = 20;
    int POURCENTAGE_DE_CROISEMENTS   = 0;
    int POURCENTAGE_DE_MUTATIONS     = 80;
    int POURCENTAGE_DES_SELECTIONNES = 30;
    int POURCENTAGE_DE_ALEATOIRE     = 0;
    int VALEUR_SCORE_MOYENNE         = 750;
}
