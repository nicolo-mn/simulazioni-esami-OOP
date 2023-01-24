package a03b.e1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EquivalenceFactoryImpl implements EquivalenceFactory{

    @Override
    public <X> Equivalence<X> fromPartition(Set<Set<X>> partition) {
        return new Equivalence<X>() {

            @Override
            public boolean areEquivalent(X x1, X x2) {
                final Set<X> set1 = partition.stream().filter(x -> x.contains(x1)).findFirst().get();
                final Set<X> set2 = partition.stream().filter(x -> x.contains(x2)).findFirst().get();
                return set1.equals(set2);
            }

            @Override
            public Set<X> domain() {
                return partition.stream().flatMap(x -> x.stream()).collect(Collectors.toSet());
            }

            @Override
            public Set<X> equivalenceSet(X x) {
                return partition.stream().filter(s -> s.contains(x)).findAny().get();
            }

            @Override
            public Set<Set<X>> partition() {
                return Collections.unmodifiableSet(partition);
            }

            @Override
            public boolean smallerThan(Equivalence<X> eq) {
                // TODO Auto-generated method stub
                return false;
            }
            
        };
    }

    @Override
    public <X> Equivalence<X> fromPredicate(Set<X> domain, BiPredicate<X, X> predicate) {
        return new Equivalence<X>() {

            @Override
            public boolean areEquivalent(X x1, X x2) {
                return domain.contains(x1) && domain.contains(x2) && predicate.test(x1,x2);
            }

            @Override
            public Set<X> domain() {
                return Collections.unmodifiableSet(domain);
            }

            @Override
            public Set<X> equivalenceSet(X x) {
                return domain.stream().filter(w -> predicate.test(x,w)).collect(Collectors.toSet());
            }

            @Override
            public Set<Set<X>> partition() {
                final Set<Set<X>> result = new HashSet<>();
                domain.forEach(e -> result.add(domain.stream()
                                        .filter(x -> predicate.test(x,e))
                                        .collect(Collectors.toSet())));
                return result;
            }

            @Override
            public boolean smallerThan(Equivalence<X> eq) {
                // TODO Auto-generated method stub
                return false;
            }
            
        };
    }

    @Override
    public <X> Equivalence<X> fromPairs(Set<Pair<X, X>> pairs) {
        return new Equivalence<X>() {

            @Override
            public boolean areEquivalent(X x1, X x2) {
                return pairs.contains(new Pair<>(x1,x2));
            }

            @Override
            public Set<X> domain() {
                return pairs.stream().flatMap(x -> Stream.of(x.getX(), x.getY())).collect(Collectors.toSet());
            }

            @Override
            public Set<X> equivalenceSet(X x) {
                return pairs.stream()
                    .filter(e -> e.getX().equals(x) || e.getY().equals(x))
                    .map(a -> a.getX().equals(x) ? a.getY() : a.getX())
                    .collect(Collectors.toSet());
            }

            @Override
            public Set<Set<X>> partition() {
                return pairs.stream()
                    .filter(a -> !a.getX().equals(a.getY()))
                    .map(e -> Stream.of(e.getX(), e.getY()).collect(Collectors.toSet()))
                    .collect(Collectors.toSet());
            }

            @Override
            public boolean smallerThan(Equivalence<X> eq) {
                // TODO Auto-generated method stub
                return false;
            }
            
        };
    }

    @Override
    public <X, Y> Equivalence<X> fromFunction(Set<X> domain, Function<X, Y> function) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
