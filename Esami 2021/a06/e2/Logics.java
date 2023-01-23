package a06.e2;

public interface Logics {
    
    HitType hitAdvance();

    boolean isAsterisk(int x, int y);

    void hitCell(int x, int y);

    boolean isPoint(int x, int y);

    enum HitType {
        WRONG, CORRECT, FIRSTCORRECT, CLOSE
    }
}
