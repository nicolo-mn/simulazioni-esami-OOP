package a01b.e1;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class MathematicalFunctionsFactoryImpl implements MathematicalFunctionsFactory{

    @Override
    public <A, B> MathematicalFunction<A, B> constant(Predicate<A> domainPredicate, B value) {
        return new MathematicalFunctionImpl<>(domainPredicate, e -> value);
    }

    @Override
    public <A, B> MathematicalFunction<A, A> identity(Predicate<A> domainPredicate) {
        return new MathematicalFunctionImpl<>(domainPredicate, e -> e);
    }

    @Override
    public <A, B> MathematicalFunction<A, B> fromFunction(Predicate<A> domainPredicate, Function<A, B> function) {
        return new MathematicalFunctionImpl<>(domainPredicate, function);
    }

    @Override
    public <A, B> MathematicalFunction<A, B> fromMap(Map<A, B> map) {
        return new MathematicalFunctionImpl<>(a -> map.containsKey(a), e -> map.get(e));
    }
    
}
