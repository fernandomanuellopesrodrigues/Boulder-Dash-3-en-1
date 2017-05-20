package entitees.tickables;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;

import static entitees.abstraites.Entitee.Type.*;
import static entitees.abstraites.Entitee.Type.Diamant;
import static entitees.abstraites.Entitee.Type.Explosion;
import static entitees.abstraites.Entitee.Type.Libellule;
import static entitees.abstraites.Entitee.Type.Luciole;
import static entitees.abstraites.Entitee.Type.Pierre;
import static entitees.abstraites.Entitee.Type.Rockford;
import static main.Constantes.BAS;
import static main.Constantes.BOMB;
import static main.Constantes.CACHER;
import static main.Constantes.DROITE;
import static main.Constantes.GAUCHE;
import static main.Constantes.HAUT;
import static main.Constantes.NOMBRE_DE_BOMBES;
import static main.Partie.SONS;
import static main.Partie.checkEntite;
import static main.Partie.gererNiveau;
import static main.Partie.setEntiteParPosition;

public final class Rockford extends Tickable {

    private char ancienneDirection = DROITE;
    private int  nombreDeBombe     = NOMBRE_DE_BOMBES;
    private boolean bombeAPoser;

    public Rockford(final int positionX, final int positionY) {
        super(positionX, positionY, Rockford, true);
        setDirection(' ');
        addDeplacementPossible(Poussiere, Diamant, Pierre, Sortie, Amibe, Libellule, Luciole, Explosion);
    }

    @Override
    public Entitee nouvelle() {
        return new Rockford(getPositionX(), getPositionY());
    }

    public static void ramasserDiamant(final Diamant diamant) {
        if (gererNiveau.getNbDiamants() >= gererNiveau.getNiveau().getDiamondsRequired()) {
            gererNiveau.setScore(gererNiveau.getScore() + gererNiveau.getNiveau().getDiamondValueBonus());
        } else {
            gererNiveau.setScore(gererNiveau.getScore() + gererNiveau.getNiveau().getDiamondValue());
        }
        gererNiveau.incrementerNbDiamants(diamant);
    }

    @Override
    public int contactAutreEntitee(final Entitee entitee) {
        int retour = 0;
        final int positionX = entitee.getPositionX();
        final int positionY = entitee.getPositionY();
        switch (entitee.getType()) {
            case Pierre:
                if (positionX - getPositionX() > 0 && checkEntite(positionX + 1, positionY, Vide)) {
                    ((Pierre) entitee).setDirection(DROITE);
                } else if (positionX - getPositionX() < 0 && checkEntite(positionX - 1, positionY, Vide)) {
                    ((Pierre) entitee).setDirection(GAUCHE);
                }
                ((Pierre) entitee).seDeplacer();
                break;
            case Diamant:
                entitee.mourir();
                ramasserDiamant((Diamant) entitee);
                retour = 1;
                break;
            case Sortie:
                if (gererNiveau.getNbDiamants() >= gererNiveau.getNiveau().getDiamondsRequired()) {
                    gererNiveau.setFiniSuccess(true);
                    gererNiveau.setDemandeFin(true);
                    retour = 1;
                }
                break;
            case Amibe:
            case Explosion:
            case Libellule:
            case Luciole:
                mourir();
                retour = -1;
                break;
            default:
                retour = 1;
        }
        return retour;
    }

    @Override
    public void tick() {
        setDirection(gererNiveau.getToucheClavier());
        checkCamouflage();
        if (getType() == Rockford) {
            if (getDirection() == BOMB) {
                poserBombe();
            } else if (deplacement()) {
                checkBombe();
            }
        }
    }

    private void checkBombe() {
        if (bombeAPoser && !gererNiveau.isTourParTour()) {
            int x = 0;
            int y = 0;
            if (getDirection() == HAUT) {
                y = -1;
            } else if (getDirection() == DROITE) {
                x = 1;
            } else if (getDirection() == GAUCHE) {
                x = -1;
            } else if (getDirection() == BAS) {
                y = 1;
            }
            setEntiteParPosition(getPositionX() - x, getPositionY() - y,
                                 new Bombe(getPositionX() - x, getPositionY() - y));
            bombeAPoser = false;
            nombreDeBombe--;
        }
    }

    @Override
    public void gererChute() {
        if (isChute() && placeLibre(getPositionX(), getPositionY() + 1)) {
            setDirection(BAS);
            seDeplacer();
        } else if (checkEntite(getPositionX(), getPositionY() + 1, Vide)) {
            setChute(true);
            gererChute();
        } else {
            if (!placeLibre(getPositionX(), getPositionY() + 1)) {
                setChute(false);
            }
            glisser();
        }
    }

    @Override
    public int getNumeroPriorite() {
        return 5;
    }

    private boolean deplacement() {
        if (getDirection() != ' ') {
            SONS.jouerSonWalkEart();
            if (seDeplacer()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mourir() {
        super.mourir();
        SONS.jouerSonMortRockford();
        gererNiveau.setDemandeReset(true);
        return true;
    }

    private void checkCamouflage() {
        if (gererNiveau.getToucheClavier() == CACHER && getType() == Rockford) {
            seCamoufler();
        } else if (getType() == Pierre && gererNiveau.getToucheClavier() != CACHER) {
            seDecamoufler();
        }
    }

    private void seCamoufler() {
        setType(Pierre);
        getDeplacementsPossibles().remove(Poussiere);
        getDeplacementsPossibles().remove(Diamant);
        getDeplacementsPossibles().remove(Sortie);
        getDeplacementsPossibles().add(Luciole);
        getDeplacementsPossibles().add(Libellule);
        getDeplacementsPossibles().add(Amibe);
    }

    private void seDecamoufler() {
        setType(Rockford);
        getDeplacementsPossibles().add(Poussiere);
        getDeplacementsPossibles().add(Diamant);
        getDeplacementsPossibles().add(Sortie);
        getDeplacementsPossibles().remove(Luciole);
        getDeplacementsPossibles().remove(Libellule);
        getDeplacementsPossibles().remove(Amibe);
    }

    private void poserBombe() {
        if (nombreDeBombe > 0) {
            bombeAPoser = true;
        }
    }

    public boolean camouflageActif() {
        return getType() == Pierre;
    }

    public char getAncienneDirection() {
        return ancienneDirection;
    }

    public void setAncienneDirection(char ancienneDirection) {
        this.ancienneDirection = ancienneDirection;
    }

}
