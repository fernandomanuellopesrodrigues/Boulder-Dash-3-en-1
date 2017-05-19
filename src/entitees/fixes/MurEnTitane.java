package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Entitees.MurEnTitane;

public class MurEnTitane extends Entitee {

    public MurEnTitane(int x, int y) {
        super(x, y);
        enumeration = MurEnTitane;
    }
}
