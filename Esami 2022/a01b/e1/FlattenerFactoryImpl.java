package a01b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FlattenerFactoryImpl implements FlattenerFactory{

    @Override
    public Flattener<Integer, Integer> sumEach() {
        return x -> x.stream().map(s -> s.stream().mapToInt(e -> e).sum()).toList();
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return x -> x.stream().flatMap(s -> s.stream()).toList();
    }

    @Override
    public Flattener<String, String> concatPairs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return x -> x.stream().map(mapper).toList();
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return new Flattener<Integer,Integer>() {

            @Override
            public List<Integer> flatten(List<List<Integer>> list) {
                List<List<Integer>> res = new ArrayList<>();
                for (int i = 0; i < list.get(0).size(); i++) {
                    List<Integer> posList = new ArrayList<>();
                    for (var internalList : list) {
                        posList.add(internalList.get(i));
                    }
                    res.add(posList);
                }
                return res.stream().map(s -> s.stream().mapToInt(e -> e).sum()).toList();
            }
            
        };
    }
    
}
