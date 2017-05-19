package vue;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import entitees.tickables.Rockford;
import main.Constantes;
import main.Partie;
import outils.EnsembleDeSprites;
import outils.Paire;

import static main.Constantes.CHEMIN_DOSSIER_SPRITES;
import static main.Constantes.FPS;

/**
 * La classe Sprites n'est jamais instanci�e, c'est une classe base de donn�es
 * contenant les images des �l�ments du jeu.
 * La m�thode {@link chargerSprites} est appel�e de mani�re static au d�but du
 * programme, elle va charger toutes les images dans la map
 * {@link Sprites#CHARGEMENT_SPRITES}.
 *
 * @author Murloc
 */
public class Sprites {

    /**
     * Contient tous les sprites en valeur et le nom du fichier du sprite en
     * cl�.
     */
    public static final Map<String, Image> CHARGEMENT_SPRITES = new HashMap<String, Image>();

    /**
     * Liste des sprites des amibes.
     */
    public static final List<Image> SPRITES_AMIBES = new ArrayList<Image>();

    /**
     * Liste des sprites des explosions.
     */
    public static final List<Image> SPRITES_EXPLOSIONS = new ArrayList<Image>();

    /**
     * Liste des sprites des diamants.
     */
    public static final List<Image> SPRITES_DIAMANTS = new ArrayList<Image>();

    /**
     * Liste des sprites des lucioles.
     */
    public static final List<Image> SPRITES_LUCIOLES = new ArrayList<Image>();

    /**
     * Liste des sprites des libellules.
     */
    public static final List<Image> SPRITES_LIBELLULES = new ArrayList<Image>();

    /**
     * Liste des sprites des murs.
     */
    public static final List<Image> SPRITES_MURS = new ArrayList<Image>();

    /**
     * Liste des sprites des murs en titane.
     */
    public static final List<Image> SPRITES_MURS_EN_TITANE = new ArrayList<Image>();

    /**
     * Liste des sprites des murs magiques.
     */
    public static final List<Image> SPRITES_MURS_MAGIQUES = new ArrayList<Image>();

    /**
     * Liste des sprites des pierres.
     */
    public static final List<Image> SPRITES_PIERRES = new ArrayList<Image>();

    /**
     * Liste des sprites de la poussi�re.
     */
    public static final List<Image> SPRITES_POUSSIERES = new ArrayList<Image>();

    /**
     * Liste des sprites de Rockford quand il ne bouge pas.
     */
    public static final List<Image> SPRITES_ROCKFORD_SUR_PLACE = new ArrayList<Image>();

    /**
     * Liste des sprites de Rockford quand il bouge vers la droite.
     */
    public static final List<Image> SPRITES_ROCKFORD_DROITE = new ArrayList<Image>();

    /**
     * Liste des sprites de Rockford quand il bouge vers la gauche.
     */
    public static final List<Image> SPRITES_ROCKFORD_GAUCHE = new ArrayList<Image>();

    /**
     * Liste des sprites de Rockford quand il est camoufl�.
     */
    public static final List<Image> SPRITES_CAMOUFLAGE = new ArrayList<Image>();

    /**
     * Liste des sprites de la sortie(Sortie et Mur en titane).
     */
    public static final List<Image> SPRITES_SORTIE = new ArrayList<Image>();

    /**
     * Liste des sprites de la sortie(Sortie et Mur en titane).
     */
    public static final List<Image> SPRITES_BOMBE = new ArrayList<Image>();

    /**
     * Objet permettant de g�rer quel sprites doivent s'afficher � quelle frame.
     *
     * @see EnsembleDeSprites
     */
    public static final EnsembleDeSprites SPRITES_ANIMES = new EnsembleDeSprites();
    /**
     * Entier repr�sentant la vitesse d'animation des amibes.
     */
    public static final int VITESSE_ANIM_AMIBES = 20;
    /**
     * Entier repr�sentant la vitesse d'animation des lucioles.
     */
    public static final int VITESSE_ANIM_LUCIOLES = 10;
    /**
     * Entier repr�sentant la vitesse d'animation de Rockford.
     */
    public static final int VITESSE_ANIM_ROCKFORD = 5;
    /**
     * Entier repr�sentant la vitesse d'animation des libellules.
     */
    public static final int VITESSE_ANIM_LIBELLULES = 10;
    /**
     * Entier repr�sentant la vitesse d'animation des diamants.
     */
    public static final int VITESSE_ANIM_DIAMANTS = 30;
    /**
     * Entier repr�sentant la vitesse d'animation des murs magiques.
     */
    public static final int VITESSE_ANIM_MURS_MAGIQUES = 30;
    /**
     * Entier repr�sentant la vitesse d'animation des explosions.
     */
    public static final int VITESSE_ANIM_EXPLOSIONS = 15;
    /**
     * Image contenant l'image actuelle de Rockford.
     */
    public static Image spriteRockford;

    static {
        chargerSprites(CHEMIN_DOSSIER_SPRITES);
    }

    /**
     * Cette m�thode est appel�e par le programme avant chaque frame.
     * Elle appelle deux autres m�thodes qui vont d�signer quels sprites
     * afficher.
     *
     * @param compteurFPS Le compteur de FPS.
     * @param rockford L'instance de Rockford de la partie actuelle.
     */
    public static void oneFrame(long compteurFPS, Rockford rockford) {
        gererSpritesEntitees(compteurFPS);
        gererSpriteRockford(compteurFPS, rockford);
    }

    /**
     * Cette m�thode choisit pour chaque liste de sprites, quel sprite se
     * situera en premi�re positions de la liste.
     * C'est ce sprite l� qui sera affich� pour repr�senter l'entit�e.
     *
     * @param compteurFPS Le compteur de FPS.
     */
    private static void gererSpritesEntitees(long compteurFPS) {
        // Parcourt la liste de paires de listes de l'objet SPRITES ANIMES et y
        // change le premier element, tout cela en fonction du compteur de FPS.
        for (Paire<Integer, List<Image>> map : SPRITES_ANIMES.get()) {
            Integer vitesse = map.getLeft();
            List<Image> liste = map.getRight();
            if (compteurFPS % (vitesse <= FPS ? FPS / vitesse : 1) == 0) {
                liste.add(liste.get(0));
                liste.remove(0);
            }
        }
    }

    /**
     * Cette m�thode choisit quel sprite repr�sentera Rockford.
     * Elle prend l'instance de Rockford en param�tre pour connaitre son �tat et
     * ainsi choisir le sprite adapt�.
     *
     * @param compteurFPS Le compteur de FPS.
     * @param rockford L'instance de Rockford de la partie actuelle.
     *
     * @see Sprites#changerDirectionSpriteRockford(char)
     */
    private static void gererSpriteRockford(long compteurFPS, Rockford rockford) {
        if (Partie.gererNiveau.getTicks() < 2) {
            changerDirectionSpriteRockford(' ');
        } else if (rockford.getDirection() == ' '
                   && compteurFPS % (VITESSE_ANIM_ROCKFORD < FPS ? FPS / VITESSE_ANIM_ROCKFORD : 1) == 0) {
            changerDirectionSpriteRockford(' ');
        } else if (rockford.getDirection() != ' '
                   &&
                   compteurFPS % (VITESSE_ANIM_ROCKFORD * 2 < FPS ? FPS / (VITESSE_ANIM_ROCKFORD * 2) : 1) == 0) {
            if (rockford.getDirection() == 'd'
                || rockford.getDirection() != 'g' && rockford.getAncienneDirection() == 'd') {
                rockford.setAncienneDirection('d');
                changerDirectionSpriteRockford('d');
            } else if (rockford.getDirection() == 'g' || rockford.getAncienneDirection() == 'g') {
                rockford.setAncienneDirection('g');
                changerDirectionSpriteRockford('g');
            }
        }
    }

    /**
     * Cette m�thode est appel�e par
     * {@link Sprites#gererSpriteRockford(long, Rockford)} afin de changer la
     * direction des sprites de Rockford.
     */
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

    /**
     * Cette m�thode est appel�e statiquement par le programme afin de charger
     * les sprites.
     * Elle prend en param�tre le dossier o� se trouvent les images et lis
     * toutes les images pr�sentent dans ce fichier et les mets dans
     * {@link Sprites#CHARGEMENT_SPRITES} avec le nom du fichier en cl� et
     * l'image en valeur.
     * Ensuite elle appele des m�thodes permettant de r�partir les images de
     * {@link Sprites#CHARGEMENT_SPRITES} dans les listes apropri�es.
     *
     * @param cheminDossier Le dossier contenant les images.
     */
    public static void chargerSprites(String cheminDossier) {
        final File dossier = new File(cheminDossier);
        int compteur = 0;
        for (final File fileEntry : dossier.listFiles()) {
            try {
                File f = new File(cheminDossier + "/" + fileEntry.getName());

                CHARGEMENT_SPRITES.put(fileEntry.getName(), ImageIO.read(f));
                compteur++;
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        if (compteur < Constantes.NOMBRE_DE_SPRITES) {
            System.err.println(
              (Constantes.NOMBRE_DE_SPRITES - compteur) + " sprite(s) n'a(n'ont) pas pu être chargé(s).");
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

    /**
     * Cette m�thode change le sprite de {@link Sprites#SPRITES_SORTIE} pour y
     * mettre le sprite du mur en titane.
     */
    public static void cacherSortie() {
        SPRITES_SORTIE.set(0, CHARGEMENT_SPRITES.get("titane.png"));
    }

    /**
     * Cette m�thode change le sprite de {@link Sprites#SPRITES_SORTIE} pour y
     * mettre le sprite de la sortie.
     */
    public static void devoilerSortie() {
        SPRITES_SORTIE.set(0, CHARGEMENT_SPRITES.get("sortie.png"));
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites du camouflage de Rockford dans la liste apropri�e.
     */
    private static void chargerSpritesCamouflage() {
        SPRITES_CAMOUFLAGE.add(CHARGEMENT_SPRITES.get("camouflage.png"));

    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites des libellules dans la liste apropri�e.
     */
    private static void chargerSpritesLibellules() {
        SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule1.png"));
        SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule2.png"));
        SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule.png"));
        SPRITES_LIBELLULES.add(CHARGEMENT_SPRITES.get("libellule2.png"));
        SPRITES_ANIMES.add(VITESSE_ANIM_LIBELLULES, SPRITES_LIBELLULES);
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites des explosions dans la liste apropri�e.
     */
    private static void chargerSpritesExplosions() {
        SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux2.png"));
        SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux3.png"));
        SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux4.png"));
        SPRITES_EXPLOSIONS.add(CHARGEMENT_SPRITES.get("explosioncailloux5.png"));
        SPRITES_ANIMES.add(VITESSE_ANIM_EXPLOSIONS, SPRITES_EXPLOSIONS);
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites des lucioles dans la liste apropri�e.
     */
    private static void chargerSpritesLucioles() {
        SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole1.png"));
        SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole2.png"));
        SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole3.png"));
        SPRITES_LUCIOLES.add(CHARGEMENT_SPRITES.get("luciole4.png"));
        SPRITES_ANIMES.add(VITESSE_ANIM_LUCIOLES, SPRITES_LUCIOLES);
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites des amibes dans la liste apropri�e.
     */
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

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites des diamants dans la liste apropri�e.
     */
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

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites des murs magiques dans la liste apropri�e.
     */
    private static void chargerSpritesMursMagiques() {
        SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("magique.png"));
        SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique2.png"));
        SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique3.png"));
        SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique4.png"));
        SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique3.png"));
        SPRITES_MURS_MAGIQUES.add(CHARGEMENT_SPRITES.get("murmagique2.png"));
        SPRITES_ANIMES.add(VITESSE_ANIM_MURS_MAGIQUES, SPRITES_MURS_MAGIQUES);
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites des listes de sprites de Rockford dans la liste apropri�e.
     */
    private static void chargerSpritesRockford() {
        for (int i = 0; i < 15; i++) { SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford.png")); }
        SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford1.png"));
        SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford2.png"));
        SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford2.png"));
        SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford1.png"));
        SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford3.png"));
        SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford4.png"));
        for (int i = 0; i < 15; i++) { SPRITES_ROCKFORD_SUR_PLACE.add(CHARGEMENT_SPRITES.get("rockford5.png")); }
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

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * le sprite du mur dans la liste apropri�e.
     */
    private static void chargerSpritesMurs() {
        SPRITES_MURS.add(CHARGEMENT_SPRITES.get("mur.png"));
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * le sprite de la pierre dans la liste apropri�e.
     */
    private static void chargerSpritesPierres() {
        SPRITES_PIERRES.add(CHARGEMENT_SPRITES.get("roc.png"));
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * les sprites de la sortie dans la liste apropri�e.
     */
    private static void chargerSpritesSortie() {
        SPRITES_SORTIE.add(CHARGEMENT_SPRITES.get("titane.png"));
        SPRITES_SORTIE.add(CHARGEMENT_SPRITES.get("sortie.png"));
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * le sprite des murs en titane dans la liste apropri�e.
     */
    private static void chargerSpritesMursEnTitane() {
        SPRITES_MURS_EN_TITANE.add(CHARGEMENT_SPRITES.get("titane.png"));
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * le sprite de la poussi�re dans la liste apropri�e.
     */
    private static void chargerSpritesPoussieres() {
        SPRITES_POUSSIERES.add(CHARGEMENT_SPRITES.get("poussiere.png"));
    }

    /**
     * Cette m�thode regarde dans {@link Sprites#CHARGEMENT_SPRITES} et dispose
     * le sprite de la bombe dans la liste apropri�e.
     */
    private static void chargerSpritesBombe() {
        SPRITES_BOMBE.add(CHARGEMENT_SPRITES.get("Bombe.png"));
        SPRITES_BOMBE.add(CHARGEMENT_SPRITES.get("BombeRouge.png"));
    }

}
