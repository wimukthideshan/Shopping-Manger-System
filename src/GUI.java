import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class GUI extends JFrame {
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTextArea productDetailsArea;
    private JButton shoppingCartButton;
    private JButton addToCartButton;
    private ShoppingCartGUI shoppingCartGUI;
    private SupermarketShoppingManager shoppingManager;
    private User currentUser;

    public GUI(SupermarketShoppingManager manager, ShoppingCartGUI shoppingCartGUI) {
        this.shoppingManager = manager;
        this.shoppingCartGUI = shoppingCartGUI;
        this.currentUser = null; // no is logged initially
        setTitle("Supermarket Shopping Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initializeComponents();
        populateProductTable(); // This will populate the table with the products
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        //to add selected product into the product details area
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String id = productTable.getValueAt(selectedRow, 0).toString();
                    String name = productTable.getValueAt(selectedRow, 1).toString();
                    String category = productTable.getValueAt(selectedRow, 2).toString();
                    String price = productTable.getValueAt(selectedRow, 3).toString();
                    String info = productTable.getValueAt(selectedRow, 4).toString();

                    String details = "Product Id: " + id + "\n"
                            + "Category: " + category + "\n"
                            + "Name: " + name + "\n"
                            + "Price: " + price + "\n"
                            + "Info: " + info;

                    productDetailsArea.setText(details);
                }
            }
        });
    }

    private void initializeComponents() {
        // North panel with ComboBox and Button
        JPanel northPanel = new JPanel(new BorderLayout());
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        northPanel.add(productTypeComboBox, BorderLayout.CENTER);

        productTypeComboBox.addActionListener(e -> {
            String selectedCategory = (String) productTypeComboBox.getSelectedItem();
            updateProductTable(selectedCategory);
        });

        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(this::onShoppingCartButtonClick);
        northPanel.add(shoppingCartButton, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);

        createProductTable();
        add(new JScrollPane(productTable), BorderLayout.CENTER);


        // Product Details Area setup
        JPanel detailsPanel = new JPanel(new BorderLayout());
        productDetailsArea = new JTextArea(10,30);
        productDetailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(productDetailsArea);
        detailsScrollPane.setPreferredSize(new Dimension(200, 100));
        detailsPanel.add(new JScrollPane(productDetailsArea), BorderLayout.CENTER);

        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(this::onAddToCartButtonClick);
        detailsPanel.add(addToCartButton, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.SOUTH);
    }

    private void createProductTable() {
        String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        productTableModel = new DefaultTableModel(columnNames, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Prevent table cells from being edited
                return false;
            }
        };
        productTable = new JTable(productTableModel);
    }

    private void onShoppingCartButtonClick(ActionEvent e) {
        if (currentUser == null) {
            // User is not logged in, prompt for registration
            int registrationOption = handleUserRegistration();

            if (registrationOption == JOptionPane.OK_OPTION) {
                // User registered successfully or logged in, show the shopping cart
                shoppingCartGUI.displayShoppingCart();
                handleUserRegistration();
            } else {
                // User canceled registration or login
                JOptionPane.showMessageDialog(this, "Shopping cart access requires registration or login.");
            }
        } if (currentUser != null){
            // User is logged in, show the shopping cart
            shoppingCartGUI.displayShoppingCart();

        }
    }



    private int handleUserRegistration() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "User Registration", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (User.isUserRegistered(username)) {
                JOptionPane.showMessageDialog(this, "User already exists. Please log in.");
            } else {
                // User does not exist, create a new user
                currentUser = new User(username, password);
                JOptionPane.showMessageDialog(this, "Registration successful. You can now access the shopping cart.");
            }
        }
        return option;
    }




    //Add to shopping cart button
    private void onAddToCartButtonClick(ActionEvent e) {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = (String) productTableModel.getValueAt(selectedRow, 0);
            shoppingCartGUI.addItemToCart(productId); // additem to the cart
            JOptionPane.showMessageDialog(this, "Added product " + productId + " to cart.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to add.");
        }
    }

    public void populateProductTable() {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0); // Clear existing table content
        for (Product product : shoppingManager.getProductList()) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product instanceof Electronics ? "Electronics" : "Clothing",
                    String.format("%.2f", product.getPrice()),
                    product instanceof Electronics ? ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarranty() + " years" : ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor()
            });
        }
    }

    // Method to update the product table based on the selected category
    private void updateProductTable(String category) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0); // Clear existing table content

        for (Product product : shoppingManager.getProductList()) {
            boolean matchesCategory = category.equals("All") ||
                    (category.equals("Electronics") && product instanceof Electronics) ||
                    (category.equals("Clothing") && product instanceof Clothing);

            if (matchesCategory) {
                model.addRow(new Object[]{
                        product.getProductId(),
                        product.getName(),
                        product instanceof Electronics ? "Electronics" : "Clothing",
                        String.format("%.2f", product.getPrice()),
                        product instanceof Electronics ? ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarranty() + " years" : ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor()
                });
            }
        }
    }









    public static void main(String[] args) {
        SupermarketShoppingManager manager = new SupermarketShoppingManager();
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(null, manager);
        // create the GUI and make it visible
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI(manager, shoppingCartGUI);
            gui.setVisible(true);
        });
    }
}
