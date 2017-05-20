package entitees.fixes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import entitees.abstraites.Entitee;
import entitees.tickables.Diamant;
import entitees.tickables.Pierre;

import static entitees.abstraites.Entitee.Type.Amibe;
import static entitees.abstraites.Entitee.Type.Poussiere;
import static entitees.abstraites.Entitee.Type.Vide;
import static java.lang.Math.random;
import static main.Constantes.MAX_AMIBES;
import static main.Partie.checkEntite;
import static main.Partie.gererNiveau;
import static main.Partie.setEntiteParPosition;

public final class Amibe extends Entitee {

    public Amibe(final int positionX, final int positionY) {
        super(positionX, positionY, Amibe, true);
    }

    @Override
    public Entitee nouvelle() {
        return new Amibe(getPositionX(), getPositionY());
    }

    public boolean sePropager() {
        final List<Point> points = new ArrayList<>(4);
        if (placeLibre(getPositionX() + 1, getPositionY())) {
            points.add(new Point(getPositionX() + 1, getPositionY()));
        }
        if (placeLibre(getPositionX() - 1, getPositionY())) {
            points.add(new Point(getPositionX() - 1, getPositionY()));
        }
        if (placeLibre(getPositionX(), getPositionY() - 1)) {
            points.add(new Point(getPositionX(), getPositionY() - 1));
        }
        if (placeLibre(getPositionX(), getPositionY() + 1)) {
            points.add(new Point(getPositionX(), getPositionY() + 1));
        }
        if (points.isEmpty()) {
            return false;
        }
        final int rng = (int) (random() * points.size());
        final Amibe amibe = new Amibe(points.get(rng).x, points.get(rng).y);
        setEntiteParPosition(points.get(rng).x, points.get(rng).y, amibe);
        gererNiveau.ajouterAmibe(amibe);
        checkDetruireAmibes();
        return true;

    }

    private static void checkDetruireAmibes() {
        if (gererNiveau.getListeAmibes().size() >= MAX_AMIBES) {
            gererNiveau.getListeAmibes().addAll(gererNiveau.getListeAmibesAjout());
            for (final Amibe amibe : gererNiveau.getListeAmibes()) {
                final int px = amibe.getPositionX();
                final int py = amibe.getPositionY();
                setEntiteParPosition(px, py, new Pierre(px, py));
            }
            gererNiveau.getListeAmibes().clear();
            gererNiveau.getListeAmibesAjout().clear();
        }
    }

    public static void transformerTousLesAmibesEnDiamant() {
        for (final Amibe amibe : gererNiveau.getListeAmibes()) {
            final int px = amibe.getPositionX();
            final int py = amibe.getPositionY();
            final Diamant diamant = new Diamant(px, py);
            setEntiteParPosition(px, py, diamant);
            gererNiveau.ajouterTickable(diamant);
        }
        gererNiveau.getListeAmibes().clear();
        gererNiveau.getListeAmibesAjout().clear();
    }

    @Override
    public boolean mourir() {
        gererNiveau.getListeAmibes().remove(this);
        return super.mourir();
    }

    private static boolean placeLibre(final int positionX, final int positionY) {
        return checkEntite(positionX, positionY, Vide) || checkEntite(positionX, positionY, Poussiere);
    }
}
