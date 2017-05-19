package ia;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import entitees.abstraites.Entitee;
import entitees.tickables.Diamant;
import loader.Niveau;
import main.Partie;
import outils.Noeud;

public class IaDirective extends Ia {

    private Noeud[][]    graphe;
    private Stack<Noeud> chemin;
    private boolean bloquer = false;

    private Noeud diamantLePlusProche(Noeud depart) {
        LinkedList<Noeud> file = new LinkedList<Noeud>();
        file.add(depart);
        depart.setCout(0);
        depart.setEtat('a');
        while (!file.isEmpty()) {
            Noeud u = file.removeFirst();
            Set<Noeud> voisins = VoisinsNoeud(u);
            for (Noeud v : voisins) {
                if (v.getEtat() != 'a') {
                    file.add(v);
                    v.setCout(u.getCout() + 1);
                    v.setEtat('a');
                    if (v.getEntite().getClass().equals(Diamant.class)) {
                        return v;
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
    private Stack<Noeud> cheminPlusCourt(Noeud depart, Noeud objectif) {
        List<Noeud> closedList = new LinkedList<Noeud>();
        Queue<Noeud> openList = new PriorityQueue<Noeud>();
        openList.add(depart);
        while (!openList.isEmpty()) {
            Noeud u = openList.poll();
            if (u.getX() == objectif.getX() && u.getY() == objectif.getY()) {
                return reconstituerChemin(u);
            }
            Set<Noeud> voisins = VoisinsNoeud(u);
            for (Noeud v : voisins) {
                if (!((openList.contains(v) && v.getCout() < u.getCout() + 1) || closedList.contains(v))) {
                    v.setPere(u);
                    v.setCout(u.getCout() + 1);
                    v.setHeuristique(
                      v.getCout() + Math.abs(objectif.getX() - v.getX()) + Math.abs(objectif.getY() - v.getY()));
                    openList.add(v);
                }
            }
            closedList.add(u);
        }
        return null;
    }

    /**
     * Retourne les voisins du noeud passé en paramètre.
     *
     * @param u Le noeud dont on veut les voisins.
     *
     * @return Un set des voisins du noeud en paramètre.
     */
    private Set<Noeud> VoisinsNoeud(Noeud u) {
        Set<Noeud> voisins = new HashSet<Noeud>();
        if (positionValide(u.getX() - 1, u.getY())) {
            voisins.add(graphe[u.getX() - 1][u.getY()]);
        }
        if (positionValide(u.getX() + 1, u.getY())) {
            voisins.add(graphe[u.getX() + 1][u.getY()]);
        }
        if (positionValide(u.getX(), u.getY() - 1)) {
            voisins.add(graphe[u.getX()][u.getY() - 1]);
        }
        if (positionValide(u.getX(), u.getY() + 1)) {
            voisins.add(graphe[u.getX()][u.getY() + 1]);
        }
        return voisins;
    }

    /**
     * Vérifie que la case dont les coordonnées sont passées en paramètres est
     * dans la limite de carte.
     *
     * @param x La coordonnée en x.
     * @param y La coordonnée en y.
     *
     * @return Vrai si tel est le cas, faux sinon.
     */
    private boolean positionValide(int x, int y) {
        if (x >= 0 && x < graphe.length && y >= 0 && y < graphe[0].length) {
            if (graphe[x][y].isTraversable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reconstitue un chemin à partir du dernier noeud d'un chemin.
     *
     * @param u Le dernier noeud.
     *
     * @return Le chemin de noeuds.
     */
    private Stack<Noeud> reconstituerChemin(Noeud u) {
        Stack<Noeud> route = new Stack<Noeud>();
        while (u.getPere() != null) {
            route.push(u);
            u = u.getPere();
        }
        return route;
    }

    @Override
    public char tick(Entitee[][] map) {
        Niveau niveau = Partie.gererNiveau.getNiveau();
        graphe = new Noeud[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                graphe[i][j] = new Noeud(map[i][j]);
            }
        }
        bloquer = false;
        char direction;

        graphe = new Noeud[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                graphe[i][j] = new Noeud(map[i][j]);
            }
        }
        if (niveau.getSortie() != null && !niveau.getSortie().isOuvert()) {
            Noeud diamant = diamantLePlusProche(graphe[niveau.getRockford().getX()][niveau.getRockford().getY()]);
            if (diamant != null) {
                chemin = cheminPlusCourt(graphe[niveau.getRockford().getX()][niveau.getRockford().getY()], diamant);
                if (chemin == null || chemin.isEmpty()) { bloquer = true; }
            } else {
                bloquer = true;
            }
        } else {
            if (niveau.getSortie() != null) {
                chemin = cheminPlusCourt(graphe[niveau.getRockford().getX()][niveau.getRockford().getY()],
                                         graphe[niveau.getSortie().getX()][niveau.getSortie().getY()]);
            }
            if (chemin == null || chemin.isEmpty()) { bloquer = true; }
        }

        if (!bloquer) {
            if (niveau.getRockford().getX() > chemin.peek().getX()) { direction = 'g'; } else if (niveau.getRockford()
                                                                                                        .getX() < chemin
                                                                                                                    .peek()

                                                                                                                    .getX()) {
                direction = 'd';
            } else if (
                     niveau.getRockford()
                           .getY() >
                     chemin.peek()
                           .getY()) {
                direction = 'h';
            } else {
                direction = 'b';
            }
        } else {
            direction = Ia.directionRandom();
        }

        return direction;
    }

    @Override
    public void initialiserTry() {
        // TODO Auto-generated method stub

    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public boolean isBloquer() {
        return bloquer;
    }

    /**
     * Un setter.
     *
     * @param bloquer L'objet en question.
     */
    public void setBloquer(boolean bloquer) {
        this.bloquer = bloquer;
    }

}
