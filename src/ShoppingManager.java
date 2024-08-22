public interface ShoppingManager {
    void addProduct(String e123, String laptop, int i, double v, String dell, int i1);
    boolean removeProduct(String productId);
    void printAllProducts();
    void saveProductsToFile();
}
