package entitees.tickables;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;

import static entitees.abstraites.Entitee.Type.Diamant;
import static entitees.abstraites.Entitee.Type.Explosion;
import static entitees.abstraites.Entitee.Type.Libellule;
import static entitees.abstraites.Entitee.Type.Luciole;
import static entitees.abstraites.Entitee.Type.MurMagique;
import static entitees.abstraites.Entitee.Type.Rockford;
import static entitees.tickables.Rockford.ramasserDiamant;
import static main.Constantes.BAS;
import static main.Partie.SONS;
import static main.Partie.checkEntite;

public final class Diamant extends Tickable {

    public Diamant(final int positionX, final int positionY) {
        super(positionX, positionY, Diamant, true);
        addDeplacementPossible(Rockford, MurMagique, Libellule, Luciole, Explosion);
    }

    @Override
    public Entitee nouvelle() {
        return new Diamant(getPositionX(), getPositionY());
    }

    @Override
    public int contactAutreEntitee(final Entitee entitee) {
        setDirection(BAS);
        int retour = 1;
        switch (entitee.getType()) {
            case Rockford:
                mourir();
                ramasserDiamant(this);
                retour = -1;
                break;
            case Libellule:
                exploser(true);
                retour = 0;
                break;
            case Luciole:
                exploser(false);
                retour = 0;
                break;
            case Explosion:
                mourir();
                retour = -1;
                break;
            case MurMagique:
                retour = 0;
                break;
            default:
        }
        return retour;
    }

    @Override
    public void tick() {
        rockfordEndessous();
        gererChute();
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

    private void rockfordEndessous() {
        if (checkEntite(getPositionX(), getPositionY() + 1, Rockford)) {
            setChute(true);
        }
    }
}
