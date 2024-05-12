package gui.mvc;
import java.util.*;

//Class representing an electronic store
//Has an array of products that represent the items the store can sell


public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private List<Product> cart; //tis is for managing the current cart list
    private double cartTotal;
    private int saleCount;
    private int officialUnitSold;
    private double avgRevenuePerSale;
    private  List<Product> topProducts;
    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        cart = new ArrayList<>();
        saleCount = 0;
        officialUnitSold =0;
        topProducts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public Product[] getStock(){return stock; }
    public List<Product> getCart(){return cart;}
    public double getCartTotal(){return cartTotal;}
    public double getRevenue(){return revenue;}
    public void setRevenue(double r){this.revenue = r;}
    public int getSaleCount(){return saleCount;}
    public double getAvgRevenuePerSale(){return  avgRevenuePerSale;}
    public void setCartTotal(double cct){this.cartTotal = cct;    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Better Buy Electronics :)");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }

    //this method is going to handle the adding to prodeucts to cart and updates quantity
    public  void addToCart(Product p){
        for (Product product : cart) {
            if (product.equals(p)) {
                product.increaseQuantity();  //for cart quantity
                setCartTotal(cartTotal + p.getPrice());
                return;
            }
        }
            p.increaseQuantity();
            cart.add(p);
             setCartTotal(cartTotal + p.getPrice());

    }

    //this method will remove prod from cart and update total quantity and price
    public void removeFromCart(Product p) {
        if (p.getCartQuantity() > 1) {
            p.decreaseCartQuantity();
        } else {
            cart.remove(p);
        }
        p.increaseStockQuantity();
        setCartTotal(cartTotal - p.getPrice());
    }

    public void completeSale() {
        for (Product product : cart) {
            product.sellUnits(product.getCartQuantity());
            product.resetCartQuantity();
        }

        saleCount++;
        revenue += cartTotal;
        avgRevenuePerSale = revenue / saleCount;
        cart.clear();
        cartTotal = 0.0;
    }
    public List<Product> getTopSellingProducts() {
        Product[] topProducts = new Product[3];
        for (Product p : stock) {
            if (p != null) {
                for (int i = 0; i < topProducts.length; i++) {
                    if (topProducts[i] == null || p.getSoldQuantity() > topProducts[i].getSoldQuantity()) {
                        System.arraycopy(topProducts, i, topProducts, i + 1, topProducts.length - i - 1);
                        topProducts[i] = p;
                        break;
                    }
                }
            }
        }

        return List.of(topProducts);
    }

}