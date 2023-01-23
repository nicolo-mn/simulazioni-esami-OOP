package a05.e1;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class StateFactoryImpl implements StateFactory{

    @Override
    public <S, A> State<S, A> fromFunction(Function<S, Pair<S, A>> fun) {
        return new State<S,A>() {

            @Override
            public S nextState(S s) {
                return fun.apply(s).get1();
            }

            @Override
            public A value(S s) {
                return fun.apply(s).get2();
            }

            @Override
            public <B> State<S, B> map(Function<A, B> fun) {
                return fromFunction(s -> new Pair<>(this.nextState(s), fun.apply(this.value(s))));
            }

            @Override
            public Iterator<A> iterator(S s0) {
                return Stream.iterate(s0, s -> nextState(s)).map(x -> value(x)).iterator();
            }
            
        };
    }

    @Override
    public <S, A, B> State<S, B> compose(State<S, A> state1, State<S, B> state2) {
        return fromFunction(s -> new Pair<>(state2.nextState(state1.nextState(s)), state2.value(state1.nextState(s))));
    }

    @Override
    public State<Integer, String> incSquareHalve() {
        return fromFunction(s -> new Pair<>((s+1) * (s+1) / 2, String.valueOf((s+1) * (s+1) / 2)));
    }

    @Override
    public State<Integer, Integer> counterOp(CounterOp op) {

        return fromFunction(s -> switch(op) {
            case INC -> new Pair<>(s+1, null);
            case RESET -> new Pair<>(0,null);
            case GET -> new Pair<>(s, s);
        });
    }

}
