package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import entitees.tickables.Diamant;
import ia.Ia;
import ia.IaEvolue;
import loader.Niveau;
import outils.Paire;
import outils.Score;

public class GererNiveau {

    private static long   compteurReset;
    private boolean tourParTour = true, demandeReset, demandeFin;
    private       int score;
    private       int nbDiamants;
    private       int tempsRestant;
    private final int tempsTotal;
    private       int compteurTicks;
    private final long tempsAuDebut = System.currentTimeMillis();
    private Niveau  niveau;
    private boolean finiSuccess;
    private char    toucheClavier;
    private       String                     trajet             = "";
    private final List<Amibe>                listeAmibes        = new ArrayList<Amibe>();
    private final List<Tickable>             listeTickable      = new ArrayList<Tickable>();
    private final List<Amibe>                listeAmibesAjout   = new ArrayList<Amibe>();
    private final List<Tickable>             listeTickableAjout = new ArrayList<Tickable>();
    private final List<Paire<Integer, Long>> listeDiamants      = new ArrayList<Paire<Integer, Long>>();
    public GererNiveau(Niveau niveau) {
        this.niveau = niveau;
        if (niveau.getCaveDelay() >= 1 && Coeur.tempsReel) {
            tourParTour = false;
        }
        tempsRestant = niveau.getCave_time();
        tempsTotal = tempsRestant;
        for (int i = 0; i < niveau.getMap().length; i++) {
            for (int j = 0; j < niveau.getMap()[i].length; j++) {
                if (niveau.getMap()[i][j] instanceof Tickable) {
                    listeTickable.add((Tickable) niveau.getMap()[i][j]);
                }
            }
        }
        for (int i = 0; i < niveau.getMap().length; i++) {
            for (int j = 0; j < niveau.getMap()[i].length; j++) {
                if (niveau.getMap()[i][j] instanceof Amibe) {
                    listeAmibes.add((Amibe) niveau.getMap()[i][j]);
                }
            }
        }
        Collections.sort(listeTickable);
        Collections.shuffle(listeAmibes);
    }

    public List<Amibe> getListeAmibesAjout() {
        return listeAmibesAjout;
    }

    public boolean tickIa(Ia ia) {
        Score s = null;
        while (!finiSuccess) {
            compteurTicks++;
            toucheClavier = ia.direction(niveau.getMap());
            trajet += toucheClavier;

            if (compteurTicks >= niveau.getCave_time() * niveau.getCaveDelay()) {
                compteurReset++;
                if (ia.getClass().equals(IaEvolue.class)) {
                    s = ((IaEvolue) ia).ajouterScore();
                }
                Partie.resetNiveau();
                break;
            }
            tickInterne();
        }
        if (!finiSuccess) {
            if (ia.getClass().equals(IaEvolue.class)) {
                ((IaEvolue) ia).ajouterScore(s);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean tickIaDirevol(Ia ia) {
        if (!finiSuccess) {
            compteurTicks++;
            toucheClavier = ia.direction(niveau.getMap());
            trajet += toucheClavier;

            if (compteurTicks >= niveau.getCave_time() * niveau.getCaveDelay()) {
                compteurReset++;
                if (ia.getClass().equals(IaEvolue.class)) {
                    ((IaEvolue) ia).ajouterScore();
                }
                Partie.resetNiveau();
            }
            tickInterne();
        }
        if (!finiSuccess) {
            if (ia.getClass().equals(IaEvolue.class)) {
                ((IaEvolue) ia).ajouterScore();
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean tickIaController(Ia ia, char c) {
        if (!finiSuccess) {
            compteurTicks++;
            toucheClavier = c;
            trajet += c;

            if (compteurTicks >= niveau.getCave_time() * niveau.getCaveDelay()) {
                compteurReset++;
                if (ia.getClass().equals(IaEvolue.class)) {
                    ((IaEvolue) ia).ajouterScore();
                }
                Partie.resetNiveau();
            }
            tickInterne();
        }
        if (!finiSuccess) {
            if (ia.getClass().equals(IaEvolue.class)) {
                ((IaEvolue) ia).ajouterScore();
            }
            return false;
        } else {
            return true;
        }
    }

    public void tick() {
        toucheClavier = Coeur.CONTROLEUR.getDirection();
        trajet += toucheClavier;
        tickInterne();
    }

    public void tickConsole(char touche) {
        toucheClavier = touche;
        trajet += toucheClavier;
        tickInterne();
    }

    public boolean tickLecture(char touche) {
        toucheClavier = touche;
        return tickInterne();
    }

    public boolean tickInterne() {
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
        Coeur.CONTROLEUR.tick();
        return false;
    }

    public void gererLesTickables() {
        for (Tickable t : listeTickable) {
            if (!t.isMort()) { t.tick(); }
        }
    }

    public void gererLesAmibes() {
        // son
        if (listeAmibes.isEmpty()) {
            Partie.sons.stopSon1();
        }
        if (!getListeAmibes().isEmpty()) {
            Partie.sons.jouerSon1("amoeba.wav", 965);
        }
        // fin son
        if (listeAmibes.size() > 0 && niveau.getAmoeba_time() != -1 && compteurTicks % niveau.getAmoeba_time() == 0) {
            Collections.shuffle(listeAmibes);
            for (Amibe amibe : listeAmibes) {
                if (amibe.sePropager()) {
                    return;
                }
            }
            listeAmibes.get(0).transformerTousLesAmibesEnDiamant();
        }
    }

    public void gererTemps() {
        long temps = System.currentTimeMillis();
        if (temps - tempsAuDebut > tempsTotal * 1000) {
            demandeReset = true;
            niveau.getRockford().mourir();
        }
        tempsRestant = (int) (tempsTotal - (temps - tempsAuDebut) / 1000);
    }

    public void ajouterAmibe(Amibe e) {
        boolean ok = true;
        for (Amibe a : listeAmibes) {
            if (a.getId() == e.getId()) {
                ok = false;
                break;
            }
        }
        if (ok) { listeAmibesAjout.add(e); }
    }

    public void ajouterTickable(Tickable e) {
        boolean ok = true;
        for (Tickable a : listeTickable) {
            if (a.getId() == e.getId()) {
                ok = false;
                break;
            }
        }
        if (ok) { listeTickableAjout.add(e); }
    }

    public void ajouterAll() {
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

    public void incrementerNbDiamants(Diamant d) {
        d.getSons().jouerSon3("explosionDiamant.wav", 1);
        listeDiamants.add(new Paire<Integer, Long>(compteurTicks, d.getId()));
        nbDiamants++;
    }

    public void finNiveau() {
        Partie.finNiveau();
    }

    public boolean isTourParTour() {
        return tourParTour;
    }

    public void setTourParTour(boolean tourParTour) {
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

    public void setFiniSuccess(boolean finiSuccess) {
        this.finiSuccess = finiSuccess;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
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

    public void setDemandeReset(boolean demandeReset) {
        this.demandeReset = demandeReset;
    }

    public boolean isDemandeFin() {
        return demandeFin;
    }

    public void setDemandeFin(boolean demandeFin) {
        this.demandeFin = demandeFin;
    }

    public String getTrajet() {
        return trajet;
    }

    public void setTrajet(String trajet) {
        this.trajet = trajet;
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
        trajet = "";
    }

    public List<Paire<Integer, Long>> getListeDiamants() {
        return listeDiamants;
    }

}
