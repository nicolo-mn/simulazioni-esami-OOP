package a03b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoublePredicate;

public class LensFactoryImpl implements LensFactory{

    @Override
    public <E> Lens<List<E>, E> indexer(int i) {
        return new Lens<List<E>,E>() {

            @Override
            public E get(List<E> s) {
                Lens<List<List<E>>, E> mod = doubleIndexer(0, i);
                return mod.get(List.of(s));
            }

            @Override
            public List<E> set(E a, List<E> s) {
                Lens<List<List<E>>, E> mod = doubleIndexer(0, i);
                return mod.set(a, List.of(s)).get(0);
            }
            
        };
    }

    @Override
    public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
        return new Lens<List<List<E>>,E>() {
            
            
            @Override
            public E get(List<List<E>> s) {
                return s.get(i).get(j);
            }

            @Override
            public List<List<E>> set(E a, List<List<E>> s) {
                List<List<E>> modified = new ArrayList<>();
                for (int i = 0; i < s.size(); i++) {
                    modified.add(new ArrayList<>(s.get(i)));
                }
                modified.get(i).set(j, a);
                return modified;
            }
            
        };
    }

    @Override
    public <A, B> Lens<Pair<A, B>, A> left() {
        return new Lens<Pair<A,B>,A>() {

            @Override
            public A get(Pair<A, B> s) {
                return s.get1();
            }

            @Override
            public Pair<A, B> set(A a, Pair<A, B> s) {
                return new Pair<>(a, s.get2());
            }
            
        };
    }

    @Override
    public <A, B> Lens<Pair<A, B>, B> right() {
        return new Lens<Pair<A,B>,B>() {

            @Override
            public B get(Pair<A, B> s) {
                return s.get2();
            }

            @Override
            public Pair<A, B> set(B a, Pair<A, B> s) {
                return new Pair<>(s.get1(), a);
            }
            
        };
    }

    @Override
    public <A, B, C> Lens<List<Pair<A, Pair<B, C>>>, C> rightRightAtPos(int i) {
        return new Lens<List<Pair<A,Pair<B,C>>>,C>() {

            @Override
            public C get(List<Pair<A, Pair<B, C>>> s) {
                return s.get(i).get2().get2();
            }

            @Override
            public List<Pair<A, Pair<B, C>>> set(C a, List<Pair<A, Pair<B, C>>> s) {
                List<Pair<A, Pair<B, C>>> newList = new ArrayList<>(s);
                newList.set(i, new Pair<>(s.get(i).get1(), new Pair<>(s.get(i).get2().get1(), a)));
                return newList;
            }
            
        };
    }

}
