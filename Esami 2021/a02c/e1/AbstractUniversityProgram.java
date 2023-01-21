package a02c.e1;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractUniversityProgram implements UniversityProgram{

    private Map<String, Pair<Set<Group>, Integer>> courses;

    @Override
    public boolean isValid(Set<String> courseNames) {
        List<Pair<Set<Group>, Integer>> coursesSpecs = courses.entrySet()
                            .stream()
                            .filter(y -> courseNames.contains(y.getKey()))
                            .map(x -> x.getValue())
                            .collect(Collectors.toList());
        return getConstraints().stream().allMatch(c -> isConstranintValid(coursesSpecs, c));
    }

    @Override
    public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
        this.courses = courses;        
    }

    private boolean isConstranintValid(List<Pair<Set<Group>, Integer>> c, Pair<Predicate<Group>, Predicate<Integer>> pr) {
        return pr.get2().test(c.stream()
                .filter(x -> x.get1().stream().anyMatch(y -> pr.get1().test(y)))
                .mapToInt(z -> z.get2())
                .sum());
    }

    abstract protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints();
    
}
