package a01a.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicsImpl implements Logics{

    private final Set<Pair<Integer, Integer>> selected = new HashSet<>();
    private final List<Pair<Integer,Integer>> lastMoves = new ArrayList<>();

    @Override
    public void hit(int x, int y) {
        if (!this.selected.remove(new Pair<>(x,y))) {
            this.selected.add(new Pair<>(x,y));
        }
        this.lastMoves.add(new Pair<>(x,y));
        if (this.lastMoves.size() > 3) {
            this.lastMoves.remove(0);
        }
    }

    @Override
    public boolean isSelected(int x, int y) {
        return this.selected.contains(new Pair<>(x,y));
    }

    @Override
    public boolean isOver() {
        return this.lastMoves.size() == 3 && this.lastMoves.stream().allMatch(e -> this.selected.contains(e)) 
        && this.areDiagonalDistant(1, this.lastMoves.get(0), this.lastMoves.get(1))
        && this.areDiagonalDistant(1, this.lastMoves.get(1), this.lastMoves.get(2))
        && this.areDiagonalDistant(2, this.lastMoves.get(0), this.lastMoves.get(2));
    }

    private boolean areDiagonalDistant(int n, Pair<Integer,Integer> one, Pair<Integer,Integer> other) {
        return Math.abs(one.getX() - other.getX()) == n && Math.abs(one.getY() - other.getY()) == n;
    }
    
}
