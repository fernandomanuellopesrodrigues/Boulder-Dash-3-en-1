package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Entitees.Vide;

public class Vide extends Entitee {

    public Vide(int x, int y) {
        super(x, y);
        setDestructible(true);
        enumeration = Vide;
    }
}
