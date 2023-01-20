package a02a.e1;

import java.util.HashMap;
import java.util.Map;

public class DietFactoryImpl implements DietFactory{

    @Override
    public Diet standard() {
        return new Diet() {
            
            private final Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
            
            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                foods.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totCals = 0;
                for (var entry : dietMap.entrySet()) {
                    var foodMap = foods.get(entry.getKey());
                    for (var foodEntry : foodMap.entrySet()) {
                        totCals += foodEntry.getValue() * entry.getValue() / 100;
                    }   
                }
                return totCals >= 1500 && totCals <= 2000;
            }
            
        };
    }

    @Override
    public Diet lowCarb() {
        return new Diet() {
            
            private final Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
            
            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                foods.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totCals = 0;
                int totCarbs = 0;
                for (var entry : dietMap.entrySet()) {
                    var foodMap = foods.get(entry.getKey());
                    for (var foodEntry : foodMap.entrySet()) {
                        Double nutrientCals = foodEntry.getValue() * entry.getValue() / 100;
                        switch(foodEntry.getKey()) {
                            case CARBS: totCarbs += nutrientCals; break;
                            case PROTEINS:
                            case FAT:
                        }
                        totCals += nutrientCals;
                    }   
                }
                return totCals >= 1000 && totCals <= 1500 && totCarbs <=300;
            }
            
        };
    }

    @Override
    public Diet highProtein() {
        return new Diet() {
            
            private final Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
            
            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                foods.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totCals = 0;
                int totCarbs = 0;
                int totProts = 0;
                for (var entry : dietMap.entrySet()) {
                    var foodMap = foods.get(entry.getKey());
                    for (var foodEntry : foodMap.entrySet()) {
                        Double nutrientCals = foodEntry.getValue() * entry.getValue() / 100;
                        switch(foodEntry.getKey()) {
                            case CARBS: totCarbs += nutrientCals; break;
                            case PROTEINS: totProts += nutrientCals; break;
                            case FAT:
                        }
                        totCals += nutrientCals;
                    }   
                }
                return totCals >= 2000 && totCals <= 2500 && totCarbs <=300 && totProts >= 1300;
            }
            
        };
    }

    @Override
    public Diet balanced() {
        return new Diet() {
            
            private final Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
            
            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                foods.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totCals = 0;
                int totCarbs = 0;
                int totProts = 0;
                int totFats = 0;
                for (var entry : dietMap.entrySet()) {
                    var foodMap = foods.get(entry.getKey());
                    for (var foodEntry : foodMap.entrySet()) {
                        Double nutrientCals = foodEntry.getValue() * entry.getValue() / 100;
                        switch(foodEntry.getKey()) {
                            case CARBS: totCarbs += nutrientCals; break;
                            case FAT: totFats += nutrientCals; break;
                            case PROTEINS: totProts += nutrientCals; break;
                        }
                        totCals += nutrientCals;
                    }   
                }
                return totCals >= 1600 && totCals <= 2000 && totCarbs >= 600
                && totFats >= 400 && totProts >= 600 && totFats + totProts <= 1100;
            }
            
        };
    }

}
