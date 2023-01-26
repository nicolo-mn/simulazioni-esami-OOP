package a01b.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicsImpl implements Logics{

    private enum Direction {
        UPRIGHT(1,-1), UPLEFT(-1,-1), DOWNLEFT(-1,1), DOWNRIGHT(1,1);
        int x;
        int y;
        Direction(int x, int y) {
            this.x=x;
            this.y=y;
        }
    }

    private final int size;
    private final Set<Pair<Integer,Integer>> selected = new HashSet<>();
    private Pair<Integer,Integer> hitLog;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public void hit(int x, int y) {
        int nRemovedValues = 0;
        int nAddedValues = 0;
        for (var dir : Direction.values()) {
            final var pos = new Pair<>(x+dir.x,y+dir.y);
            if (this.isValid(pos) && this.selected.contains(pos)) {
                this.selected.remove(pos);
                nRemovedValues += 1;
            } else if (this.isValid(pos) && !this.selected.contains(pos)) {
                this.selected.add(pos);
                nAddedValues += 1;
            }
        }
        this.hitLog = new Pair<>(nAddedValues, nRemovedValues);
    }

    @Override
    public boolean isOver() {
        return this.hitLog.getX() == 1 && this.hitLog.getY() == 3;
    }

    @Override
    public boolean isSelected(int x, int y) {
        return this.selected.contains(new Pair<>(x,y));
    }

    private boolean isValid(Pair<Integer,Integer> pos) {
        return pos.getX() >= 0 && pos.getX() < this.size && pos.getY() >= 0 && pos.getY() < this.size;
    }
    
}
