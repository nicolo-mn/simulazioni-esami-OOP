package a02a.e2;

public interface Logics {

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    Pair<Integer,Integer> hit();

    boolean isOver();

}
