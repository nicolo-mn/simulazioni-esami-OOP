package a02a.e1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class DietImpl implements Diet{

    private final Map<String,Map<Nutrient,Integer>> foodMap = new HashMap<>();
    private final List<Pair<Predicate<Nutrient>, Predicate<Double>>> predList;

    public DietImpl(List<Pair<Predicate<Nutrient>, Predicate<Double>>> predList) {
        this.predList = predList;
    }

    @Override
    public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
        this.foodMap.put(name, nutritionMap);
    }

    @Override
    public boolean isValid(Map<String, Double> dietMap) {
        final Map<Nutrient, Double> caloriesMap = new HashMap<>();
        dietMap.entrySet()
            .forEach(e -> this.foodMap.get(e.getKey()).entrySet()
                            .forEach(a -> caloriesMap.merge(a.getKey(), a.getValue() * e.getValue() / 100, (c,d) -> c+d)));
        return this.predList.stream().allMatch(predPair -> predPair.get2().test(caloriesMap.entrySet().stream()
                                                                .filter(x -> predPair.get1().test(x.getKey()))
                                                                .mapToDouble(e -> e.getValue())
                                                                .sum()));
    }
    
}
