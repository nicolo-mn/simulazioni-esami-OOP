package a05.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import a01c.e2.Logics.Direction;

public class LogicsImpl implements Logics{

    private final Map<Pair<Integer, Integer>, Object> map = new HashMap<>();
    private final int size;
    private Optional<Operation> operationPending = Optional.empty();
    private Optional<Pair<Integer, Integer>> lastHit;
    private Optional<Boolean> result = Optional.empty();


    public LogicsImpl(int size) {
        this.size = size;
        final Random r = new Random();
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final Pair<Integer, Integer> pos = new Pair<>(j,i);
                this.map.put(pos, (pos.getX() + pos.getY()) % 2 == 0 
                ? r.nextBoolean() 
                : Operation.values()[r.nextInt(Operation.values().length)]);
            }
        }
    }

    private Object get(int x, int y) {
		return this.map.get(new Pair<>(x,y));
	}

    @Override
    public boolean isCellBoolean(int x, int y) {
        return this.get(x,y) instanceof Boolean;
    }

    @Override
    public void hit(int x, int y) {
        if (this.result.isEmpty() && this.isCellBoolean(x, y)) {
            this.result = Optional.of(this.getCellAsBool(x, y));
            this.lastHit = Optional.of(new Pair<>(x,y));
        }
        else if(this.result.isPresent() && this.isCellBoolean(x, y) && this.operationPending.isPresent() && this.isAdjacent(new Pair<>(x,y), lastHit.get())) {
            this.result = Optional.of(operationPending.get().getOperator().apply(result.get(), this.getCellAsBool(x, y)));
            this.lastHit = Optional.of(new Pair<>(x,y));
            this.operationPending = Optional.empty();
        }
        else if (this.result.isPresent() && !this.isCellBoolean(x, y) && this.operationPending.isEmpty() && this.isAdjacent(new Pair<>(x,y), lastHit.get())) {
            this.operationPending = Optional.of(this.getCellAsOperation(x, y));
            this.lastHit = Optional.of(new Pair<>(x,y));
        }
    }

    @Override
    public boolean isOver() {
        return this.result.isPresent() ? !this.result.get() : false;
    }

    @Override
    public Operation getCellAsOperation(int x, int y) {
        return (Operation)this.get(x,y);
    }

    @Override
    public boolean getCellAsBool(int x, int y) {
        return (boolean)this.get(x, y);
    }

    private boolean isAdjacent(Pair<Integer, Integer> last, Pair<Integer, Integer> other) {
		return Math.abs(last.getX() - other.getX()) + Math.abs(last.getY() - other.getY()) == 1;  
	}
    
}
