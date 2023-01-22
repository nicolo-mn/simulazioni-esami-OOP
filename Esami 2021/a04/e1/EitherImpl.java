package a04.e1;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class EitherImpl<A,B> implements Either<A,B>{

    private final Optional<B> success;
    private final Optional<A> failure;

    private EitherImpl(Optional<B> success, Optional<A> failure) {
        if (failure.isEmpty() ^ success.isPresent()) {
			throw new IllegalArgumentException();
		}
        this.success = success;
        this.failure = failure;
    }

    public static <A,B> Either<A,B> obtainSucess(B succ) {
        return new EitherImpl<A,B>(Optional.of(succ), Optional.empty());
    }

    public static <A,B> Either<A,B> obtainFailure(A fail) {
        return new EitherImpl<A,B>(Optional.empty(), Optional.of(fail));
    }

    @Override
    public boolean isFailure() {
        return this.failure.isPresent();
    }

    @Override
    public boolean isSuccess() {
        return this.success.isPresent();
    }

    @Override
    public Optional<A> getFailure() {
        return this.failure;
    }

    @Override
    public Optional<B> getSuccess() {
        return this.success;
    }

    @Override
    public B orElse(B other) {
        return this.isSuccess() ? this.success.get() : other;
    }

    @Override
    public <B1> Either<A, B1> map(Function<B, B1> function) {
        return this.isSuccess() ? EitherImpl.obtainSucess(function.apply(this.success.get())) : EitherImpl.obtainFailure(this.failure.get());
    }

    @Override
    public <B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function) {
        return this.isSuccess() ? function.apply(this.success.get()) : EitherImpl.obtainFailure(this.failure.get());
    }

    @Override
    public <A1> Either<A1, B> filterOrElse(Predicate<B> predicate, A1 failure) {
        return this.isFailure() || !predicate.test(this.success.get()) ? EitherImpl.obtainFailure(failure) : EitherImpl.obtainSucess(this.success.get());
    }

    @Override
    public <C> C fold(Function<A, C> funFailure, Function<B, C> funSuccess) {
        return this.isFailure() ? funFailure.apply(this.failure.get()) : funSuccess.apply(this.success.get());
    }

    
    
}
