package a01a.e1;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.xml.stream.StreamFilter;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory{

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E,Pair<O1,O2>>() {

            Acceptor<E,O1> acc1 = a1;
            Acceptor<E,O2> acc2 = a2;
            @Override
            public boolean accept(E e) {
                return acc1.accept(e) && acc2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                Optional<O1> opt1 = acc1.end();
                Optional<O2> opt2 = acc2.end();
                if (opt1.isPresent() && opt2.isPresent()) {
                    return Optional.of(new Pair(opt1.get(), opt2.get()));
                }

                return Optional.empty();
            }
            
        };
    }

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return new Acceptor<String,Integer>() {
            private int nullStr = 0;
            @Override
            public boolean accept(String e) {
                if (e.length() == 0) {
                    ++nullStr;
                }
                return true;
            }

            @Override
            public Optional<Integer> end() {
                return Optional.of(nullStr);
            }
            
        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        return new Acceptor<E,O>() {
            
            private Optional<S> state = Optional.ofNullable(initial);
            @Override
            public boolean accept(E e) {
                if (state.isPresent()) {
                    Optional<S> tmp = stateFun.apply(e, state.get());
                    if (tmp.isPresent()) {
                        state = tmp;
                    }
                    return tmp.isPresent();
                }
                return false;
            }

            @Override
            public Optional<O> end() {
                return outputFun.apply(state.get());
            }
            
        };
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return new Acceptor<Integer,String>() {
            private boolean stillValid = true;
            private boolean isFirst = true;
            private String res = "";
            private int last;
            @Override
            public boolean accept(Integer e) {
                if (isFirst) {
                    isFirst = false;
                    last = e;
                    res = Integer.toString(e);
                    return true;
                }
                else if (e > last && stillValid) {
                    last = e;
                    res = res + ":" + Integer.toString(e);
                    return true;
                }
                else {
                    if (stillValid) {
                        stillValid = false;
                    }
                    return false;
                }
            }

            @Override
            public Optional<String> end() {
                return stillValid ? Optional.of(res) : Optional.empty();
            }
            
        };
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return new Acceptor<Integer,Integer>() {
            private int sum = 0;
            private int nElems = 0;
            @Override
            public boolean accept(Integer e) {
                ++nElems;
                if (nElems <= 3) {
                    sum += e;
                    return true;
                }
                return false;
            }

            @Override
            public Optional<Integer> end() {
                return nElems == 3 ? Optional.of(sum) : Optional.empty();
            }
            
        };
    }
}
