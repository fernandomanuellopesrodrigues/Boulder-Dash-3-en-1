package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Entitee.Type;
import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import entitees.tickables.Diamant;
import ia.Ia;
import ia.IaEvolue;
import loader.Niveau;
import outils.Paire;
import outils.Score;

import static main.Coeur.CONTROLEUR;
import static main.Partie.SONS;

public class GererNiveau {

    private static long compteurReset;
    private boolean tourParTour = true;
    private       boolean demandeReset;
    private       boolean demandeFin;
    private       int     score;
    private       int     nbDiamants;
    private       int     tempsRestant;
    private final int     tempsTotal;
    private       int     compteurTicks;
    private final long tempsAuDebut = System.currentTimeMillis();
    private Niveau  niveau;
    private boolean finiSuccess;
    private char    toucheClavier;
    private       StringBuilder              trajet             = new StringBuilder();
    private final List<Amibe>                listeAmibes        = new ArrayList<>(10);
    private final List<Tickable>             listeTickable      = new ArrayList<>(10);
    private final List<Amibe>                listeAmibesAjout   = new ArrayList<>(10);
    private final List<Tickable>             listeTickableAjout = new ArrayList<>(10);
    private final List<Paire<Integer, Long>> listeDiamants      = new ArrayList<>(10);

    public GererNiveau(final Niveau niveau) {
        this.niveau = niveau;
        if (niveau.getCaveDelay() >= 1 && Coeur.tempsReel) {
            tourParTour = false;
        }
        tempsRestant = niveau.getCaveTime();
        tempsTotal = tempsRestant;
        buildTickables(niveau);
        buildAmibes(niveau);
        Collections.sort(listeTickable);
        Collections.shuffle(listeAmibes);
    }

    private void buildAmibes(final Niveau niveau) {
        for (int i = 0; i < niveau.getMap().length; i++) {
            for (int j = 0; j < niveau.getMap()[i].length; j++) {
                if (niveau.getMap()[i][j].getType() == Type.Amibe) {
                    listeAmibes.add((Amibe) niveau.getMap()[i][j]);
                }
            }
        }
    }

    private void buildTickables(final Niveau niveau) {
        for (int i = 0; i < niveau.getMap().length; i++) {
            for (int j = 0; j < niveau.getMap()[i].length; j++) {
                if (niveau.getMap()[i][j] instanceof Tickable) {
                    listeTickable.add((Tickable) niveau.getMap()[i][j]);
                }
            }
        }
    }

    public List<Amibe> getListeAmibesAjout() {
        return listeAmibesAjout;
    }

    public boolean tickIa(final Ia ia) {
        Score sc = null;
        while (!finiSuccess) {
            compteurTicks++;
            toucheClavier = ia.direction(niveau.getMap());
            trajet.append(toucheClavier);
            if (compteurTicks >= niveau.getCaveTime() * niveau.getCaveDelay()) {
                compteurReset++;
                if (ia.getClass().equals(IaEvolue.class)) {
                    sc = ((IaEvolue) ia).ajouterScore();
                }
                Partie.resetNiveau();
                break;
            }
            tickInterne();
        }
        if (finiSuccess) {
            return true;
        }
        if (ia.getClass().equals(IaEvolue.class)) {
            ((IaEvolue) ia).ajouterScore(sc);
        }
        return false;

    }

    public boolean tickIaDirevol(final Ia ia) {
        if (!finiSuccess) {
            compteurTicks++;
            toucheClavier = ia.direction(niveau.getMap());
            trajet.append(toucheClavier);
            if (compteurTicks >= niveau.getCaveTime() * niveau.getCaveDelay()) {
                compteurReset++;
                if (ia.getClass().equals(IaEvolue.class)) {
                    ((IaEvolue) ia).ajouterScore();
                }
                Partie.resetNiveau();
            }
            tickInterne();
        }
        if (finiSuccess) {
            return true;
        }
        if (ia.getClass().equals(IaEvolue.class)) {
            ((IaEvolue) ia).ajouterScore();
        }
        return false;
    }

    public boolean tickIaController(final Ia ia, final char touche) {
        if (!finiSuccess) {
            compteurTicks++;
            toucheClavier = touche;
            trajet.append(touche);
            if (compteurTicks >= niveau.getCaveTime() * niveau.getCaveDelay()) {
                compteurReset++;
                if (ia.getClass().equals(IaEvolue.class)) {
                    ((IaEvolue) ia).ajouterScore();
                }
                Partie.resetNiveau();
            }
            tickInterne();
        }
        if (finiSuccess) {
            return true;
        }
        if (ia.getClass().equals(IaEvolue.class)) {
            ((IaEvolue) ia).ajouterScore();
        }
        return false;
    }

    public void tick() {
        toucheClavier = CONTROLEUR.getDirection();
        trajet.append(toucheClavier);
        tickInterne();
    }

    public void tickConsole(final char touche) {
        toucheClavier = touche;
        trajet.append(touche);
        tickInterne();
    }

    public boolean tickLecture(final char touche) {
        toucheClavier = touche;
        return tickInterne();
    }

    private boolean tickInterne() {
        gererLesTickables();
        gererLesAmibes();
        ajouterAll();
        compteurTicks++;
        if (demandeReset) {
            Partie.resetNiveau();
            return true;
        }
        if (demandeFin) {
            Partie.finNiveau();
            return true;

        }
        gererTemps();
        CONTROLEUR.tick();
        return false;
    }

    private void gererLesTickables() {
        for (final Tickable tickable : listeTickable) {
            if (!tickable.isMort()) {
                tickable.tick();
            }
        }
    }

    private void gererLesAmibes() {
        // son
        if (listeAmibes.isEmpty()) {
            SONS.stopSon1();
        }
        if (!listeAmibes.isEmpty()) {
            SONS.jouerSonAmoeba();
        }
        // fin son
        if (!listeAmibes.isEmpty() && niveau.getAmoebaTime() != -1 && compteurTicks % niveau.getAmoebaTime() == 0) {
            Collections.shuffle(listeAmibes);
            for (final Amibe amibe : listeAmibes) {
                if (amibe.sePropager()) {
                    return;
                }
            }
            Amibe.transformerTousLesAmibesEnDiamant();
        }
    }

    public void gererTemps() {
        final long temps = System.currentTimeMillis();
        if (temps - tempsAuDebut > tempsTotal * 1000) {
            demandeReset = true;
            niveau.getRockford().mourir();
        }
        tempsRestant = (int) (tempsTotal - (temps - tempsAuDebut) / 1000);
    }

    public void ajouterAmibe(final Amibe amibe) {
        boolean ok = true;
        for (final Amibe unAmibe : listeAmibes) {
            if (unAmibe.getIdentitifant() == amibe.getIdentitifant()) {
                ok = false;
                break;
            }
        }
        if (ok) {
            listeAmibesAjout.add(amibe);
        }
    }

    public void ajouterTickable(final Tickable e) {
        boolean ok = true;
        for (final Tickable tickable : listeTickable) {
            if (tickable.getIdentitifant() == e.getIdentitifant()) {
                ok = false;
                break;
            }
        }
        if (ok) {
            listeTickableAjout.add(e);
        }
    }

    private void ajouterAll() {
        listeAmibes.addAll(listeAmibesAjout);
        listeAmibesAjout.clear();
        for (int i = 0; i < listeAmibes.size(); i++) {
            if (listeAmibes.get(i).isMort()) {
                listeAmibes.remove(i);
                i--;
            }
        }
        Collections.shuffle(listeAmibes);
        listeTickable.addAll(listeTickableAjout);
        listeTickableAjout.clear();
        for (int i = 0; i < listeTickable.size(); i++) {
            if (listeTickable.get(i).isMort()) {
                listeTickable.remove(i);
                i--;
            }
        }
        Collections.sort(listeTickable);
    }

    public void stop() {
        listeTickable.clear();
        listeTickableAjout.clear();
        listeAmibes.clear();
        listeAmibesAjout.clear();
        niveau = null;
    }

    public void incrementerNbDiamants(final Diamant diamant) {
        SONS.jouerSonExplosionDiamant();
        listeDiamants.add(new Paire<>(compteurTicks, diamant.getIdentitifant()));
        nbDiamants++;
    }

    public void finNiveau() {
        Partie.finNiveau();
    }

    public boolean isTourParTour() {
        return tourParTour;
    }

    public void setTourParTour(final boolean tourParTour) {
        this.tourParTour = tourParTour;
    }

    public int getTicks() {
        return niveau.getCaveDelay();
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public boolean isFiniSuccess() {
        return finiSuccess;
    }

    public void setFiniSuccess(final boolean finiSuccess) {
        this.finiSuccess = finiSuccess;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public int getNbDiamants() {
        return nbDiamants;
    }

    public int getTempsRestant() {
        return tempsRestant;
    }

    public char getToucheClavier() {
        return toucheClavier;
    }

    public List<Amibe> getListeAmibes() {
        return listeAmibes;
    }

    public List<Tickable> getListeTickable() {
        return listeTickable;
    }

    public boolean isDemandeReset() {
        return demandeReset;
    }

    public void setDemandeReset(final boolean demandeReset) {
        this.demandeReset = demandeReset;
    }

    public boolean isDemandeFin() {
        return demandeFin;
    }

    public void setDemandeFin(final boolean demandeFin) {
        this.demandeFin = demandeFin;
    }

    public String getTrajet() {
        return trajet.toString();
    }

    public void setTrajet(final String trajet) {
        this.trajet = new StringBuilder(trajet);
    }

    public int getCompteurTicks() {
        return compteurTicks;
    }

    public void incrCompteurReset() {
        compteurReset++;
    }

    public void resetCompteurTicks() {
        compteurTicks = 0;
    }

    public void resetTrajet() {
        trajet =  new StringBuilder(10);
    }

    public List<Paire<Integer, Long>> getListeDiamants() {
        return listeDiamants;
    }

}
