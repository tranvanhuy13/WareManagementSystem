import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Product {
    private String name;
    private String sku;
    private int quantity;
    private String location;

    public Product(String name, String sku, int quantity, String location) {
        this.name = name;
        this.sku = sku;
        this.quantity = quantity;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
// Getters and setters...

    @Override
    public String toString() {
        return "Name: " + name + ", SKU: " + sku + ", Quantity: " + quantity + ", Location: " + location;
    }
}

class Supplier {
    private String name;
    private String contactInfo;
    private ArrayList<String> orderHistory;

    public Supplier(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.orderHistory = new ArrayList<>();
    }

    // Getters and setters...
    public void addOrder(String order) {
        orderHistory.add(order);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ArrayList<String> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<String> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Contact: " + contactInfo;
    }
}

class Warehouse {
    private Map<String, Product> products;
    private Map<String, Supplier> suppliers;

    public Warehouse() {
        products = new HashMap<>();
        suppliers = new HashMap<>();
    }

    // Product management
    public void addProduct(Product product) {
        products.put(product.getSku(), product);
    }

    public void removeProduct(String sku) {
        products.remove(sku);
    }

    public Product getProduct(String sku) {
        return products.get(sku);
    }

    public void updateProductQuantity(String sku, int quantity) {
        Product product = products.get(sku);
        if (product != null) {
            product.setQuantity(quantity);
        }
    }

    // Supplier management
    public void addSupplier(Supplier supplier) {
        suppliers.put(supplier.getName(), supplier);
    }

    public Supplier getSupplier(String name) {
        return suppliers.get(name);
    }

    public void addSupplierOrder(String name, String order) {
        Supplier supplier = suppliers.get(name);
        if (supplier != null) {
            supplier.addOrder(order);
        }
    }

    // Search for products
    public Product searchProduct(String name) {
        for (Product product : products.values()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    // Alerts for low inventory
    public ArrayList<Product> getLowInventoryProducts(int threshold) {
        ArrayList<Product> lowInventoryProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getQuantity() < threshold) {
                lowInventoryProducts.add(product);
            }
        }
        return lowInventoryProducts;
    }

    // Generate reports
    public String generateInventoryReport() {
        StringBuilder report = new StringBuilder();
        for (Product product : products.values()) {
            report.append(product).append("\n");
        }
        return report.toString();
    }

    public String generateSupplierReport() {
        StringBuilder report = new StringBuilder();
        for (Supplier supplier : suppliers.values()) {
            report.append(supplier).append("\n");
        }
        return report.toString();
    }
}

public class WarehouseManagementSystem extends JFrame {
    private Warehouse warehouse;
    private JTextArea displayArea;
    private JTextField nameField, skuField, quantityField, locationField, supplierNameField, contactInfoField;

    public WarehouseManagementSystem() {
        warehouse = new Warehouse();

        setTitle("Warehouse Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("SKU:"));
        skuField = new JTextField();
        inputPanel.add(skuField);
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        inputPanel.add(locationField);
        JButton addButton = new JButton("Add Product");
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);

        // Supplier panel
        JPanel supplierPanel = new JPanel(new GridLayout(3, 2));
        supplierPanel.add(new JLabel("Supplier Name:"));
        supplierNameField = new JTextField();
        supplierPanel.add(supplierNameField);
        supplierPanel.add(new JLabel("Contact Info:"));
        contactInfoField = new JTextField();
        supplierPanel.add(contactInfoField);
        JButton addSupplierButton = new JButton("Add Supplier");
        supplierPanel.add(addSupplierButton);
        add(supplierPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String sku = skuField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String location = locationField.getText();

                Product product = new Product(name, sku, quantity, location);
                warehouse.addProduct(product);
                displayArea.append("Added: " + product + "\n");

                // Clear fields
                nameField.setText("");
                skuField.setText("");
                quantityField.setText("");
                locationField.setText("");
            }
        });

        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = supplierNameField.getText();
                String contactInfo = contactInfoField.getText();

                Supplier supplier = new Supplier(name, contactInfo);
                warehouse.addSupplier(supplier);
                displayArea.append("Added: " + supplier + "\n");

                // Clear fields
                supplierNameField.setText("");
                contactInfoField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WarehouseManagementSystem().setVisible(true);
            }
        });
    }
}
