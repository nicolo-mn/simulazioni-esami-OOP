package a03a.e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics{

    private final int size;
    private Pair<Integer, Integer> rookPos;
    private final Set<Pair<Integer, Integer>> pawns;
    private Set<Pair<Integer, Integer>> selected;

    public LogicsImpl(int size) {
        this.size = size;
        final Random r = new Random();
        pawns = Stream.generate(() -> new Pair<>(r.nextInt(size), r.nextInt(size)))
            .limit(5)
            .collect(Collectors.toSet());
        rookPos = Stream.generate(() -> new Pair<>(r.nextInt(size), r.nextInt(size)))
            .filter(x -> !this.pawns.contains(x))
            .limit(1)
            .findAny().get();
        this.hit(rookPos.getX(), rookPos.getY());
    }

    @Override
    public void hit(int x, int y) {
        this.rookPos = new Pair<>(x,y);
        this.pawns.remove(rookPos);

        final Set<Pair<Integer, Integer>> avaliablePos = new HashSet<>();   
        avaliablePos.add(rookPos);
        for (var dir : Direction.values()) {
            boolean isPawn = false;
            Pair<Integer, Integer> toVerify = rookPos;
            while (this.isValid(toVerify) && !isPawn) {
                avaliablePos.add(toVerify);
                if (this.isPawn(toVerify.getX(), toVerify.getY())) {
                    isPawn = true;
                }
                toVerify = new Pair<>(toVerify.getX() + dir.x, toVerify.getY() + dir.y);
            }
        }
        selected = avaliablePos;
    }

    private boolean isValid(Pair<Integer, Integer> pos) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < this.size && pos.getY() < this.size;
    }

    @Override
    public boolean isRook(int x, int y) {
        return this.rookPos.equals(new Pair<>(x,y));
    }

    @Override
    public boolean isPawn(int x, int y) {
        return this.pawns.contains(new Pair<>(x,y));
    }

    @Override
    public boolean isOver() {
        return this.pawns.isEmpty();
    }

    @Override
    public boolean isSelected(int x, int y) {
        return this.selected.contains(new Pair<>(x,y));
    }

    private enum Direction {

        UP(0,-1), RIGHT(1,0), DOWN(0,1), LEFT(-1,0);

        int x;
        int y;
        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
}
