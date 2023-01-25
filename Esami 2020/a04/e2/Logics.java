package a04.e2;

import java.util.Set;

public interface Logics {
    
    boolean isOver();

    boolean isKing();

    Pair<Integer, Integer> getMainPos();

    Set<Pair<Integer,Integer>> getPawns();

    void hit(int x, int y);
}
