package a01c.e1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory{

    private <E, R> Iterator<R> mapIterator(Map<Double, E> map, Function<Entry<Double, E>, R> func) {
        return map.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e1.getKey(), e2.getKey()))
                .map(func::apply).iterator();
    }

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        return this.fromIterators(this.mapIterator(map, e -> e.getKey()), this.mapIterator(map, e -> e.getValue()));
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        return new EventHistory<E>() {

            private Double time = times.next();
            private E evContent = content.next();
            @Override
            public double getTimeOfEvent() {
                return time;
            }

            @Override
            public E getEventContent() {
                return evContent;
            }

            @Override
            public boolean moveToNextEvent() {
                if (times.hasNext() && content.hasNext()) {
                    time = times.next();
                    evContent = content.next();
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
        return this.fromIterators(Stream.iterate(initial, x -> x + delta).iterator(), content.iterator());
    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return this.fromIterators(Stream.generate(Math::random).limit(size).iterator(), 
                                  Stream.generate(content).iterator());
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        try (BufferedReader buffReader = new BufferedReader(new FileReader(file))) {
            return this.fromIterators(buffReader.lines().map(x -> x.split(":", 2)).map(x -> Double.parseDouble(x[0])).iterator(),
                   buffReader.lines().map(x -> x.split(":", 2)).map(x -> x[1]).iterator());
        }
       
    }

}
