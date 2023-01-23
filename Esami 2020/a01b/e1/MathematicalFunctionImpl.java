package a01b.e1;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class MathematicalFunctionImpl<A,B> implements MathematicalFunction<A,B>{

    private final Predicate<A> domainPred;
    private final Function<A,B> function;

    public MathematicalFunctionImpl(Predicate<A> domainPred, Function<A,B> function) {
        this.domainPred = domainPred;
        this.function = function;
    }

    @Override
    public Optional<B> apply(A a) {
        return Optional.of(a).filter(this.domainPred).map(this.function);
    }

    @Override
    public boolean inDomain(A a) {
        return this.domainPred.test(a);
    }

    @Override
    public <C> MathematicalFunction<A, C> composeWith(MathematicalFunction<B, C> f) {
        return new MathematicalFunctionImpl<>(this.domainPred, e -> f.apply(this.apply(e).get()).get());
    }

    @Override
    public MathematicalFunction<A, B> withUpdatedValue(A domainValue, B codomainValue) {
        return new MathematicalFunctionImpl<>(a -> this.inDomain(a) || a.equals(domainValue),
                                              e -> e.equals(domainValue) ? codomainValue : this.function.apply(e));
    }

    @Override
    public MathematicalFunction<A, B> restrict(Set<A> subDomain) {
        return new MathematicalFunctionImpl<>(a -> this.inDomain(a) && subDomain.contains(a), this.function);
    }
    
}
