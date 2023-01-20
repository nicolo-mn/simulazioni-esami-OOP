package a02b.e1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractUniversityProgram implements UniversityProgram{

    private final Map<String, Pair<Sector, Integer>> courseMap = new HashMap<>();

    @Override
    public void addCourse(String name, Sector sector, int credits) {
        this.courseMap.put(name, new Pair<>(sector, credits));        
    }

    @Override
    public boolean isValid(Set<String> courseNames) {
        List<Pair<Sector, Integer>> courseList = courseNames.stream()
                                    .map(x -> this.courseMap.get(x))
                                    .collect(Collectors.toList());
        System.out.println(courseList);
        return getConditions().stream().allMatch(c -> isConditionValid(c, courseList));
    }

    private boolean isConditionValid(Pair<Predicate<Sector>, Predicate<Integer>> c,
            List<Pair<Sector, Integer>> courseList) {
        return c.get2().test(courseList.stream()
                    .filter(x -> c.get1().test(x.get1()))
                    .mapToInt(y -> y.get2())
                    .sum());
    }

    abstract protected Set<Pair<Predicate<Sector>, Predicate<Integer>>> getConditions();
    
}
