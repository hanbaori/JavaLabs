import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Визначити клас косметика, який складається як мінімум з 5-и полів.
 */
class Cosmetic {

    private final String brand;
    private final String name;
    private final String category;
    private final double price;
    private final int volumeMl;

    public Cosmetic(String brand, String name, String category, double price, int volumeMl) {
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.price = price;
        this.volumeMl = volumeMl;
    }

    public String getBrand() { return brand; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getVolumeMl() { return volumeMl; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %.2f UAH | %d ml",
                brand, name, category, price, volumeMl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cosmetic)) return false;
        Cosmetic cosmetic = (Cosmetic) o;
        return Double.compare(cosmetic.price, price) == 0 &&
                volumeMl == cosmetic.volumeMl &&
                Objects.equals(brand, cosmetic.brand) &&
                Objects.equals(name, cosmetic.name) &&
                Objects.equals(category, cosmetic.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, name, category, price, volumeMl);
    }
}

public class lab3 {

    public static void sortByBrandAscending(Cosmetic[] arr) {
        Arrays.sort(arr, Comparator.comparing(Cosmetic::getBrand));
    }

    public static void sortByPriceDescending(Cosmetic[] arr) {
        Arrays.sort(arr, Comparator.comparing(Cosmetic::getPrice).reversed());
    }

    public static void main(String[] args) {

        Cosmetic[] cosmeticsArray = {
                new Cosmetic("L'Oreal", "Revitalift", "Face Cream", 350.50, 50),
                new Cosmetic("Nivea", "Soft", "Hand Cream", 120.00, 100),
                new Cosmetic("Maybelline", "Lash Sensational", "Mascara", 280.00, 10),
                new Cosmetic("L'Oreal", "Infaillible", "Foundation", 390.00, 30),
                new Cosmetic("Dior", "Sauvage", "Perfume", 3500.00, 100)
        };

        System.out.println("Initial array:");
        printArray(cosmeticsArray);

        sortByBrandAscending(cosmeticsArray);
        System.out.println("\nSorted by brand (ascending):");
        printArray(cosmeticsArray);

        sortByPriceDescending(cosmeticsArray);
        System.out.println("\nSorted by price (descending):");
        printArray(cosmeticsArray);

        Cosmetic target = new Cosmetic("Nivea", "Soft", "Hand Cream", 120.00, 100);
        System.out.println("\nSearching for: " + target.getName());

        boolean found = false;
        for (int i = 0; i < cosmeticsArray.length; i++) {
            if (cosmeticsArray[i].equals(target)) {
                System.out.println("Match found at index " + i);
                System.out.println("Object: " + cosmeticsArray[i]);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No match found.");
        }
    }

    private static void printArray(Cosmetic[] array) {
        for (Cosmetic item : array) {
            System.out.println(item);
        }
    }
}
