import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class ShoppingCartGUI extends JDialog {
    private JList<String> cartItemsList;
    private DefaultListModel<String> cartListModel;
    private List<String> shoppingCart; // Representing the shopping cart
    private JLabel totalPriceLabel;
    private JButton checkoutButton;
    private SupermarketShoppingManager shoppingManager;


    public ShoppingCartGUI(Frame parentFrame, SupermarketShoppingManager shoppingManager) {
        super(parentFrame, "Shopping Cart", true);
        this.shoppingManager = shoppingManager;
        setSize(300, 400);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());

        cartItemsList = new JList<>();
        shoppingCart = new ArrayList<>();
        cartListModel = new DefaultListModel<>();
        cartItemsList.setModel(cartListModel);
        totalPriceLabel = new JLabel("Total Price: $0.00");
        checkoutButton = new JButton("Checkout");
        add(new JScrollPane(cartItemsList), BorderLayout.CENTER);
        checkoutButton.addActionListener(e -> checkout());
        add(totalPriceLabel, BorderLayout.NORTH);
        add(checkoutButton, BorderLayout.SOUTH);


    }

    public void displayShoppingCart() {
        updateShoppingCartList();
        updateTotalPrice();

        setVisible(true);
    }

    public void addItemToCart(String itemId) {

        // Assume you have a method in SupermarketShoppingManager to get a product by ID
        Product product = shoppingManager.getProductById(itemId);

        // Check if product is not null
        if (product != null) {
            // Add the item to the cart
            shoppingCart.add(itemId);

            // Add product details to the list model
            String productDetails = product.getName() + " - $" + String.format("%.2f", product.getPrice());
            cartListModel.addElement(productDetails);

            // Update the total price here as well
            updateTotalPrice();
        } else {
            JOptionPane.showMessageDialog(this, "Product not found.");
        }
    }




    private void updateShoppingCartList() {
        // Logic to update the shopping cart list
        // For example, you might have a DefaultListModel to update
    }

    private void updateTotalPrice() {
        if (shoppingCart.isEmpty()) {
            totalPriceLabel.setText("Total Price: $0.00");
            return;
        }
        double totalPrice = calculateTotalPrice();


        /* Apply 10% first purchase discount if applicable
        if (currentUser != null && !currentUser.hasPurchased()) {
            totalPrice *= 0.9; // Apply 10% discount for the first time purchase
        }*/

        totalPriceLabel.setText(String.format("Total Price: $%.2f", totalPrice));
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        HashMap<String, Integer> categoryCounts = new HashMap<>();

        for (String itemId : shoppingCart) {
            Product product = shoppingManager.getProductById(itemId);
            totalPrice += product.getPrice();
            String category = product instanceof Electronics ? "Electronics" : "Clothing";
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }

        // Apply 20 % category discount
        for (int count : categoryCounts.values()) {
            if (count >= 3) {
                totalPrice *= 0.8; // Apply 20% discount
                break; // Assuming the discount is applied to the total once
            }
        }
        return totalPrice;
    }



    private void checkout() {
        if (!shoppingCart.isEmpty()) {
            //Process the purchase
            double totalPrice = calculateTotalPrice();
            // Display thank you message
            JOptionPane.showMessageDialog(this, "Thank You For Purchasing");

            // Clear the cart
            shoppingCart.clear();
            cartListModel.clear();



            // Update total price label
            updateTotalPrice();
        } else {
            // Display message if cart is empty
            JOptionPane.showMessageDialog(this, "Your cart is empty.");
        }
    }




}
