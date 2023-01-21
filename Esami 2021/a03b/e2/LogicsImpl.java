package a03b.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class LogicsImpl implements Logics {

    private enum Direction {
        UP(0,-1), RIGHT(1,0), DOWN(0,1), LEFT(-1, 0);

        int x;
        int y;
        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Direction next() {
            return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
        }
    }

    private Optional<Pair<Integer, Integer>> next = Optional.empty();
    private final Set<Pair<Integer, Integer>> obstacles = new HashSet<>();
    private final int size;
    private Direction curDir = Direction.UP;


    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public Optional<Pair<Integer, Integer>> getNext() {
        if (next.isEmpty()) {
            final Random r = new Random();
            next = Optional.of(new Pair<>(r.nextInt(2, size - 1), r.nextInt(2, size - 2)));
            obstacles.add(next.get());
            return next;
        }
        final var possibleDirs = List.of(this.curDir.next(), this.curDir);
        for (var dir : possibleDirs) {
            final var toVerify = new Pair<>(next.get().getX() + dir.x, next.get().getY() + dir.y);
            if (isValid(toVerify) && !isObstacle(toVerify)) {
                this.curDir = dir;
                this.next = Optional.of(toVerify);
                obstacles.add(next.get());
                return next;
            }
        }
        return Optional.empty();
    }

    private boolean isValid(Pair<Integer, Integer> pos) {
        return pos.getX() >= 0 && pos.getY() < this.size && pos.getY() >= 0 && pos.getY() < this.size;
    }

    private boolean isObstacle(Pair<Integer, Integer> pos) {
        return this.obstacles.contains(pos);
    }
    
}
