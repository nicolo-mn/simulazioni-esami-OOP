package a03c.e1;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeterministicParserFactoryImpl implements DeterministicParserFactory{

    @Override
    public DeterministicParser oneSymbol(String s) {
        return x -> Optional.ofNullable(x.stream()
                        .filter(y -> !y.equals(s))
                        .collect(Collectors.toList()))
                        .filter(z -> z.size() == x.size() - 1);
    }

    @Override
    public DeterministicParser twoSymbols(String s1, String s2) {
        return sequence(oneSymbol(s1), oneSymbol(s2));
    }

    @Override
    public DeterministicParser possiblyEmptyIncreasingSequenceOfPositiveNumbers() {
        return null;
    }

    @Override
    public DeterministicParser sequenceOfParsersWithDelimiter(String start, String stop, String delimiter,
            DeterministicParser element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeterministicParser sequence(DeterministicParser first, DeterministicParser second) {
        return x -> second.accepts(first.accepts(x).isPresent() ? first.accepts(x).get() : List.of());
    }
    
}
