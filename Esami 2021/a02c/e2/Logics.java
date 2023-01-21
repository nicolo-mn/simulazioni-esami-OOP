package a02c.e2;

public interface Logics {
    boolean isOver();

    void next();

    Pair<Integer, Integer> getPos();

    boolean isObstacle(Pair<Integer, Integer> toVerify);
}
