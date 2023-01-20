package a02b.e2;

import java.util.Optional;

public interface Logics {
    Optional<Pair<Integer, Integer>> next();
    
    boolean isRight(Pair<Integer, Integer> pos);

    boolean isLeft(Pair<Integer, Integer> pos);
}
