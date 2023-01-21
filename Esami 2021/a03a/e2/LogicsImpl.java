package a03a.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicsImpl implements Logics {

    private final Set<Pair<Integer, Integer>> points = new HashSet<>();
    private final int size;
    private Pair<Integer, Integer> lastHit;
    private Pair<Integer, Integer> lastHitOpposite;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public void hit(Pair<Integer, Integer> hitPos) {
        if (hitPos.getX() < this.size / 2 && hitPos.getY() < this.size / 2 && this.greaterThanPrev(hitPos)) {
            this.lastHit = hitPos;
            this.lastHitOpposite = new Pair<>(this.size - 1 - hitPos.getX(), this.size - 1 - hitPos.getY());
        }
        for (int i = lastHit.getX(); i <= lastHitOpposite.getX(); i++) {
            this.points.add(new Pair<>(i, lastHit.getY()));
            this.points.add(new Pair<>(i, lastHitOpposite.getY()));
        }
        for (int i = lastHit.getY() + 1; i < lastHitOpposite.getY(); i++) {
            this.points.add(new Pair<>(lastHit.getX(), i));
            this.points.add(new Pair<>(lastHitOpposite.getX(), i));
        }
    }

    @Override
    public boolean isPoint(Pair<Integer, Integer> toVerify) {
        return this.points.contains(toVerify);
    }

    @Override
    public boolean isOver() {
        return lastHit.getX() == this.size / 2 - 1 || lastHit.getY() == size / 2 - 1;  
    }
    
    private boolean greaterThanPrev(Pair<Integer,Integer> hitPos) {
        if (lastHit != null) {
            return hitPos.getX() > lastHit.getX() && hitPos.getY() > lastHit.getY();
        }
        return true;
    }

}
