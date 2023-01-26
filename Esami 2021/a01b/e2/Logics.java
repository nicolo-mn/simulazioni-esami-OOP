package a01b.e2;

public interface Logics {
    
    HitType hit(int x, int y);

    boolean isSelected(int x, int y);

    enum HitType{
        FIRST, SECOND, THIRD, WRONG
    }
}
