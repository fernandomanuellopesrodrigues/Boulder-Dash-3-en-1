package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Entitees.Mur;

public class Mur extends Entitee {

    public Mur(int x, int y) {
        super(x, y);
        setDestructible(true);
        enumeration = Mur;
    }
}
