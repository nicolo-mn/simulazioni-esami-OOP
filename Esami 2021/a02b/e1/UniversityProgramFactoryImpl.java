package a02b.e1;

import java.util.Set;
import java.util.function.Predicate;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory{

    @Override
    public UniversityProgram flexible() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Sector>, Predicate<Integer>>> getConditions() {
                return Set.<Pair<Predicate<Sector>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i == 60));
            }

        };
    }

    @Override
    public UniversityProgram scientific() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Sector>, Predicate<Integer>>> getConditions() {
                return Set.<Pair<Predicate<Sector>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i == 60),
                    new Pair<>(n -> n == Sector.MATHEMATICS, i -> i >= 12),
                    new Pair<>(n -> n == Sector.COMPUTER_SCIENCE, i -> i >= 12),
                    new Pair<>(n -> n == Sector.PHYSICS, i -> i >= 12));
            }

        };
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Sector>, Predicate<Integer>>> getConditions() {
                return Set.<Pair<Predicate<Sector>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i >= 48),
                    new Pair<>(n -> n == Sector.COMPUTER_SCIENCE || n == Sector.COMPUTER_ENGINEERING, i -> i >= 30));
            }

        };
    }

    @Override
    public UniversityProgram realistic() {
        return new AbstractUniversityProgram() {

            @Override
            protected Set<Pair<Predicate<Sector>, Predicate<Integer>>> getConditions() {
                return Set.<Pair<Predicate<Sector>, Predicate<Integer>>>of(
                    new Pair<>(n -> true, i -> i == 120),
                    new Pair<>(n -> n == Sector.MATHEMATICS || n == Sector.PHYSICS, i -> i <= 18),
                    new Pair<>(n -> n == Sector.COMPUTER_SCIENCE || n == Sector.COMPUTER_ENGINEERING, i -> i >= 60),
                    new Pair<>(n -> n == Sector.THESIS, i -> i == 24));
            }

        };
    }
    
}
