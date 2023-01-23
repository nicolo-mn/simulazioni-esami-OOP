package a06.e1;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CirclerFactoryImpl implements CirclerFactory{

    private static <T> Circler<T> fromStream(Function<List<T>, Stream<T>> func) {
        return new Circler<T>() {

            private Stream<T> infiniteStream;
            private Function<List<T>, Stream<T>> streamFunc = func;

            @Override
            public void setSource(List<T> elements) {
                this.infiniteStream = streamFunc.apply(elements);
            }

            @Override
            public T produceOne() {
                final var elem = this.infiniteStream.findFirst();
                this.infiniteStream = this.infiniteStream.skip(1);
                return elem.get();
            }

            @Override
            public List<T> produceMany(int n) {
                final List<T> elemList = this.infiniteStream.limit(n).collect(Collectors.toList());
                this.infiniteStream = this.infiniteStream.skip(n);
                return elemList;
            }
            
        };
    }

    @Override
    public <T> Circler<T> leftToRight() {
        return fromStream(s -> Stream.iterate(s.get(0), e -> s.get((s.indexOf(e) + 1) % s.size())));
    }

    @Override
    public <T> Circler<T> alternate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Circler<T> stayToLast() {
        return fromStream(s -> Stream.generate(() -> s.get(0)));
    }

    @Override
    public <T> Circler<T> leftToRightSkipOne() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Circler<T> alternateSkipOne() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Circler<T> stayToLastSkipOne() {
        // TODO Auto-generated method stub
        return null;
    }

}
