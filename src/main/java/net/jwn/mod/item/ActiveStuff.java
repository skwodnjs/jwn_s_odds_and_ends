package net.jwn.mod.item;

import net.jwn.mod.util.Stat;
import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;

public class ActiveStuff extends Stuff {
    public ActiveStuff(Properties pProperties, int id, StuffRank rank, int t0, int w, Stat... stats) {
        // t0: 0레벨일 때 쿨타임, w: 레벨이 오를 때 줄어드는 쿨타임 (단위는 tick)
        super(pProperties, id, StuffType.ACTIVE, rank, stats);
        this.t0 = t0;
        this.weight = w;
    }
    public ActiveStuff(Properties pProperties, int id, StuffRank rank, boolean max_level, int t0, int w, Stat... stats) {
        // t0: 0레벨일 때 쿨타임, w: 레벨이 오를 때 줄어드는 쿨타임 (단위는 tick)
        super(pProperties, id, StuffType.ACTIVE, rank, max_level, stats);
        this.t0 = t0;
        this.weight = w;
    }
    public int t0;
    public int weight;
}
