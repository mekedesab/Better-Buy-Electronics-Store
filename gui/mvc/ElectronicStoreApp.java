package gui.mvc;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {

    ElectronicStore model;
    ElectronicStoreView view;

    public void start(Stage primaryStage) {
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);

        view.getAddToCartButton().setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) {
             handleAddToCartButton();
             view.update();
          }
     });
        view.getStoreStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handleStoreStock();
                view.update();
            }});

        view.getRemoveFromCartButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handleRemoveFromCartButton();
                view.update();
            }
        });

        view.getCompleteSaleButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleCompleteSaleButton();
                view.update();
            }
        });

        view.getResetStoreButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleResetStoreButton();
                view.update();
            }
        });

        Scene scene = new Scene(view, 800, 400);
        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        view.getCurrCartLabel().setText("Current Cart($" + model.getCartTotal() + "):");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        view.update();
    }

    public static void main(String[] args) { launch(args); }

    public void handleStoreStock(){

        int index = view.getStoreStockList().getSelectionModel().getSelectedIndex();
        if (index == -1){
            view.getAddToCartButton().setDisable(true);
        }
        else {
            view.getAddToCartButton().setDisable(false);
        }

    }
    private void handleAddToCartButton(){
        int index = view.getStoreStockList().getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Product[] stockArray = model.getStock();
            if (stockArray != null && index < stockArray.length) {
                Product selectedProduct = stockArray[index];
                if (selectedProduct.getStockQuantity() > 0)
                 model.addToCart(selectedProduct);
                view.update();
                view.getCompleteSaleButton().setDisable(false);
                view.getRemoveFromCartButton().setDisable(false);
                }
             }

    }
    private void handleRemoveFromCartButton(){
        int index = view.getCurrentCartList().getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Product selectedProduct = model.getCart().get(index);
            model.removeFromCart(selectedProduct);
            view.update();
        }

    }

    public void handleCompleteSaleButton(){
        if (!model.getCart().isEmpty()){
            model.completeSale();
            view.updateMostPopItem();
            view.update();
        }
    }

    public void handleResetStoreButton(){
        model = ElectronicStore.createStore();
        model.getCart().clear();

        view.update();

        // view.getStoreStockList().getItems().clear();

        view.getAddToCartButton().setDisable(true);
        view.getCompleteSaleButton().setDisable(true);
        view.getRemoveFromCartButton().setDisable(true);
        view.getSalesTf().setText("0");
        view. getRevenueTf().setText("$0.00");
        view.getMsTf().setText("N/A");

        view.getCurrentCartList().getItems().clear();

    }
}




















