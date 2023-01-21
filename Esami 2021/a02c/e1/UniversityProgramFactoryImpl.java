package a02c.e1;

import java.util.Set;
import java.util.function.Predicate;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory{

    @Override
    public UniversityProgram flexible() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
                return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i == 48)
                );
            }

        };
    }

    @Override
    public UniversityProgram fixed() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
                return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i == 60),
                    new Pair<>(n -> n == Group.MANDATORY, i -> i == 12),
                    new Pair<>(n -> n == Group.OPTIONAL, i -> i == 36),
                    new Pair<>(n -> n == Group.THESIS, i -> i == 12)
                );
            }

        };
    }

    @Override
    public UniversityProgram balanced() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
                return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i == 60),
                    new Pair<>(n -> n == Group.MANDATORY, i -> i == 24),
                    new Pair<>(n -> n == Group.OPTIONAL, i -> i >= 24),
                    new Pair<>(n -> n == Group.FREE, i -> i <= 12),
                    new Pair<>(n -> n == Group.THESIS, i -> i <= 12)
                );
            }

        };
    }

    @Override
    public UniversityProgram structured() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
                return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i == 60),
                    new Pair<>(n -> n == Group.MANDATORY, i -> i == 12),
                    new Pair<>(n -> n == Group.OPTIONAL_A, i -> i >= 6),
                    new Pair<>(n -> n == Group.OPTIONAL_B, i -> i >= 6),
                    new Pair<>(n -> n == Group.OPTIONAL, i -> i == 30),
                    new Pair<>(n -> n == Group.FREE || n == Group.THESIS, i -> i == 18)
                );
            }

        };
    }
    
}
