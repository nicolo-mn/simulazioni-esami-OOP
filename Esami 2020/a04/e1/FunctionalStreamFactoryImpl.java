package a04.e1;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class FunctionalStreamFactoryImpl implements FunctionalStreamFactory{

    private class FunctionalStreamImpl<E> implements FunctionalStream<E> {

        private Stream<E> stream;

        public FunctionalStreamImpl(Stream<E> stream) {
            this.stream = stream;
        }

            @Override
            public NextResult<E> next() {
                return new NextResult<E>() {

                    Stream<E> internalStream = stream;
                    @Override
                    public E getElement() {
                        return internalStream.findFirst().get();
                    }

                    @Override
                    public FunctionalStream<E> getStream() {
                        return new FunctionalStreamImpl<>(internalStream);
                    }
                    
                };
            }

            @Override
            public List<E> toList(int size) {
                return this.stream.limit(size).toList();
            }

            @Override
            public Iterator<E> toIterator() {
                return this.stream.iterator();
            }
    }

    @Override
    public <E> FunctionalStream<E> fromListRepetitive(List<E> list) {
        return new FunctionalStreamImpl<>(list.stream());
    }

    @Override
    public <E> FunctionalStream<E> iterate(E initial, UnaryOperator<E> op) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <A, B> FunctionalStream<B> map(FunctionalStream<A> fstream, Function<A, B> mapper) {
        // TODO Auto-generated method stub
        return null;
    }

    
    
}
