package a01b.e2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics{

    private final Set<Pair<Integer,Integer>> selected = new HashSet<>();
    private Pair<Integer, Integer> first = new Pair<>(-1, -1);
    private Pair<Integer, Integer> second = new Pair<>(-1, -1);
    private Pair<Integer, Integer> third = new Pair<>(-1, -1);
    private HitType res = HitType.FIRST;

    private Iterable<Integer> range(int x, int x2) {
		return x<=x2 ? ()->IntStream.rangeClosed(x,x2).iterator() : ()->IntStream.rangeClosed(x2,x).iterator();
	}
    
    private boolean isValid(Pair<Integer, Integer> hitPos) {
        if (res == HitType.SECOND && hitPos.getY() == first.getY()) {
            return true;
        }
        else if (res == HitType.THIRD && hitPos.getX() == first.getX()) {
            return true;
        }
        return false;
    }

    @Override
    public void hit(int x, int y) {
        final Pair<Integer, Integer> hitPos = new Pair<>(x,y);
        if (res == HitType.FIRST) {
            selected.add(hitPos);
            first = hitPos;
            res = HitType.SECOND;
        }
        else if (res == HitType.SECOND && isValid(hitPos)) {
            for (var index : range(first.getX(), hitPos.getX())) {
                selected.add(new Pair<>(index, hitPos.getY()));
            }
            second = hitPos;
            res = HitType.THIRD;
        }
        else if (res == HitType.THIRD && isValid(hitPos)) {
            for (var index : range(first.getY(), hitPos.getY())) {
                selected.add(new Pair<>(hitPos.getX(), index));
            }
            third = hitPos;
            res = HitType.OVER;
        }
    }

    @Override
    public boolean isSelected(int x, int y) {
        if (selected.contains(new Pair<>(x,y))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isOver() {
        return res == HitType.OVER;
    }

    @Override
    public boolean isFirst(int x, int y) {
        return first.equals(new Pair<>(x,y));
    }

    @Override
    public boolean isSecond(int x, int y) {
        return second.equals(new Pair<>(x,y));
    }

    @Override
    public boolean isThird(int x, int y) {
        return third.equals(new Pair<>(x,y));
    } 
    
}
