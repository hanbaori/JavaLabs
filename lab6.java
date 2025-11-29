import java.util.*;

abstract class Gem {
    private final String name;
    private final double weight;
    private final double price;
    private final double transparency;

    public Gem(String name, double weight, double price, double transparency) {
        if (weight <= 0 || price < 0 || transparency < 0 || transparency > 100)
            throw new IllegalArgumentException("Invalid gem parameters");
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.transparency = transparency;
    }

    public String getName() { return name; }
    public double getWeight() { return weight; }
    public double getPrice() { return price; }
    public double getTransparency() { return transparency; }

    @Override
    public String toString() {
        return String.format("%s (weight: %.2f ct, price: %.2f $, transparency: %.1f%%)",
                name, weight, price, transparency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gem gem = (Gem) o;
        return Double.compare(gem.weight, weight) == 0 &&
               Double.compare(gem.price, price) == 0 &&
               Double.compare(gem.transparency, transparency) == 0 &&
               name.equals(gem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, price, transparency);
    }
}

class PreciousGem extends Gem {
    public PreciousGem(String name, double weight, double price, double transparency) {
        super(name, weight, price, transparency);
    }
}

class SemiPreciousGem extends Gem {
    public SemiPreciousGem(String name, double weight, double price, double transparency) {
        super(name, weight, price, transparency);
    }
}

class SyntheticGem extends Gem {
    public SyntheticGem(String name, double weight, double price, double transparency) {
        super(name, weight, price, transparency);
    }
}

class CustomSet<T> implements Set<T> {
    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomSet() {
        head = tail = null;
        size = 0;
    }

    public CustomSet(T item) {
        this();
        add(item);
    }

    public CustomSet(Collection<? extends T> collection) {
        this();
        addAll(collection);
    }

    @Override
    public int size() { return size; }
    @Override
    public boolean isEmpty() { return size == 0; }

    private Node<T> findNode(Object o) {
        if (o == null) return null;
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(o)) return current;
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean contains(Object o) { return findNode(o) != null; }

    @Override
    public Iterator<T> iterator() { return new SetIterator(); }

    private class SetIterator implements Iterator<T> {
        private Node<T> current = head;
        private Node<T> lastReturned = null;

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastReturned = current;
            T data = current.data;
            current = current.next;
            return data;
        }

        @Override
        public void remove() {
            if (lastReturned == null) throw new IllegalStateException();
            Node<T> nodeToRemove = lastReturned;
            lastReturned = null;
            CustomSet.this.remove(nodeToRemove.data);
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (T item : this) array[i++] = item;
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        int length = size;
        if (a.length < length) {
            a = (T1[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), length);
        }
        int i = 0;
        for (T item : this) a[i++] = (T1) item;
        if (a.length > length) a[length] = null;
        return a;
    }

    @Override
    public boolean add(T element) {
        if (element == null) return false;
        if (contains(element)) return false;
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        Node<T> nodeToRemove = findNode(o);
        if (nodeToRemove == null) return false;
        if (nodeToRemove == head && nodeToRemove == tail) {
            head = tail = null;
        } else if (nodeToRemove == head) {
            head = nodeToRemove.next;
            if (head != null) head.prev = null;
        } else if (nodeToRemove == tail) {
            tail = nodeToRemove.prev;
            if (tail != null) tail.next = null;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        nodeToRemove.prev = nodeToRemove.next = null;
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) if (!contains(item)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T item : c) if (add(item)) modified = true;
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object item : c) if (remove(item)) modified = true;
        return modified;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append("\n");
            current = current.next;
        }
        return sb.toString();
    }
}

public class lab6 {
    public static void main(String[] args) {
        Gem diamond = new PreciousGem("Diamond", 1.5, 5000, 95);
        Gem ruby = new PreciousGem("Ruby", 2.0, 1500, 85);
        Gem amethyst = new SemiPreciousGem("Amethyst", 3.0, 300, 70);
        Gem cz = new SyntheticGem("Cubic Zirconia", 1.0, 50, 90);
        Gem topaz = new SemiPreciousGem("Topaz", 2.5, 200, 80);

        CustomSet<Gem> set1 = new CustomSet<>();
        set1.add(diamond);
        set1.add(ruby);
        set1.add(diamond);
        System.out.println("Set 1:");
        System.out.println(set1);

        CustomSet<Gem> set2 = new CustomSet<>(amethyst);
        set2.add(cz);
        System.out.println("\nSet 2:");
        System.out.println(set2);

        List<Gem> initialGems = Arrays.asList(topaz, ruby, cz, topaz);
        CustomSet<Gem> set3 = new CustomSet<>(initialGems);
        System.out.println("\nSet 3:");
        System.out.println(set3);

        System.out.println("\nTesting Set methods");
        System.out.println("Set 3 contains Ruby?: " + set3.contains(ruby));
        System.out.println("Set 3 contains Diamond?: " + set3.contains(diamond));

        set3.remove(cz);
        System.out.println("Set 3 after removing Cubic Zirconia:");
        System.out.println(set3);

        Collection<Gem> newGems = Arrays.asList(diamond, amethyst);
        set3.addAll(newGems);
        System.out.println("Set 3 after addAll:");
        System.out.println(set3);

        set3.clear();
        System.out.println("Set 3 after clear. Empty?: " + set3.isEmpty());
    }
}
