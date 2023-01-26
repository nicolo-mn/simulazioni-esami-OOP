package a01a.e2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics{

    private final int size;
    private final Set<Pair<Integer,Integer>> selected = new HashSet<>();
    private Optional<Pair<Integer,Integer>> first = Optional.empty();

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(int x, int y) {
        if (this.first.isEmpty()) {
            this.first = Optional.of(new Pair<>(x,y));
            this.selected.add(new Pair<>(x,y));
            return false;
        } else {
            for (var i : range(x, this.first.get().getX())) {
                for (var j : range(y, this.first.get().getY())) {
                    this.selected.add(new Pair<>(i,j));
                }
            }
            // this.range(x, this.first.get().getX())
            //     .forEach(a -> this.range(y, this.first.get().getY())
            //         .forEach(b -> this.selected.add(new Pair<>(a,b))));
            this.first = Optional.empty();
            return this.selected.size() == this.size * this.size;
        }
    }

    @Override
    public boolean isSelected(int x, int y) {
        return this.selected.contains(new Pair<>(x,y));
    }

    private Iterable<Integer> range(int x, int x2) {
		return x<=x2 ? ()->IntStream.rangeClosed(x,x2).iterator() : ()->IntStream.rangeClosed(x2,x).iterator();
	}
    
}
