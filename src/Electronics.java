public class Electronics extends Product {
    private String brand;
    private int warranty;

    // Constructor
    public Electronics(String productId, String name, int availableItemCount, double price, String brand, int warranty) {
        super(productId, name, availableItemCount, price); // Call to the superclass (Product) constructor
        this.brand = brand;
        this.warranty = warranty;
    }


    // Implement the abstract method from Product class
    @Override
    public void printDetails() {
        System.out.println("Electronics - ID: " + getProductId() + ", Name: " + getName() + ", Available: " + getAvailableItemCount() + ", Price: " + getPrice() + ", Brand: " + brand + ", Warranty: " + warranty);
    }

    public String getBrand() {
        return brand;
    }

    public int getWarranty() {
        return warranty;
    }
}

