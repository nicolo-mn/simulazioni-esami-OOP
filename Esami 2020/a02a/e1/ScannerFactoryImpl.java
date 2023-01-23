package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScannerFactoryImpl implements ScannerFactory {

    @Override
    public <X, Y> Scanner<X, List<Y>> collect(Predicate<X> filter, Function<X, Y> mapper) {
        return s -> Stream.generate(() -> null)
                        .takeWhile(e -> s.hasNext())
                        .map(n -> s.next())
                        .filter(filter)
                        .map(mapper)
                        .collect(Collectors.toList());
    }

    @Override
    public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
        return s -> Stream.generate(() -> null)
                        .takeWhile(e -> s.hasNext())
                        .map(n -> s.next())
                        .filter(filter)
                        .findFirst();
    }

    @Override
    public Scanner<Integer, Optional<Integer>> maximalPrefix() {
        return new Scanner<Integer,Optional<Integer>>() {

            @Override
            public Optional<Integer> scan(Iterator<Integer> input) {
                Optional<Integer> cur = Optional.empty();
                Optional<Integer> prev = Optional.empty();
                while (input.hasNext()) {
                    if (cur.isEmpty()) {
                        cur = Optional.of(input.next());
                    }
                    else {
                        prev = cur;
                        cur = Optional.of(input.next());
                        if (cur.get() < prev.get()) {
                            return prev;
                        }
                    }
                }
                return cur;
            }
            
        };
    }

    @Override
    public Scanner<Integer, List<Integer>> cumulativeSum() {
        return new Scanner<Integer,List<Integer>>() {

            @Override
            public List<Integer> scan(Iterator<Integer> input) {
                final List<Integer> res = Stream.generate(() -> null)
                .takeWhile(e -> input.hasNext())
                .map(n -> input.next())
                .collect(Collectors.toList());
                final List<Integer> list = new ArrayList<>();
                if (!res.isEmpty()) {
                    list.add(res.get(0));
                } 
                for (int i = 1; i < res.size(); i++) {
                    list.add(res.get(i) + list.get(list.size() - 1));
                }
                return list;
            }
            
        };
    }
    
}
