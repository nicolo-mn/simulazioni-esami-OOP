package a03a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.management.AttributeList;

public class DecisionChainFactoryImpl implements DecisionChainFactory{

    @Override
    public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
        return switchChain(mapList.stream()
                    .map(x -> new Pair<Predicate<A>, B>(n -> n == x.get1(), x.get2()))
                    .collect(Collectors.toList()), defaultReply);
    }

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.of(b);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return null;
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
        return twoWay(predicate, oneResult(positive),oneResult(negative));
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        return new DecisionChain<A,B>() {

            private List<Pair<Predicate<A>, B>> casesList = new ArrayList<>(cases);

            @Override
            public Optional<B> result(A a) {
                return cases.get(0).get1().test(a) ? Optional.of(casesList.get(0).get2()) : Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                final List<Pair<Predicate<A>, B>> nextCasesList = new ArrayList<>(casesList);
                nextCasesList.remove(0);
                return nextCasesList.isEmpty() ? oneResult(defaultReply) : switchChain(nextCasesList, defaultReply); 
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive,
            DecisionChain<A, B> negative) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return predicate.test(a) ? positive : negative;
            }
            
        };
    }

}
