package a05.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class LogicsImpl implements Logics{

    private final int size;
    private final Map<Pair<Integer,Integer>, Integer> numberMap = new HashMap<>();

    public LogicsImpl(int size) {
        this.size = size;
        final Random r = new Random();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.numberMap.put(new Pair<>(j,i), r.nextInt(10));
            }
        }
    }

    @Override
    public boolean isNumber(int x, int y) {
        return this.numberMap.containsKey(new Pair<>(x,y));
    }

    @Override
    public void hit(int x, int y) {
        if (this.numberMap.containsKey(new Pair<>(x,y))) {
            final int value = this.numberMap.get(new Pair<>(x,y));
            for (int i = x - 1; i <= x+1; i++) {
                for (int j = y - 1; j <= y+1; j++) {
                    if (this.isValid(i, j) && this.areAdjacent(new Pair<>(x,y), new Pair<>(i,j))) {
                        this.numberMap.put(new Pair<>(i,j), value);
                    }
                }
            }
            this.numberMap.remove(new Pair<>(x,y));
            return;
        }
        int total = 0;
        for (int i = x - 1; i <= x+1; i++) {
            for (int j = y - 1; j <= y+1; j++) {
                if (this.numberMap.containsKey(new Pair<>(i,j))) {
                    total += this.numberMap.remove(new Pair<>(i,j));
                }
            }
        }
        this.numberMap.put(new Pair<>(x,y), total);
    }

    @Override
    public Integer getNumber(int x, int y) {
        return this.numberMap.get(new Pair<>(x,y));
    }

    @Override
    public boolean isOver() {
        return this.numberMap.isEmpty();
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < this.size && y < this.size;
    }

    private boolean areAdjacent(Pair<Integer,Integer> one, Pair<Integer,Integer> other) {
        return Math.abs(one.getX() - other.getX()) <= 1 && Math.abs(one.getY() - other.getY()) <= 1;
    }
    
}
