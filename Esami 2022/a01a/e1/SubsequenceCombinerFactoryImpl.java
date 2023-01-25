package a01a.e1;

import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory{

    private static class SubsequenceCombinerImpl<X,Y> implements SubsequenceCombiner<X,Y> {

        private final Predicate<List<X>> pred;
        private final Function<List<X>, Y> func;

        public SubsequenceCombinerImpl(Predicate<List<X>> pred, Function<List<X>, Y> func) {
            this.pred = pred;
            this.func = func;
        }

        @Override
        public List<Y> combine(List<X> list) {
            final List<Y> res = new ArrayList<>();
            List<X> acc = new ArrayList<>();
            for (var elem : list) {
                if (this.pred.test(acc)) {
                    res.add(func.apply(acc));
                    acc = new ArrayList<>();
                }
                acc.add(elem);
            }
            if (!acc.isEmpty()) {
                res.add(func.apply(acc));
            }
            return res;
        }

    }

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombinerImpl<>(l -> l.size() == 3, x -> x.stream()
                                                    .mapToInt(e -> e)
                                                    .sum());
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombinerImpl<>(l -> l.size() == 3, x -> x);
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return new SubsequenceCombinerImpl<>(l -> !l.isEmpty() && l.get(l.size() - 1) == 0, x -> x.stream().filter(e -> e != 0).reduce(0, (a,b) -> a+1));
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombinerImpl<>(l -> l.size() == 1, x -> function.apply(x.get(0)));
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombinerImpl<>(l -> l.stream().mapToInt(e -> e).sum() >= threshold, x -> x); 
    }
    
}
