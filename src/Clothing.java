public class Clothing extends Product {
    private String size;
    private String color;

    // Constructor
    public Clothing(String productId, String name, int availableItemCount, double price, String size, String color) {
        super(productId, name, availableItemCount, price); // Call to the superclass (Product) constructor
        this.size = size;
        this.color = color;
    }


    // Implement the abstract method from Product class
    @Override
    public void printDetails() {
        System.out.println("Clothing - ID: " + getProductId() + ", Name: " + getName() + ", Available: " + getAvailableItemCount() + ", Price: " + getPrice() + ", Size: " + size + ", Color: " + color);
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
