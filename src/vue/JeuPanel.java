package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import entitees.abstraites.Entitee;
import entitees.fixes.Amibe;
import entitees.fixes.Mur;
import entitees.fixes.MurEnTitane;
import entitees.fixes.MurMagique;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.tickables.Diamant;
import entitees.tickables.Explosion;
import entitees.tickables.Libellule;
import entitees.tickables.Luciole;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;
import main.Coeur;
import main.Partie;

public class JeuPanel extends JPanel {

	private Entitee[][] map;
	private long compteurFPS;

	public JeuPanel() {
		map = Partie.gererNiveau.getNiveau().getMap();
		compteurFPS = 0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		compteurFPS++;
		Sprites.gererSprites(compteurFPS, Partie.gererNiveau.getNiveau().getRockford());
		int largeur_case;
		int hauteur_case;
		largeur_case = getWidth() / map.length;
		hauteur_case = getHeight() / map[0].length;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				Image image = getSprite(map[j][i]);
				if (image != null) {
					g.drawImage(image, (j) * largeur_case, (i) * hauteur_case, largeur_case, hauteur_case, this);
				}

			}
		}

	}

	private Image getSprite(Entitee e) {
		if (e.getClass().equals(Mur.class)) {
			return Sprites.SPRITES_MURS.get(0);
		} else if (e.getClass().equals(Diamant.class)) {
			return Sprites.SPRITES_DIAMANTS.get(0);
		} else if (e.getClass().equals(Amibe.class)) {
			return Sprites.SPRITES_AMIBES.get(0);
		} else if (e.getClass().equals(Luciole.class)) {
			return Sprites.SPRITES_LUCIOLES.get(0);
		} else if (e.getClass().equals(Libellule.class)) {
			return Sprites.SPRITES_LIBELLULES.get(0);
		} else if (e.getClass().equals(MurEnTitane.class)) {
			return Sprites.SPRITES_MURS_EN_TITANE.get(0);
		} else if (e.getClass().equals(Pierre.class)) {
			return Sprites.SPRITES_PIERRES.get(0);
		} else if (e.getClass().equals(Poussiere.class)) {
			return Sprites.SPRITES_POUSSIERES.get(0);
		} else if (e.getClass().equals(Rockford.class)) {
			return Sprites.spriteRockford;
		} else if (e.getClass().equals(Sortie.class)) {
			return Sprites.SPRITES_SORTIE.get(0);
		} else if (e.getClass().equals(MurMagique.class)) {
			return Sprites.SPRITES_MURS_MAGIQUES.get(0);
		} else if (e.getClass().equals(Explosion.class)) {
			return Sprites.SPRITES_EXPLOSIONS.get(0);
		} else {
			return null;
		}
	}

}
