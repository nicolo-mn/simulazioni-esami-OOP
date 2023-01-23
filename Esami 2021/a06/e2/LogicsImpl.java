package a06.e2;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class LogicsImpl implements Logics{

    private List<Pair<Integer, Integer>> list = new LinkedList<>();
    private Iterator<Pair<Integer, Integer>> it;
    private final Set<Pair<Integer, Integer>> asterisks = new HashSet<>();
    private final int size;

    public LogicsImpl(int size) {
        this.size = size;
    }


    @Override
    public HitType hitAdvance() {
        if (this.list.isEmpty()) {
            return HitType.WRONG;
        }
        else if (this.asterisks.isEmpty()) {
            this.it = this.list.stream().flatMap(s -> Stream.of(getUp(s), getDown(s))).iterator();
            this.asterisks.add(it.next());
            return HitType.FIRSTCORRECT;
        }
        else if(it.hasNext()) {
            this.asterisks.add(it.next());
            return HitType.CORRECT; 
        }
        else {
            return HitType.CLOSE;
        }
    }

    @Override
    public boolean isAsterisk(int x, int y) {
        return this.asterisks.contains(new Pair<>(x,y));
    }

    @Override
    public void hitCell(int x, int y) {
        if (this.isValid(x,y) && !this.list.contains(new Pair<>(x,y)) && this.list.isEmpty() ? true : !this.isAdjacent(new Pair<>(x,y), this.list.get(list.size() - 1))) {
            this.list.add(this.list.size(), new Pair<>(x,y));
        }
    }

    @Override
    public boolean isPoint(int x, int y) {
        return this.list.contains(new Pair<>(x,y));
    }
    
    private boolean isValid(int x, int y) {
        return x > 0 && x < size - 1 && y > 0 && y < size - 1;
    }
    
    private boolean isAdjacent(Pair<Integer, Integer> one, Pair<Integer, Integer> other) {
        return Math.abs(one.getX()-other.getX()) + Math.abs(one.getY() - other.getY()) == 1;
    }

    private Pair<Integer, Integer> getUp(Pair<Integer, Integer> pos) {
        return new Pair<>(pos.getX(), pos.getY()+1);
    }

    private Pair<Integer, Integer> getDown(Pair<Integer, Integer> pos) {
        return new Pair<>(pos.getX(), pos.getY()-1);
    }
}
