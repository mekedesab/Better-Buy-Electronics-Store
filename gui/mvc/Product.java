package gui.mvc;

//Base class for all products the store will sell
public abstract class Product {
    private double price;
    private int stockQuantity;
    private int soldQuantity;
    private int cartQuantity; //cart quantity
    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }
    public double getPrice() {
        return price;
    }
    public int getCartQuantity(){return cartQuantity;}

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }

    public void decreaseStockQuantity() {
        if (this.stockQuantity > 0) {
            this.stockQuantity--;
        }
    }
    public void increaseStockQuantity() {
            this.stockQuantity++;
    }
    public void increaseQuantity() {
        this.cartQuantity++;
    }
    public void decreaseCartQuantity() {
        if (this.cartQuantity > 0) {
            this.cartQuantity--;
        }
    }
    public void resetCartQuantity() {
        this.cartQuantity = 0;
    }
}