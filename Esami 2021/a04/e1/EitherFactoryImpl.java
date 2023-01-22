package a04.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class EitherFactoryImpl implements EitherFactory{

    @Override
    public <A, B> Either<A, B> success(B b) {
        return EitherImpl.obtainSucess(b);
    }

    @Override
    public <A, B> Either<A, B> failure(A a) {
        return EitherImpl.obtainFailure(a);
    }

    @Override
    public <A> Either<Exception, A> of(Supplier<A> computation) {
        try {
            return EitherImpl.obtainSucess(computation.get());
        } catch (Exception e) {
            return EitherImpl.obtainFailure(e);
        }
    }

    @Override
    public <A, B, C> Either<A, List<C>> traverse(List<B> list, Function<B, Either<A, C>> function) {
        Either<A, List<C>> e = success(new ArrayList<>());
		for (var b: list) {
			var e2 = function.apply(b);
			if (e2.isFailure()) {
				return failure(e2.getFailure().get());
			}
			e.getSuccess().get().add(e2.getSuccess().get());
		}
		return e;
    }

}
