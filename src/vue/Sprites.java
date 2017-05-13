package vue;

import static constantes.Constantes.CHEMIN_DOSSIER_SPRITES;
import static constantes.Constantes.FPS;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import constantes.Constantes;
import entitees.tickables.Rockford;
import main.Partie;
import outils.EnsembleDeSprites;
import outils.Paire;

/**
 * La classe Sprites n'est jamais instanciée, c'est une classe base de données
 * contenant les images des éléments du jeu.
 * 
 * @author Murloc
 */
public class Sprites {

	/*
	 * 
	 */
	
	public static final Map<String, Image> CHARGEMENT_SPRITES = new HashMap<String, Image>();
	public static final List<Image> SPRITES_AMIBES = new ArrayList<Image>();
	public static final List<Image> SPRITES_EXPLOSIONS = new ArrayList<Image>();
	public static final List<Image> SPRITES_DIAMANTS = new ArrayList<Image>();
	public static final List<Image> SPRITES_LUCIOLES = new ArrayList<Image>();
	public static final List<Image> SPRITES_LIBELLULES = new ArrayList<Image>();
	public static final List<Image> SPRITES_MURS = new ArrayList<Image>();
	public static final List<Image> SPRITES_MURS_EN_TITANE = new ArrayList<Image>();
	public static final List<Image> SPRITES_MURS_MAGIQUES = new ArrayList<Image>();
	public static final List<Image> SPRITES_PIERRES = new ArrayList<Image>();
	public static final List<Image> SPRITES_POUSSIERES = new ArrayList<Image>();
	public static final List<Image> SPRITES_ROCKFORD_SUR_PLACE = new ArrayList<Image>();
	public static final List<Image> SPRITES_ROCKFORD_DROITE = new ArrayList<Image>();
	public static final List<Image> SPRITES_ROCKFORD_GAUCHE = new ArrayList<Image>();
	public static final List<Image> SPRITES_SORTIE = new ArrayList<Image>();
	public static final List<Image> SPRITES_BOMBE = new ArrayList<Image>();
	public static final List<Image> SPRITES_CAMOUFLAGE = new ArrayList<Image>();
	public static final EnsembleDeSprites SPRITES_ANIMES = new EnsembleDeSprites();
	public static Image spriteRockford;
	public static final int VITESSE_ANIM_AMIBES = 20;
	public static final int VITESSE_ANIM_LUCIOLES = 10;
	public static final int VITESSE_ANIM_ROCKFORD = 5;
	public static final int VITESSE_ANIM_LIBELLULES = 10;
	public static final int VITESSE_ANIM_DIAMANTS = 30;
	public static final int VITESSE_ANIM_MURS_MAGIQUES = 30;
	public static final int VITESSE_ANIM_EXPLOSIONS = 15;

	// Apelle la méthode qui charge les images.
	static {
		chargerSprites(CHEMIN_DOSSIER_SPRITES);
	}

	public static void oneFrame(long compteurFPS, Rockford rockford) {
		for (Paire<Integer, List<Image>> map : SPRITES_ANIMES.get()) {
			Integer vitesse = map.getLeft();
			List<Image> liste = map.getRight();
			if (compteurFPS % (vitesse <= FPS ? FPS / vitesse : 1) == 0) {
				liste.add(liste.get(0));
				liste.remove(0);
			}
		}
		/*
		 * Gère
		 */
		if (Partie.gererNiveau.getTicks() < 2) {
			changerDirectionSpriteRockford(' ');
		} else if (rockford.getDirection() == ' '
				&& compteurFPS % ((VITESSE_ANIM_ROCKFORD < FPS) ? FPS / (VITESSE_ANIM_ROCKFORD) : 1) == 0) {
			changerDirectionSpriteRockford(' ');
		} else if (rockford.getDirection() != ' '
				&& compteurFPS % (((VITESSE_ANIM_ROCKFORD * 2) < FPS) ? FPS / (VITESSE_ANIM_ROCKFORD * 2) : 1) == 0) {
			if (rockford.getDirection() == 'd'
					|| (rockford.getDirection() != 'g' && rockford.getAncienneDirection() == 'd')) {
				rockford.setAncienneDirection('d');
				changerDirectionSpriteRockford('d');
			} else if (rockford.getDirection() == 'g' || rockford.getAncienneDirection() == 'g') {
				rockford.setAncienneDirection('g');
				changerDirectionSpriteRockford('g');
			}
		}
	}

	private static void changerDirectionSpriteRockford(char directionDeRockford) {
		List<Image> liste;
		if (directionDeRockford == ' ') {
			liste = SPRITES_ROCKFORD_SUR_PLACE;
		} else if (directionDeRockford == 'd') {
			liste = SPRITES_ROCKFORD_DROITE;
		} else {
			liste = SPRITES_ROCKFORD_GAUCHE;
		}
		liste.add(liste.get(0));
		liste.remove(0);
		spriteRockford = liste.get(0);
	}

	public static void chargerSprites(String cheminDossier) {
		final File dossier = new File(cheminDossier);
		int compteur = 0;
		for (final File fileEntry : dossier.listFiles()) {
			try {
				File f = new File(cheminDossier + "/" + fileEntry.getName());

				CHARGEMENT_SPRITES.put(fileEntry.getName(), (ImageIO.read(f)));
				compteur++;
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		if (compteur < Constantes.NOMBRE_DE_SPRITES) {
			System.err.println(
					(Constantes.NOMBRE_DE_SPRITES - compteur) + " sprite(s) n'a(n'ont) pas pu Ãªtre chargÃ©(s).");
		}
		chargerSpritesAmibes();
		chargerSpritesLibellules();
		chargerSpritesLucioles();
		chargerSpritesDiamants();
		chargerSpritesMursMagiques();
		chargerSpritesMurs();
		chargerSpritesPoussieres();
		chargerSpritesRockford();
		chargerSpritesMursEnTitane();
		chargerSpritesPierres();
		chargerSpritesSortie();
		chargerSpritesExplosions();
		chargerSpritesBombe();
		chargerSpritesCamouflage();
	}

	public static void cacherSortie() {
		Sprites.SPRITES_SORTIE.set(0, CHARGEMENT_SPRITES.get("titane.png"));
	}

	public static void devoilerSortie() {
		Sprites.SPRITES_SORTIE.set(0, CHARGEMENT_SPRITES.get("sortie.png"));
	}

	private static void chargerSpritesCamouflage() {
		SPRITES_CAMOUFLAGE.add(CHARGEMENT_SPRITES.get("camouflage.png"));

	}

	private static void chargerSpritesLibellules() {
		SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule1.png"));
		SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule2.png"));
		SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule.png"));
		SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule2.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_LIBELLULES, SPRITES_LIBELLULES);
	}

	private static void chargerSpritesExplosions() {
		SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux2.png"));
		SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux3.png"));
		SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux4.png"));
		SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux5.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_EXPLOSIONS, SPRITES_EXPLOSIONS);
	}

	private static void chargerSpritesLucioles() {
		SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole1.png"));
		SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole2.png"));
		SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole3.png"));
		SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole4.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_LUCIOLES, SPRITES_LUCIOLES);
	}

	private static void chargerSpritesAmibes() {
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe.png"));
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe2.png"));
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe3.png"));
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe4.png"));
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe5.png"));
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe6.png"));
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe7.png"));
		SPRITES_AMIBES.add(CHARGEMENT_SPRITES.get("amibe8.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_AMIBES, SPRITES_AMIBES);
	}

	private static void chargerSpritesDiamants() {
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant.png"));
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant2.png"));
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant3.png"));
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant4.png"));
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant5.png"));
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant6.png"));
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant7.png"));
		SPRITES_DIAMANTS.add(CHARGEMENT_SPRITES.get("diamant8.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_DIAMANTS, SPRITES_DIAMANTS);
	}

	private static void chargerSpritesMursMagiques() {
		SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("magique.png"));
		SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique2.png"));
		SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique3.png"));
		SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique4.png"));
		SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique3.png"));
		SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique2.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_MURS_MAGIQUES, SPRITES_MURS_MAGIQUES);
	}

	private static void chargerSpritesRockford() {
		for (int i = 0; i < 15; i++)
			SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford1.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford2.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford2.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford1.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford3.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford4.png"));
		for (int i = 0; i < 15; i++)
			SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford5.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford6.png"));
		SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford7.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_ROCKFORD, SPRITES_ROCKFORD_SUR_PLACE);

		SPRITES_ROCKFORD_GAUCHE.add(CHARGEMENT_SPRITES.get("rockgauche1.png"));
		SPRITES_ROCKFORD_GAUCHE.add(CHARGEMENT_SPRITES.get("rockgauche2.png"));
		SPRITES_ROCKFORD_GAUCHE.add(CHARGEMENT_SPRITES.get("rockgauche3.png"));
		SPRITES_ROCKFORD_GAUCHE.add(CHARGEMENT_SPRITES.get("rockgauche4.png"));
		SPRITES_ROCKFORD_GAUCHE.add(CHARGEMENT_SPRITES.get("rockgauche5.png"));
		SPRITES_ROCKFORD_GAUCHE.add(CHARGEMENT_SPRITES.get("rockgauche6.png"));
		SPRITES_ROCKFORD_GAUCHE.add(CHARGEMENT_SPRITES.get("rockgauche7.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_ROCKFORD, SPRITES_ROCKFORD_GAUCHE);

		SPRITES_ROCKFORD_DROITE.add(CHARGEMENT_SPRITES.get("rockdroite1.png"));
		SPRITES_ROCKFORD_DROITE.add(CHARGEMENT_SPRITES.get("rockdroite2.png"));
		SPRITES_ROCKFORD_DROITE.add(CHARGEMENT_SPRITES.get("rockdroite3.png"));
		SPRITES_ROCKFORD_DROITE.add(CHARGEMENT_SPRITES.get("rockdroite4.png"));
		SPRITES_ROCKFORD_DROITE.add(CHARGEMENT_SPRITES.get("rockdroite5.png"));
		SPRITES_ROCKFORD_DROITE.add(CHARGEMENT_SPRITES.get("rockdroite6.png"));
		SPRITES_ROCKFORD_DROITE.add(CHARGEMENT_SPRITES.get("rockdroite7.png"));
		SPRITES_ANIMES.add(VITESSE_ANIM_ROCKFORD, SPRITES_ROCKFORD_DROITE);
	}

	private static void chargerSpritesMurs() {
		SPRITES_MURS.add(CHARGEMENT_SPRITES.get("mur.png"));
	}

	private static void chargerSpritesPierres() {
		SPRITES_PIERRES.add(CHARGEMENT_SPRITES.get("roc.png"));
	}

	private static void chargerSpritesSortie() {
		SPRITES_SORTIE.add(CHARGEMENT_SPRITES.get("titane.png"));
		SPRITES_SORTIE.add(CHARGEMENT_SPRITES.get("sortie.png"));
	}

	private static void chargerSpritesMursEnTitane() {
		SPRITES_MURS_EN_TITANE.add(CHARGEMENT_SPRITES.get("titane.png"));
	}

	private static void chargerSpritesPoussieres() {
		SPRITES_POUSSIERES.add(CHARGEMENT_SPRITES.get("poussiere.png"));
	}

	private static void chargerSpritesBombe() {
		SPRITES_BOMBE.add(CHARGEMENT_SPRITES.get("Bombe.png"));
		SPRITES_BOMBE.add(CHARGEMENT_SPRITES.get("BombeRouge.png"));
	}

}
