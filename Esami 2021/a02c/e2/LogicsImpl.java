package a02c.e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicsImpl implements Logics{

    private enum Direction {
        RIGHT(1,0), LEFT(-1,0), DOWN(0,1);

        int x;
        int y;
        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Direction invert() {
            return Direction.values()[(this.ordinal() + 1) % 2];
        }
    }


    private final Set<Pair<Integer, Integer>> obstacles = new HashSet<>();
    private final int size;
    private final Random r = new Random();
    private Pair<Integer, Integer> pos;


    public LogicsImpl(int size) {
        this.size = size;
        this.pos = new Pair<>(r.nextInt(size), 0);
        while (obstacles.size() != 20) {
            obstacles.add(new Pair<>(r.nextInt(size), r.nextInt(1, size)));
        }
    }

    @Override
    public boolean isOver() {
        if (pos.getY() == this.size - 1) {
            return false;
        }
        for (var dir : Direction.values()) {
            if (isEligible(dir)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void next() {
        if (pos.getY() == this.size - 1) {
            this.pos = new Pair<>(r.nextInt(size), 0);
            return;
        }
        if(isEligible(Direction.DOWN)) {
            pos = new Pair<>(pos.getX(), pos.getY() + 1);
        }
        else {
            var dir = Direction.values()[r.nextInt(2)];
            dir = isEligible(dir) ? dir : dir.invert();
            pos = new Pair<>(pos.getX() + dir.x, pos.getY() + dir.y);
        }
        System.out.print(pos + ", ");
    }

    @Override
    public Pair<Integer, Integer> getPos() {
        return this.pos;
    }

    @Override
    public boolean isObstacle(Pair<Integer, Integer> toVerify) {
        return obstacles.contains(toVerify);
    }

    private boolean isEligible(Direction dir) {
        final Pair<Integer, Integer> toVerify = new Pair<>(this.pos.getX() + dir.x, this.pos.getY() + dir.y);
        return isValid(toVerify) && !obstacles.contains(toVerify);
    }

    private boolean isValid(Pair<Integer, Integer> toVerify) {
        return toVerify.getX() >= 0 && toVerify.getX() < size && toVerify.getY() >= 0 && toVerify.getY() < size;
    }
    
}
