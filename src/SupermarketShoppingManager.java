import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.SwingUtilities;




public class SupermarketShoppingManager implements ShoppingManager {
    private ArrayList<Product> productList;
    private Scanner scanner;

    // Constructor
    public SupermarketShoppingManager() {
        this.productList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        loadProductsFromFile("products.txt");
    }

    // Method to display and handle the console menu
    public void showMenu() {
        while (true) {
            try {
                System.out.println("1. Add a New Product");
                System.out.println("2. Delete a Product");
                System.out.println("3. Print the List of Products");
                System.out.println("4. Save in a File");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addProduct("E123", "Laptop", 10, 999.99, "Dell", 2); // This calls the method to add a new product
                        break;
                    case 2:
                        System.out.print("Enter product ID to delete: ");
                        String productId = scanner.next();
                        removeProduct(productId); // This calls the method to remove a product
                        break;
                    case 3:
                        printAllProducts(); // This calls the method to print the list of products
                        break;
                    case 4:
                        saveProductsToFile(); // This calls the method to save products to a file
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input from the scanner
            }
        }
    }

    // Method to add a product to the list
    @Override
    public void addProduct(String e123, String laptop, int i, double v, String dell, int i1) {
        if (productList.size() >= 50) {
            System.out.println("Maximum product limit reached.");
            return;
        }

        String type = null, productId, name, brand, size , color ;
        int itemCount ;
        double price ;
        Product product = null;

        while (type == null) {
            try {
                System.out.print("Enter product type (Electronics/Clothing): ");
                type = scanner.next();
                if (!"Electronics".equalsIgnoreCase(type) && !"Clothing".equalsIgnoreCase(type)) {
                    throw new IllegalArgumentException("Invalid product type.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                type = null;
            }
        }

        System.out.print("Enter product ID: ");
        productId = scanner.next();

        System.out.print("Enter product name: ");
        name = scanner.next();

        while (true) {
            try {
                System.out.print("Enter number of items available: ");
                itemCount = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        while (true) {
            try {
                System.out.print("Enter product price: ");
                price = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid price.");
                scanner.next(); // Clear the invalid input
            }
        }

        if ("Electronics".equalsIgnoreCase(type)) {
            System.out.print("Enter brand: ");
            brand = scanner.next();

            int warranty;
            while (true) {
                try {
                    System.out.print("Enter warranty period (in years): ");
                    warranty = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer for the warranty period.");
                    scanner.next(); // Clear the invalid input
                }
            }

            product = new Electronics(productId, name, itemCount, price, brand, warranty);
        } else if
            ("Clothing".equalsIgnoreCase(type)) {
            System.out.print("Enter size: ");
            size = scanner.next();

            while (true) {
                try {
                    System.out.print("Enter color: ");
                    color = scanner.next();
                    if (color.matches("[0-9]+")) {
                        throw new IllegalArgumentException("Invalid input. Please enter a valid color.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            product = new Clothing(productId, name, itemCount, price, size, color);
        }

        productList.add(product);
        System.out.println("Product added successfully.");
    }

    public Product getProductById(String id) {
        for (Product product : productList) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null; // or throw an exception if product not found
    }


    // Method to remove a product by ID

    @Override
    public boolean removeProduct(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
                System.out.println("Product deleted: " + product);
                System.out.println("Total products now: " + productList.size());
                return true;
            }
        }
        System.out.println("Product not found.");
        return false;
    }



    // Method to print all products
    @Override
    public void printAllProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products available.");
        } else {
            productList.sort(Comparator.comparing(Product::getProductId));
            for (Product product : productList) {
                product.printDetails();
            }
        }
    }



    //Save Files
    @Override
    public void saveProductsToFile() {
        String filename = "products.txt"; // Fixed filename
        try (FileWriter writer = new FileWriter(filename)) {
            for (Product product : productList) {
                String productType = product instanceof Electronics ? "Electronics" : "Clothing";
                String additionalInfo = product instanceof Electronics ?
                        ((Electronics) product).getBrand() + "," + ((Electronics) product).getWarranty() :
                        ((Clothing) product).getSize() + "," + ((Clothing) product).getColor();
                writer.write(productType + "," + product.getProductId() + "," + product.getName() + "," +
                        product.getAvailableItemCount() + "," + product.getPrice() + "," +
                        additionalInfo + "\n");
            }
            System.out.println("Products saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }


    // load products
    protected void loadProductsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("Electronics")) {
                    Electronics product = new Electronics(parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), parts[5], Integer.parseInt(parts[6]));
                    productList.add(product);
                } else if (parts[0].equals("Clothing")) {
                    Clothing product = new Clothing(parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), parts[5], parts[6]);
                    productList.add(product);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }






    // Method to get the list of all products
    public ArrayList<Product> getProductList() {
        return productList;
    }



    // Main method
    public static void main(String[] args) {
        SupermarketShoppingManager manager = new SupermarketShoppingManager();
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(null, manager);
        manager.showMenu();
        SwingUtilities.invokeLater(() -> new GUI(manager, shoppingCartGUI).setVisible(true));
    }
}
