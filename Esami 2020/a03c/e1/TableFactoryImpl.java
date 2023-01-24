package a03c.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableFactoryImpl implements TableFactory{

    @Override
    public <R, C, V> Table<R, C, V> fromMap(Map<Pair<R, C>, V> map) {
        return new Table<R,C,V>() {

            final Map<Pair<R, C>, V> map1 = new HashMap<>(map); 

            @Override
            public Set<R> rows() {
                return map1.keySet().stream().map(x -> x.getX()).collect(Collectors.toSet());
            }

            @Override
            public Set<C> columns() {
                return map1.keySet().stream().map(x -> x.getY()).collect(Collectors.toSet());
            }

            @Override
            public Map<C, Map<R, V>> asColumnMap() {
                final Map<C, Map<R, V>> colMap = new HashMap<>();
                for (var col : this.map1.keySet().stream().map(x -> x.getY()).toList()) {
                    final Map<R,V> internalMap = new HashMap<>();
                    this.map1.entrySet().stream()
                        .filter(x -> x.getKey().getY().equals(col))
                        .forEach(e -> internalMap.put(e.getKey().getX(), e.getValue()));
                    colMap.put(col, internalMap);
                }
                return colMap;
            }

            @Override
            public Map<R, Map<C, V>> asRowMap() {
                final Map<R, Map<C, V>> rowMap = new HashMap<>();
                for (var row : this.map1.keySet().stream().map(x -> x.getX()).toList()) {
                    final Map<C,V> internalMap = new HashMap<>();
                    this.map1.entrySet().stream()
                        .filter(x -> x.getKey().getX().equals(row))
                        .forEach(e -> internalMap.put(e.getKey().getY(), e.getValue()));
                    rowMap.put(row, internalMap);
                }
                return rowMap;
            }

            @Override
            public Optional<V> getValue(R row, C column) {
                return Optional.ofNullable(this.map1.get(new Pair<>(row, column)));
            }

            @Override
            public void putValue(R row, C column, V value) {
                map1.put(new Pair<>(row, column), value);
            }
            
        };
    }

    @Override
    public <R, C, V> Table<R, C, V> fromFunction(Set<R> rows, Set<C> columns, BiFunction<R, C, V> valueFunction) {
        Map<Pair<R, C>, V> map = new HashMap<>();
        rows.stream().forEach(r -> columns.stream().forEach(c -> map.put(new Pair<>(r,c), valueFunction.apply(r, c))));
        return fromMap(map);
    }

    @Override
    public <G> Table<G, G, Boolean> graph(Set<Pair<G, G>> edges) {
        Map<Pair<G, G>, Boolean> map = new HashMap<>();
        edges.stream()
            .map(e -> e.getX())
            .forEach(a -> edges.stream()
                            .map(w -> w.getY())
                            .forEach(b -> map.put(new Pair<>(a, b), edges.contains(new Pair<>(a,b)) ? true : false)));
        return fromMap(map);
    }

    @Override
    public <V> Table<Integer, Integer, V> squareMatrix(V[][] values) {
        Set<Integer> set = Stream.iterate(0, i -> i + 1).limit(values.length).collect(Collectors.toSet());
        return fromFunction(set, set, (a,b) -> values[a][b]);
    }
    
}
