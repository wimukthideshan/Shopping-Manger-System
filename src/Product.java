public abstract class Product {
    private String productId;
    private String name;
    private int availableItemCount;
    private double price;

    // Constructor
    public Product(String productId, String name, int availableItemCount, double price) {
        this.productId = productId;
        this.name = name;
        this.availableItemCount = availableItemCount;
        this.price = price;
    }

    public void decreaseItemCount(int amount) {
        if (availableItemCount >= amount) {
            availableItemCount -= amount;
        } else {
            throw new IllegalStateException("Insufficient items available");
        }
    }


    // Getters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getAvailableItemCount() {
        return availableItemCount;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailableItemCount(int availableItemCount) {
        this.availableItemCount = availableItemCount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Abstract method to be implemented in subclasses
    public abstract void printDetails();

}
