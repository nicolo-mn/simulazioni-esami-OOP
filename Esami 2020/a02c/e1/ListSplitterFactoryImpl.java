package a02c.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ListSplitterFactoryImpl implements ListSplitterFactory{

    @Override
    public <X> ListSplitter<X> asPairs() {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(List<X> list) {
                List<List<X>> result = new ArrayList<>();
                List<X> residual = list;
                while (residual.size() != 0) {
                    final List<X> selected = residual.stream().limit(2).toList();
                    if (selected.size() == 2) {
                        result.add(selected);
                    }
                    residual = residual.stream().skip(2).toList();
                }
                return result;
            }
            
        };
    }

    @Override
    public <X> ListSplitter<X> asTriplets() {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(List<X> list) {
                List<List<X>> result = new ArrayList<>();
                List<X> residual = list;
                while (residual.size() != 0) {
                    final List<X> selected = residual.stream().limit(3).toList();
                    if (selected.size() == 3) {
                        result.add(selected);
                    }
                    residual = residual.stream().skip(3).toList();
                }
                return result;
            }
            
        };
    }

    @Override
    public <X> ListSplitter<X> asTripletsWithRest() {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(List<X> list) {
                List<List<X>> result = new ArrayList<>();
                List<X> residual = list;
                while (residual.size() != 0) {
                    final List<X> selected = residual.stream().limit(3).toList();
                    result.add(selected);
                    residual = residual.stream().skip(3).toList();
                }
                return result;
            }
            
        };
    }

    @Override
    public <X> ListSplitter<X> bySeparator(X separator) {
        return this.byPredicate(t -> t.equals(separator));
    }

    @Override
    public <X> ListSplitter<X> byPredicate(Predicate<X> predicate) {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(List<X> list) {
                Predicate<X> condition = predicate;
                List<List<X>> result = new ArrayList<>();
                List<X> residual = list;
                while (residual.size() != 0) {
                    condition = condition.negate();
                    final List<X> selected = residual.stream().takeWhile(condition).toList();
                    if (!selected.isEmpty()) {
                        result.add(selected);
                    }
                    residual = residual.stream().dropWhile(condition).toList();
                    
                }
                return result;
            }
            
        };
    }

}
