import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

class Necklace {
    private final List<Gem> gems;

    public Necklace() {
        gems = new ArrayList<>();
    }

    public void addGem(Gem gem) {
        if (gem == null)
            throw new IllegalArgumentException("Cannot add a null gem");
        gems.add(gem);
    }

    public double getTotalWeight() {
        return gems.stream().mapToDouble(Gem::getWeight).sum();
    }

    public double getTotalPrice() {
        return gems.stream().mapToDouble(Gem::getPrice).sum();
    }

    public void sortByPriceDescending() {
        gems.sort(Comparator.comparingDouble(Gem::getPrice).reversed());
    }

    public List<Gem> findByTransparency(double min, double max) {
        if (min > max || min < 0 || max > 100)
            throw new IllegalArgumentException("Invalid transparency range");
        List<Gem> result = new ArrayList<>();
        for (Gem g : gems) {
            if (g.getTransparency() >= min && g.getTransparency() <= max)
                result.add(g);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Gem g : gems) sb.append(g).append("\n");
        return sb.toString();
    }
}

public class lab5 {
    public static void main(String[] args) {
        try {
            Necklace necklace = new Necklace();
            necklace.addGem(new PreciousGem("Diamond", 1.5, 5000, 95));
            necklace.addGem(new SemiPreciousGem("Amethyst", 3.0, 300, 70));
            necklace.addGem(new PreciousGem("Ruby", 2.0, 1500, 85));
            necklace.addGem(new SyntheticGem("Cubic Zirconia", 1.0, 50, 90));
            necklace.addGem(new SemiPreciousGem("Topaz", 2.5, 200, 80));

            System.out.println("All gems in the necklace:");
            System.out.println(necklace);

            System.out.printf("Total weight: %.2f ct\n", necklace.getTotalWeight());
            System.out.printf("Total price: %.2f $\n", necklace.getTotalPrice());

            necklace.sortByPriceDescending();
            System.out.println("\nGems sorted by price:");
            System.out.println(necklace);

            double minTransparency = 80;
            double maxTransparency = 100;
            List<Gem> filtered = necklace.findByTransparency(minTransparency, maxTransparency);
            System.out.println("\nGems with transparency from " + minTransparency + "% to " + maxTransparency + "%:");
            for (Gem g : filtered) System.out.println(g);

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
