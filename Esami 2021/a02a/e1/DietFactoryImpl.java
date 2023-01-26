package a02a.e1;

import java.util.List;
import java.util.function.Predicate;

import a02a.e1.Diet.Nutrient;


public class DietFactoryImpl implements DietFactory{

    @Override
    public Diet standard() {
        return new DietImpl(List.of(
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> true, d -> d >= 1500 && d <= 2000)
        ));
    }

    @Override
    public Diet lowCarb() {
        return new DietImpl(List.of(
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> true, d -> d >= 1000 && d <= 1500),
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> n == Nutrient.CARBS, d -> d <= 300)
        ));
    }

    @Override
    public Diet highProtein() {
        return new DietImpl(List.of(
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> true, d -> d >= 2000 && d <= 2500),
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> n == Nutrient.CARBS, d -> d <= 300),
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> n == Nutrient.PROTEINS, d -> d >= 1300)
        ));
    }

    @Override
    public Diet balanced() {
        return new DietImpl(List.of(
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> true, d -> d >= 1600 && d <= 2000),
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> n == Nutrient.CARBS, d -> d >= 600),
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> n == Nutrient.FAT, d -> d >= 400), 
            new Pair<Predicate<Nutrient>, Predicate<Double>>(n -> n == Nutrient.FAT || n == Nutrient.PROTEINS, d -> d >= 400) 
        ));
    }
    
}
