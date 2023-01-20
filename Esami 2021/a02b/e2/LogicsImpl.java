package a02b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LogicsImpl implements Logics{

    private static enum Direction {
		UP(0,-1), RIGHT(1,0), LEFT(-1,0);
		
		int x;
		int y;
		Direction(int x, int y){
			this.x = x;
			this.y = y;
		}
		
	}

    private final int NUMLETTERS = 10;
    private final int size;
    private final List<Pair<Integer, Integer>> rightPositions;
    private final List<Pair<Integer, Integer>> leftPositions;
    private Optional<Pair<Integer, Integer>> mainPos;
    private Direction dir;

    
    public LogicsImpl(int size) {
        this.size = size;
        this.rightPositions = new ArrayList<>();
        this.leftPositions = new ArrayList<>();
        this.mainPos = Optional.empty();
        this.dir = Direction.UP;
        var r = new Random();
        for (int i = 0; i < NUMLETTERS; i++) {
            rightPositions.add(new Pair<>(r.nextInt(this.size), r.nextInt(this.size)));
            leftPositions.add(new Pair<>(r.nextInt(this.size), r.nextInt(this.size)));
        }

    }

    @Override
    public Optional<Pair<Integer, Integer>> next() {
        if (mainPos.isEmpty()) {
            var r = new Random();
            this.mainPos = Optional.of(new Pair<>(r.nextInt(this.size), this.size - 1));
            return mainPos;
        }
        if (isRight(mainPos.get())) {
            dir = Direction.RIGHT;
        }
        else if (isLeft(mainPos.get())) {
            dir = Direction.LEFT;
        }
        mainPos = Optional.of(new Pair<>(mainPos.get().getX() + dir.x, mainPos.get().getY() + dir.y));
        return isValid(mainPos.get()) ? mainPos : Optional.empty();

    }

    private boolean isValid(Pair<Integer, Integer> pos) {
        return pos.getX() >= 0 && pos.getX() < this.size && pos.getY() >= 0 && pos.getY() < this.size;
    } 

    @Override
    public boolean isRight(Pair<Integer, Integer> pos) {
        return this.rightPositions.contains(pos);
    }

    @Override
    public boolean isLeft(Pair<Integer, Integer> pos) {
        return this.leftPositions.contains(pos);
    }
    
}
