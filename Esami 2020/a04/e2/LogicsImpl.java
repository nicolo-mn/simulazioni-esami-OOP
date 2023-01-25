package a04.e2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics{

    private Pair<Integer, Integer> mainPos;
    private final Set<Pair<Integer, Integer>> pawns;
    private boolean isKing = true;
    private final int size;


    public LogicsImpl(int size) {
        this.size = size;
        final Random r = new Random();
        this.mainPos = new Pair<>(r.nextInt(size), r.nextInt(size));
        this.pawns = Stream.generate(()->new Pair<>(r.nextInt(size),r.nextInt(size)))
                .filter(e -> !e.equals(this.mainPos))
                .limit(3)
                .collect(Collectors.toSet());
        System.out.println("Pawns' size is: "+this.pawns.size());
    }

    @Override
    public boolean isOver() {
        return this.pawns.isEmpty();
    }

    @Override
    public boolean isKing() {
        return this.isKing;
    }

    @Override
    public Pair<Integer, Integer> getMainPos() {
        return this.mainPos;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPawns() {
        return Collections.unmodifiableSet(this.pawns);
    }

    @Override
    public void hit(int x, int y) {
        if (this.isValid(x, y) && this.isLegal(x, y)) {
            this.isKing = !this.isKing;
            this.mainPos = new Pair<>(x,y);
            this.pawns.remove(mainPos);
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < this.size && y >= 0 && y < this.size;
    }

    private boolean isLegal(int x, int y) {
        if (this.isKing) {
            return !this.mainPos.equals(new Pair<>(x,y)) && Math.abs(x - this.mainPos.getX()) <= 1 && Math.abs(y - this.mainPos.getY()) <= 1;
        }
        return !this.mainPos.equals(new Pair<>(x,y)) &&  (Math.abs(x - this.mainPos.getX()) == 1 && Math.abs(y - this.mainPos.getY()) == 2)
               || (Math.abs(x - this.mainPos.getX()) == 2 && Math.abs(y - this.mainPos.getY()) == 1);
    }
    
}
