package a03a.e2;

public interface Logics {
    void hit(int x, int y);
    
    boolean isRook(int x, int y);

    boolean isPawn(int x, int y);

    boolean isSelected(int x, int y);

    boolean isOver();
}
