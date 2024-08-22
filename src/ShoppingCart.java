import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> products;

    // Constructor
    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    // Method to add a product
    public void addProduct(Product product) {
        products.add(product);
    }

    // Method to remove a product
    public boolean removeProduct(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    // Method to calculate total cost
    public double calculateTotalCost() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    // Method to print cart details
    public void printCartDetails() {
        for (Product product : products) {
            product.printDetails();
        }
    }
}
