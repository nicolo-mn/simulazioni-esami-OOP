package a01c.e2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics{

    private final Set<Pair<Integer, Integer>> selected = new HashSet<>();
    private Pair<Integer, Integer> lastHit;
    private Direction dir = Direction.ANY;


    @Override
    public HitType hit(int x, int y) {
        if (lastHit == null) {
            lastHit = new Pair<>(x,y);
            selected.add(lastHit);
            return HitType.VALID;
        }
        else if(isValid(x,y)) {
            if (isOriz(x,y)) {
                for (var index : range(y, lastHit.getY())) {
                    selected.add(new Pair<>(lastHit.getX(), index));
                }
                dir = Direction.VERT;
            }
            else {
                for (var index : range(x, lastHit.getX())) {
                    selected.add(new Pair<>(index, lastHit.getY()));
                }
                dir = Direction.ORIZ;
            }
            lastHit = new Pair<>(x,y);
            return HitType.VALID;
        }
        return HitType.WRONG;
        
    }

    private boolean isOriz(int x, int y) {
        return lastHit.getX() == x;
    }

    private boolean isVert(int x, int y) {
        return lastHit.getY() == y;
    }

    private boolean isValid(int x, int y) {
        switch (dir) {
            case ANY: return isOriz(x,y) || isVert(x,y);
            case ORIZ: return isOriz(x,y);
            case VERT: return isVert(x,y);
            default: return false;
        }
    }

    @Override
    public boolean isSelected(int x, int y) {
        return selected.contains(new Pair<>(x,y));
    }

    private Iterable<Integer> range(int x, int x2) {
		return x<=x2 ? ()->IntStream.rangeClosed(x,x2).iterator() : ()->IntStream.rangeClosed(x2,x).iterator();
	}

}
