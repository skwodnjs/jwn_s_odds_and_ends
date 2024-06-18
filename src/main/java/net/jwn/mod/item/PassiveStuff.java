package net.jwn.mod.item;

import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;

public class PassiveStuff extends Stuff {
    public PassiveStuff(Properties pProperties, int id, StuffRank rank) {
        super(pProperties, id, StuffType.PASSIVE, rank);
    }
}
