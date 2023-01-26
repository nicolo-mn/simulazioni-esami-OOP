package a01c.e1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory{

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        return fromIterators(
            map.entrySet().stream()
                .sorted((e1,e2) -> Double.compare(e1.getKey(), e2.getKey()))
                .map(e -> e.getKey())
                .iterator(),
            map.entrySet().stream()
                .sorted((e1,e2) -> Double.compare(e1.getKey(), e2.getKey()))
                .map(e -> e.getValue())
                .iterator()
        );
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        return new EventHistory<E>() {

            @Override
            public double getTimeOfEvent() {
                return times.next();
            }

            @Override
            public E getEventContent() {
                return content.next();
            }

            @Override
            public boolean moveToNextEvent() {
                return times.hasNext() && content.hasNext();
            }
            
        };
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
        return fromIterators(Stream.iterate(initial, t -> t+delta).iterator(), content.iterator());
    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return fromIterators(
            Stream.iterate(Math.random(), x -> x + Math.random()).limit(size).iterator(),
            Stream.generate(content).limit(size).iterator()
        );
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        try (BufferedReader buff = new BufferedReader(new FileReader(file))) {
            String str;
            Map<Double, String> map = new HashMap<>();
            while ((str = buff.readLine()) != null) {
                String[] strSplit = str.split(":", 2);
                map.put(Double.parseDouble(strSplit[0]), strSplit[1]);
            }
            return fromMap(map);
        }
    }
    
}
