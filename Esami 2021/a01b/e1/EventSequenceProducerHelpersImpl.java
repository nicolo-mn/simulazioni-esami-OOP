package a01b.e1;

import java.lang.StackWalker.Option;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers{

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                else {
                    throw new NoSuchElementException();
                }
            }
            
        };
    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        List<E> list = new LinkedList<>();
        try {
            double time = -1;
            while (time <= toTime) {
                Pair<Double, E> ev = sequence.getNext();
                time = ev.get1();
                if (time >= fromTime && time <= toTime) {
                    list.add(ev.get2());
                }
            }
        } catch (NoSuchElementException e) {
            return list;
        }
        return list;
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        List<E> list = new LinkedList<>();
        try {
            while (true) {
                list.add(sequence.getNext().get2());
            }
        } catch (NoSuchElementException e) {
            return list;
        }
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        Pair<Double, E> ev;
        try {
            do {
                ev = sequence.getNext();
            } while (ev.get1() <= time);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return Optional.of(ev);
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                Pair<Double, E> ev;
                do {
                    ev = sequence.getNext();
                } while (!predicate.test(ev.get2()));
                return ev;
            }
            
        };
    }

}
