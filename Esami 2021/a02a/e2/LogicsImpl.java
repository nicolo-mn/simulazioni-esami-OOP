package a02a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LogicsImpl implements Logics{

    private final int NUMDIR = 4;

    private final int size;
    private final Random r = new Random();
    private final List<Pair<Integer, Integer>> posList = new ArrayList<>();
    Optional<Pair<Integer, Integer>> lastPos = Optional.empty();
    private Direction dir = Direction.UP;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public Pair<Integer, Integer> hit() {
        return lastPos.get();
    }

    @Override
    public boolean isOver() {
        if (lastPos.isEmpty()) {
            lastPos = Optional.of(new Pair<>(r.nextInt(size), r.nextInt(size)));
            posList.add(lastPos.get());
            return false;
        }
        Pair<Integer,Integer> nextPos;
        for (int i = 0; i < NUMDIR; i++) {
            switch (dir) {
                case UP: nextPos = new Pair<>(lastPos.get().getX(), lastPos.get().getY() - 1); break;
                case RIGHT: nextPos = new Pair<>(lastPos.get().getX() + 1, lastPos.get().getY()); break;
                case DOWN: nextPos = new Pair<>(lastPos.get().getX(), lastPos.get().getY() + 1); break;
                default: nextPos = new Pair<>(lastPos.get().getX() - 1, lastPos.get().getY()); break;
            }
            if (isValid(nextPos) && isFree(nextPos)) {
                lastPos = Optional.of(nextPos);
                posList.add(nextPos);
                return false;
            }
            switch (dir) {
                case UP: dir = Direction.RIGHT; break;
                case RIGHT: dir = Direction.DOWN; break;
                case DOWN: dir = Direction.LEFT; break;
                case LEFT: dir = Direction.UP; break;
            }
        }
        return true;
    }

    private boolean isValid(Pair<Integer, Integer> pos) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < this.size && pos.getY() < this.size;
    }

    private boolean isFree(Pair<Integer, Integer> pos) {
        return !this.posList.contains(pos);
    } 
    
}
