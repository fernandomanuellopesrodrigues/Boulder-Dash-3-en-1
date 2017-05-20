package ia;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import entitees.abstraites.Entitee;
import entitees.abstraites.Entitee.Type;
import entitees.fixes.Sortie;
import loader.Niveau;
import outils.Noeud;

import static main.Constantes.AUTRE;
import static main.Constantes.BAS;
import static main.Constantes.DROITE;
import static main.Constantes.GAUCHE;
import static main.Constantes.HAUT;
import static main.Partie.gererNiveau;

public final class IaDirective extends Ia {

    private Noeud[][]    graphe;
    private Stack<Noeud> chemin;
    private boolean      bloquer;

    private Noeud diamantLePlusProche(final Noeud depart) {
        final LinkedList<Noeud> file = new LinkedList<>();
        file.add(depart);
        depart.setCout(0);
        depart.setEtat(AUTRE);
        while (!file.isEmpty()) {
            final Noeud first = file.removeFirst();
            final Set<Noeud> voisins = getVoisinsNoeud(first);
            for (final Noeud voisin : voisins) {
                if (voisin.getEtat() != AUTRE) {
                    file.add(voisin);
                    voisin.setCout(first.getCout() + 1);
                    voisin.setEtat(AUTRE);
                    if (voisin.getEntite().getType() == Type.Diamant) {
                        return voisin;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param depart
     * @param objectif
     * @return
     */
    private Stack<Noeud> cheminPlusCourt(final Noeud depart, final Noeud objectif) {
        final Queue<Noeud> openList = new PriorityQueue<>(10);
        openList.add(depart);
        while (!openList.isEmpty()) {
            final Noeud poll = openList.poll();
            if (poll.getPositionX() == objectif.getPositionX() && poll.getPositionY() == objectif.getPositionY()) {
                return reconstituerChemin(poll);
            }
            final Set<Noeud> voisins = getVoisinsNoeud(poll);
            final List<Noeud> closedList = new LinkedList<>();
            for (final Noeud noeud : voisins) {
                if (!(openList.contains(noeud) && noeud.getCout() < poll.getCout() + 1 || closedList.contains(noeud))) {
                    noeud.setPere(poll);
                    noeud.setCout(poll.getCout() + 1);
                    noeud.setHeuristique(
                      noeud.getCout() + Math.abs(objectif.getPositionX() - noeud.getPositionX()) +
                      Math.abs(objectif.getPositionY() - noeud.getPositionY()));
                    openList.add(noeud);
                }
            }
            closedList.add(poll);
        }
        return null;
    }

    /**
     * Retourne les voisins du noeud pass\u00E9 en param\u00E8tre.
     *
     * @param noeud Le noeud dont on veut les voisins.
     *
     * @return Un set des voisins du noeud en param\u00E8tre.
     */
    private Set<Noeud> getVoisinsNoeud(final Noeud noeud) {
        final Set<Noeud> voisins = new HashSet<>(10);
        if (positionValide(noeud.getPositionX() - 1, noeud.getPositionY())) {
            voisins.add(graphe[noeud.getPositionX() - 1][noeud.getPositionY()]);
        }
        if (positionValide(noeud.getPositionX() + 1, noeud.getPositionY())) {
            voisins.add(graphe[noeud.getPositionX() + 1][noeud.getPositionY()]);
        }
        if (positionValide(noeud.getPositionX(), noeud.getPositionY() - 1)) {
            voisins.add(graphe[noeud.getPositionX()][noeud.getPositionY() - 1]);
        }
        if (positionValide(noeud.getPositionX(), noeud.getPositionY() + 1)) {
            voisins.add(graphe[noeud.getPositionX()][noeud.getPositionY() + 1]);
        }
        return voisins;
    }

    /**
     * V\u00E9rifie que la case dont les coordonn\u00E9es sont pass\u00E9es en param\u00E8tres est
     * dans la limite de carte.
     *
     * @param coordX La coordonn\u00E9e en coordX.
     * @param coordY La coordonn\u00E9e en coordY.
     *
     * @return Vrai si tel est le cas, faux sinon.
     */
    private boolean positionValide(final int coordX, final int coordY) {
        if (coordX >= 0 && coordX < graphe.length && coordY >= 0 && coordY < graphe[0].length) {
            if (graphe[coordX][coordY].isTraversable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reconstitue un chemin \u00E0 partir du dernier noeud d'un chemin.
     *
     * @param noeud Le dernier noeud.
     *
     * @return Le chemin de noeuds.
     */
    private Stack<Noeud> reconstituerChemin(Noeud noeud) {
        final Stack<Noeud> route = new Stack<>();
        while (noeud.getPere() != null) {
            route.push(noeud);
            noeud = noeud.getPere();
        }
        return route;
    }

    @Override
    public char tick(final Entitee[][] map) {
        final Niveau niveau = gererNiveau.getNiveau();
        this.graphe = new Noeud[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                graphe[i][j] = new Noeud(map[i][j]);
            }
        }
        bloquer = false;
        graphe = new Noeud[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                graphe[i][j] = new Noeud(map[i][j]);
            }
        }
        final int rockPX = niveau.getRockford().getPositionX();
        final int rockPY = niveau.getRockford().getPositionY();
        if (niveau.getSortie() != null && !Sortie.isOuvert()) {
            final Noeud diamant = diamantLePlusProche(graphe[rockPX][rockPY]);
            if (diamant != null) {
                chemin = cheminPlusCourt(graphe[rockPX][rockPY], diamant);
                if (chemin == null || chemin.isEmpty()) {
                    bloquer = true;
                }
            } else {
                bloquer = true;
            }
        } else {
            if (niveau.getSortie() != null) {
                final int sortiePX = niveau.getSortie().getPositionX();
                final int sortiePY = niveau.getSortie().getPositionY();
                chemin = cheminPlusCourt(graphe[rockPX][rockPY], graphe[sortiePX][sortiePY]);
            }
            if (chemin == null || chemin.isEmpty()) {
                bloquer = true;
            }
        }
        if (bloquer) {
            return Ia.directionRandom();
        }
        final int peekPX = chemin.peek().getPositionX();
        final int peekPY = chemin.peek().getPositionY();
        final char direction;
        if (rockPX > peekPX) {
            direction = GAUCHE;
        } else if (rockPX < peekPX) {
            direction = DROITE;
        } else {
            direction = rockPY > peekPY ? HAUT : BAS;
        }
        return direction;
    }

    @Override
    public void initialiserTry() {
        // TODO Auto-generated method stub

    }

}
