package gui.mvc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.util.List;


public class ElectronicStoreView extends Pane{

    ElectronicStore model;

    private Button resetStoreButton;
    private Button addToCartButton;
    private Button removeFromCartButton;
    private Button completeSaleButton;

    private TextField salesTf;
    private TextField revenueTf;
    private TextField msTf;

    private ListView<String> mostPopItemsList;
    private ListView<String> storeStockList;
    private ListView<String> currentCartList;

    private  Label currCartLabel;


    public ElectronicStoreView(ElectronicStore initModel){
        model = initModel;

        //Labels
        Label sSummary = new Label("Store Summary: ");
        sSummary.relocate(56, 8);
        sSummary.setPrefSize(100, 30);
        getChildren().add(sSummary);

        Label storeStockLabel = new Label("Store Stock: ");
        storeStockLabel.relocate(330, 8);
        storeStockLabel.setPrefSize(100, 30);
        getChildren().add(storeStockLabel);

        currCartLabel = new Label("Current Cart: ");
        currCartLabel.relocate(610, 8);
        currCartLabel.setPrefSize(100, 30);
        getChildren().add(currCartLabel);

        Label nSales = new Label("# Sales: ");
        nSales.relocate(40, 33);
        nSales.setPrefSize(100, 30);
        getChildren().add(nSales);

        Label revenueLabel = new Label("Revenue:");
        revenueLabel.relocate(35, 62);
        revenueLabel.setPrefSize(100, 30);
        getChildren().add(revenueLabel);

        Label $SaleLabel = new Label("$ / Sale: ");
        $SaleLabel.relocate(40, 92);
        $SaleLabel.setPrefSize(100, 30);
        getChildren().add($SaleLabel);

        Label mostPopularItemLabel = new Label("Most Popular Items:");
        mostPopularItemLabel.relocate(40, 120);
        mostPopularItemLabel.setPrefSize(140, 30);
        getChildren().add(mostPopularItemLabel);


        //Textfields
        salesTf = new TextField();
        salesTf.relocate(90,35);
        salesTf.setPrefSize(100,25);
        salesTf.setText("0");
        getChildren().add(salesTf);


        revenueTf = new TextField();
        revenueTf.relocate(90,65);
        revenueTf.setPrefSize(100,25);
        revenueTf.setText("$0.00");
        getChildren().add(revenueTf);

        msTf = new TextField();
        msTf.relocate(90,95);
        msTf.setPrefSize(100,25);
        msTf.setText("N/A");
        getChildren().add(msTf);


        //Buttons
        resetStoreButton = new Button("Reset Store");
        resetStoreButton.relocate(40, 355);
        resetStoreButton.setPrefSize(120, 30);
        getChildren().add(resetStoreButton);

        addToCartButton = new Button("Add to Cart");
        addToCartButton.relocate(310, 355);
        addToCartButton.setPrefSize(120, 30);
        addToCartButton.setDisable(true);
        getChildren().add(addToCartButton);

        removeFromCartButton = new Button("Remove from Cart");
        removeFromCartButton.relocate(530, 355);
        removeFromCartButton.setPrefSize(120, 30);
        removeFromCartButton.setDisable(true);
        getChildren().add(removeFromCartButton);

        completeSaleButton = new Button("Complete Sale");
        completeSaleButton.relocate(650, 355);
        completeSaleButton.setPrefSize(120, 30);
        completeSaleButton.setDisable(true);
        getChildren().add(completeSaleButton);



        //Lists
        mostPopItemsList = new ListView<String>();
        mostPopItemsList.relocate(10, 150);
        mostPopItemsList.setPrefSize(180,200);
        getChildren().add(mostPopItemsList);

        storeStockList =  new ListView<String>();
        storeStockList.relocate(200, 35);
        storeStockList.setPrefSize(310,315);
        getChildren().add(storeStockList);

        currentCartList = new ListView<String>();
        currentCartList.relocate(520, 35);
        currentCartList.setPrefSize(260,315);
        getChildren().add(currentCartList);

        update();

    }

    public  Button getResetStoreButton(){return resetStoreButton;}
    public Button getAddToCartButton(){return addToCartButton;}
    public Button getRemoveFromCartButton(){return removeFromCartButton;}
    public Button getCompleteSaleButton(){return completeSaleButton;}
    public ListView<String> getStoreStockList(){return storeStockList;}
    public ListView<String> getCurrentCartList(){return currentCartList;}
    public TextField getSalesTf(){return salesTf;}
    public TextField getRevenueTf(){return revenueTf;}
    public TextField getMsTf(){return msTf;}
    public Label getCurrCartLabel(){return currCartLabel;}
    public void setMostPopItems(ObservableList<String> items) {mostPopItemsList.setItems(items);}


    public void update() {
        ObservableList<String> stockItemlist = FXCollections.observableArrayList();
        if(model.getStock() != null) {
            for (Product p : model.getStock()) {
                if (p != null && p.getStockQuantity() > 0) {
                    stockItemlist.add(p.toString());
                }
            }
        }
        storeStockList.setItems(stockItemlist);

        ObservableList<String> curCartList = FXCollections.observableArrayList();
        List<Product> curCartItem = model.getCart();
        for (Product prod : curCartItem) {
            if (prod != null) {
                curCartList.add(prod.toString());
            }

        }
        currentCartList.setItems(curCartList);

        //updating the text fields
        salesTf.setText(String.valueOf(model.getSaleCount()));
        revenueTf.setText(String.format("$%.2f", model.getRevenue()));
        msTf.setText("N/A");

        int poplist = 0;
        ObservableList<String> mostPopList = FXCollections.observableArrayList();
        for (Product p : model.getStock()) {
            if (poplist < 3) {
                mostPopList.add(p.toString());
                poplist++;
            } else {
                break;
            }
        }
        mostPopItemsList.setItems(mostPopList);

        updateMostPopItem();
        updateCartTotal();
        updateCartLabel();
    }

    private void updateCartTotal() {
        msTf.setText(String.valueOf(model.getAvgRevenuePerSale()));
        salesTf.setText(String.valueOf(model.getSaleCount()));
    }
    private void updateCartLabel(){
        currCartLabel.setText("Current Cart($" + String.format("%.0f", model.getCartTotal()) + ")");
    }
    public void updateMostPopItem(){
        List<Product> topSellingProducts = model.getTopSellingProducts();
        ObservableList<String> mostPopList = FXCollections.observableArrayList();
        for (Product p : topSellingProducts) {
            String productDetails = p.toString();
            mostPopList.add(productDetails);
        }
        mostPopItemsList.setItems(mostPopList);
    }
}







