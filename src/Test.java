// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// class WestminsterShoppingManagerTest {

//     private SupermarketShoppingManager manager;

//     @BeforeEach
//     void setUp() {
//         manager = new SupermarketShoppingManager();
//         manager.addProduct("E123", "Laptop", 10, 999.99, "Dell", 2);
//     }

//     @Test
//     void testLoadProductsFromFile() {
//         SupermarketShoppingManager manager = new SupermarketShoppingManager();
//         manager.loadProductsFromFile("example.txt");
//         assertFalse(manager.getProductList().isEmpty(), "Product list should not be empty after loading products");
//     }

//     @Test
//     void testAddProduct() {
//         // Adding a sample Electronics product
//         manager.addProduct("E123", "Laptop", 10, 999.99, "Dell", 2);

//         // Adding a sample Clothing product
//         manager.addProduct("C456", "T-Shirt", 20, 19.99, "M", Integer.parseInt("Blue"));

//         assertEquals(2, manager.getProductList().size(), "There should be 2 products in the list");
//     }

//     @Test
//     void testRemoveProduct() {

//         manager.addProduct("E123", "Laptop", 10, 999.99, "Dell", 2);
//         assertTrue(manager.removeProduct("E123"), "Product should be removed successfully");
//         assertNull(manager.getProductById("E123"), "Product should be null after being removed");
//     }

//    /* @Test
//     void testSaveProductsToFile() {
//         manager.saveProductsToFile(example.txt);

//         try (BufferedReader reader = new BufferedReader(new FileReader(example.txt))) {
//             // Read the file and assert its contents
//             String line = reader.readLine();
//             assertNotNull(line, "The file should not be empty");
//             assertTrue(line.contains("E123") && line.contains("Laptop"), "File should contain the product details");
//         } catch (IOException e) {
//             fail("IOException should not have been thrown");
//         }
//     }*/



// }
