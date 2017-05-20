package entitees.tickables;

import java.util.ArrayList;
import java.util.List;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;

import static entitees.abstraites.Entitee.Type.Amibe;
import static entitees.abstraites.Entitee.Type.Explosion;
import static entitees.abstraites.Entitee.Type.Libellule;
import static entitees.abstraites.Entitee.Type.Luciole;
import static entitees.abstraites.Entitee.Type.MurMagique;
import static entitees.abstraites.Entitee.Type.Pierre;
import static entitees.abstraites.Entitee.Type.Rockford;
import static entitees.abstraites.Entitee.Type.Vide;
import static main.Constantes.BAS;
import static main.Partie.SONS;
import static main.Partie.checkEntite;

public final class Pierre extends Tickable {

    private final List<Type> deplacementsPossiblesChute = new ArrayList<>(10);

    public Pierre(final int positionX, final int positionY) {
        super(positionX, positionY, Pierre, true);
        addDeplacementPossible(Rockford, Luciole, Libellule, Amibe, MurMagique, Explosion);
        deplacementsPossiblesChute.add(Vide);
    }

    @Override
    public Entitee nouvelle() {
        return new Pierre(getPositionX(), getPositionY());
    }

    @Override
    public void tick() {
        if (!isBloque() || isChute()) {
            gererChute();
        } else {
            glisser();
        }
        bloquer();
    }

    @Override
    public int contactAutreEntitee(final Entitee entitee) {
        setDirection(BAS);
        int retour = 0;
        switch (entitee.getType()) {
            case Explosion:
            case Luciole:
            case Rockford:
                exploser(false);
                break;
            case Amibe:
            case MurMagique:
                break;
            case Libellule:
                exploser(true);
                break;
            default:
                retour = 1;
        }
        return retour;
    }

    @Override
    public void exploser(final boolean popDiamants) {
        SONS.jouerSonExplosion();
        for (int i = -1; i < 2; i++) {
            for (int j = 0; j <= 2; j++) {
                explosion(i, j, popDiamants);
            }
        }
    }

    @Override
    public int getNumeroPriorite() {
        return 3;
    }

    @Override
    public void bloquer() {
        setBloque(!placeLibreChute(getPositionX(), getPositionY() + 1));
    }

    private boolean placeLibreChute(final int positionX, final int positionY) {
        for (final Type type : deplacementsPossiblesChute) {
            if (checkEntite(positionX, positionY, type)) {
                return true;
            }
        }
        return false;
    }
}
