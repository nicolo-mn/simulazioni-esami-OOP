package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PatternExtractorFactoryImpl implements PatternExtractorFactory{

    private static <A,B> PatternExtractor<A,B> fromFunction(Predicate<A> condition, Function<List<A>, B> mapper) {
        return new PatternExtractor<A,B>() {

            @Override
            public List<B> extract(List<A> input) {
                List<A> residual = input;
                List<List<A>> filteredLists = new ArrayList<>();
                while (residual.size() != 0) {
                    if (condition.test(residual.get(0))) {
                        filteredLists.add(residual.stream().takeWhile(condition).toList());
                        residual = residual.stream().dropWhile(condition).toList();
                    }
                    else {
                        residual = residual.stream().dropWhile(condition.negate()).toList();
                    }
                }
                return filteredLists.stream().map(mapper).toList();
            }
            
        };
    }

    @Override
    public PatternExtractor<Integer, Integer> countConsecutiveZeros() {
        return fromFunction(x -> x == 0, List::size);
    }

    @Override
    public PatternExtractor<Double, Double> averageConsecutiveInRange(double min, double max) {
        return fromFunction(x -> x >= min && x <= max, y -> y.stream().mapToDouble(a -> a).average().getAsDouble());
    }

    @Override
    public PatternExtractor<String, String> concatenateBySeparator(String separator) {
        return fromFunction(x -> !x.equals(":"), y -> y.stream().reduce(String::concat).get());
    }

    @Override
    public PatternExtractor<String, Double> sumNumericStrings() {
        return fromFunction(x -> {
            try {
                Double.parseDouble(x);
                return true;
            } catch (Exception e) {
                return false;
            }
        }, y -> y.stream().mapToDouble(Double::parseDouble).sum());
    }
    
}
