package a01a.e1;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TreeFactoryImpl implements TreeFactory{

    @Override
    public <E> Tree<E> singleValue(E root) {
        return new Tree<E>() {

            @Override
            public E getRoot() {
                return root;
            }

            @Override
            public List<Tree<E>> getChildren() {
                return Collections.emptyList();
            }

            @Override
            public Set<E> getLeafs() {
                return Set.of(root);
            }

            @Override
            public Set<E> getAll() {
                return Set.of(root);
            }
            
        };
    }

    @Override
    public <E> Tree<E> twoChildren(E root, Tree<E> child1, Tree<E> child2) {
        return new Tree<E>() {

            @Override
            public E getRoot() {
                return root;
            }

            @Override
            public List<Tree<E>> getChildren() {
                return List.of(child1, child2);
            }

            @Override
            public Set<E> getLeafs() {
                final Set<E> elemSet = new HashSet<>(child1.getLeafs());
                elemSet.addAll(child2.getLeafs());
                return elemSet;
            }

            @Override
            public Set<E> getAll() {
                final Set<E> elemSet = new HashSet<>(child1.getAll());
                elemSet.add(root);
                elemSet.addAll(child2.getAll());
                return elemSet;
            }
            
        };
    }

    @Override
    public <E> Tree<E> oneLevel(E root, List<E> children) {
        return new Tree<E>() {

            @Override
            public E getRoot() {
                return root;
            }

            @Override
            public List<Tree<E>> getChildren() {
                return children.stream().map(e -> singleValue(e)).toList();
            }

            @Override
            public Set<E> getLeafs() {
                return Set.copyOf(children);
            }

            @Override
            public Set<E> getAll() {
                final Set<E> elemSet = new HashSet<>(children);
                elemSet.add(root);
                return elemSet;
            }
            
        };
    }

    @Override
    public <E> Tree<E> chain(E root, List<E> list) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
