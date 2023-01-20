package a01c.e2;

public interface Logics {
    enum HitType {
        VALID, WRONG
    }

    enum Direction {
        ANY, VERT, ORIZ
    }
    HitType hit(int x, int y);

    boolean isSelected(int x, int y);
}
