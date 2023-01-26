package a01b.e2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static a01b.e2.Logics.HitType.*;

public class LogicsImpl implements Logics{

    private final Set<Pair<Integer,Integer>> selected = new HashSet<>();
    Optional<Pair<Integer,Integer>> first = Optional.empty();
    Optional<Pair<Integer,Integer>> second = Optional.empty();
    Optional<Pair<Integer,Integer>> third = Optional.empty();

    @Override
    public HitType hit(int x, int y) {
        if (this.first.isEmpty()) {
            this.first = Optional.of(new Pair<>(x,y));
            return FIRST;
        }
        else if (this.second.isEmpty() && this.isVertical(x, y)) {
            this.second = Optional.of(new Pair<>(x,y));
            return SECOND;
        }
        else if (this.second.isPresent() && this.third.isEmpty() && this.isHorizontal(x, y)) {
            this.third = Optional.of(new Pair<>(x,y));
            this.range(this.first.get().getY(), this.second.get().getY()).forEach(e -> this.selected.add(new Pair<>(this.first.get().getX(),e)));
            this.range(this.first.get().getX(), x).forEach(e -> this.selected.add(new Pair<>(e,y)));
            return THIRD;
        }
        return WRONG;
    }

    @Override
    public boolean isSelected(int x, int y) {
        return this.selected.contains(new Pair<>(x,y));
    }

    private boolean isHorizontal(int x, int y) {
        return this.first.get().getY() == y && this.first.get().getX() != x;
    }

    private boolean isVertical(int x, int y) {
        return this.first.get().getX() == x && this.first.get().getY() != y;
    }

    Iterable<Integer> range(int x1, int x2) {
        return x1<=x2 ? () -> IntStream.range(x1,x2).filter(e -> e != x1).iterator() : () -> IntStream.range(x2, x1).filter(e -> e != x1).iterator();
    }
    
}
