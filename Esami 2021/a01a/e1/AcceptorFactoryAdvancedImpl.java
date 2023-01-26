package a01a.e1;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory{

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return generalised(0, (e,s) -> Optional.of(e.isEmpty() ? s+1 : s), r -> Optional.of(r));
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return generalised(new ArrayList<Integer>(), (e,s) -> {
            if (s.isEmpty() || s.get(s.size() - 1 ) < e) {
                s.add(e);
                return Optional.of(s);
            }
            return Optional.empty();
        }, r -> Optional.of(r.stream().map(e -> String.valueOf(e)).collect(Collectors.joining(":"))));
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return generalised(new ArrayList<Integer>(), (e,s) -> Optional.of(s).filter(a -> a.add(e)).filter(g -> g.size() <= 3),
                           r -> Optional.of(r).filter(b -> b.size() == 3).map(c -> c.stream().reduce(0, (i,j) -> i+j)));
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E,Pair<O1,O2>>() {

            @Override
            public boolean accept(E e) {
                return a1.accept(e) && a2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                Optional<O1> a1res = a1.end();
                Optional<O2> a2res = a2.end();
                return a1res.isPresent() && a2res.isPresent() 
                       ? Optional.of(new Pair<>(a1.end().get(), a2.end().get()))
                       : Optional.empty();
            }
            
        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        return new Acceptor<E,O>() {

            private Optional<S> currState = Optional.of(initial);

            @Override
            public boolean accept(E e) {
                this.currState = this.currState.flatMap(s-> stateFun.apply(e, s));
                return this.currState.isPresent();
            }

            @Override
            public Optional<O> end() {
                return this.currState.isEmpty() ? Optional.empty() : outputFun.apply(this.currState.get());
            }
            
        };
    }
    
}
