import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

import java.sql.SQLException;

public class CommodityForm extends GridPane {//BorderPane {

    private final int id;
    private static int number;

    private String commodity;

    private double purchasePrice;
    private double amount;
    private double currentPrice;
    private double yield;
    private double yieldPercent;

    //private Scene scene;
//    private final BorderPane bP = new BorderPane();
    private TextField purchasePriceTxt = new TextField();
    private TextField amountTxt = new TextField();
//    private TextField fullPurchasePrice = new TextField();
    private TextField currentPriceTxt = new TextField();
    private Label yieldTxt = new Label("+/-");
    private Label yieldPercentTxt = new Label(" %");
    private Button saveBtn = new Button("Save");
    private Button exitBtn = new Button("×");

    public CommodityForm() {
        id = number++;

        setInputProcessing();

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(5);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(65);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);

        this.getColumnConstraints().addAll(col1, col2, col3);

        this.addColumn(0, createCenter());
        this.addColumn(1, createRight());
        this.addColumn(2, createRightRight());

        this.setPadding(new Insets(5));
    }

    private void countYield() {
        if (amount == 0 || (purchasePrice + currentPrice) == 0)
            return;

        yield = (currentPrice - purchasePrice) * amount;
        yieldPercent = yield / (purchasePrice * amount * 0.01);

        if (yield < 0) {
            yieldTxt.setTextFill(Color.RED);
            yieldPercentTxt.setTextFill(Color.RED);
        } else {
            if (yield > 0) {
                yieldTxt.setTextFill(Color.GREEN);
                yieldPercentTxt.setTextFill(Color.GREEN);

                yieldTxt.setText("+" + Double.toString(yield));
                yieldPercentTxt.setText("+" + Double.toString(yieldPercent) + "%");

                return;
            } else {
                yieldTxt.setTextFill(Color.BLACK);
                yieldPercentTxt.setTextFill(Color.BLACK);
            }
        }

        yieldTxt.setText(Double.toString(yield));
        yieldPercentTxt.setText(Double.toString(yieldPercent) + "%");
    }

    private void setInputProcessing() {


        purchasePriceTxt.setAlignment(Pos.CENTER_RIGHT);
        amountTxt.setAlignment(Pos.CENTER_RIGHT);
        currentPriceTxt.setAlignment(Pos.CENTER_RIGHT);

        purchasePriceTxt.setTextFormatter(new TextFormatter<>(new StringConverter<Double>() {
            @Override
            public Double fromString(String string) {
                //System.out.println("FROM");
                double x = Double.parseDouble(string);

                if (x < 0)
                    return 0.0;

                return x;
            }

            @Override
            public String toString(Double object) {
                //System.out.println("TO");
                if (object == null)
                    return "0.0";

                purchasePrice = object;

                return object.toString();
            }
        }));
        amountTxt.setTextFormatter(new TextFormatter<>(new StringConverter<Double>() {
            @Override
            public Double fromString(String string) {
                //System.out.println("FROM");
                double x = Double.parseDouble(string);

                if (x < 0)
                    return 0.0;

                return x;
            }

            @Override
            public String toString(Double object) {
                //System.out.println("TO");
                if (object == null)
                    return "0.0";

                amount = object;

                return object.toString();
            }
        }));
        currentPriceTxt.setTextFormatter(new TextFormatter<>(new StringConverter<Double>() {
            @Override
            public Double fromString(String string) {
                //System.out.println("FROM");
                double x = Double.parseDouble(string);

                if (x < 0)
                    return 0.0;

                return x;
            }

            @Override
            public String toString(Double object) {
                //System.out.println("TO");
                if (object == null)
                    return "0.0";

                currentPrice = object;

                return object.toString();
            }
        }));

        purchasePriceTxt.setOnKeyReleased(event -> countYield());
        amountTxt.setOnKeyReleased(event -> countYield());
        currentPriceTxt.setOnKeyReleased(event -> countYield());
    }

    private VBox createRightRight() {
        VBox vBox = new VBox(exitBtn);
        vBox.setAlignment(Pos.TOP_CENTER);

        return vBox;
    }

    private VBox createCenter() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
//        vBox.setMinWidth(this.getWidth() * 0.8);
        HBox hBox = new HBox();

        TextField commodityText = new TextField();
        commodityText.setPromptText("Commodity");
        commodityText.textProperty().addListener((observable, oldValue, newValue) -> commodity = newValue);

        Region region = new Region();
        HBox.setHgrow(region, Priority.SOMETIMES);

        hBox.getChildren().addAll(commodityText, region, saveBtn);
        saveBtn.setOnAction(event -> {
            try {
                Db.saveCommodity(commodity, purchasePrice, amount, currentPrice, yield, yieldPercent);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        GridPane gP = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow( Priority.ALWAYS );
        gP.getColumnConstraints().addAll(new ColumnConstraints(), col1, new ColumnConstraints());
        //HBox.setHgrow(gP, Priority.ALWAYS);
        gP.setHgap(10);
        gP.setVgap(5);

        Label lbl = new Label("Purchase price");
        lbl.setTooltip(new Tooltip("Purchase price for 'one'"));

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);

        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);

        Region r3 = new Region();
        HBox.setHgrow(r3, Priority.ALWAYS);

        gP.add(lbl, 0, 0);
        gP.add(r1, 1, 0);
        gP.add(purchasePriceTxt, 2, 0);

        gP.add(new Label("Amount"), 0, 1);
        gP.add(r2, 1, 1);
        gP.add(amountTxt, 2, 1);

        gP.add(new Label("Current price"), 0, 2);
        gP.add(r3, 1, 2);
        gP.add(currentPriceTxt, 2, 2);

        //gP.getColumnConstraints().get(1).setHgrow(Priority.ALWAYS);

        vBox.getChildren().addAll(hBox, gP);

        return vBox;
    }

    private VBox createRight() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0, 0, 0 ,10));
//        vBox.setMinWidth(this.getWidth() * 0.2);
        vBox.setAlignment(Pos.CENTER);

        yieldTxt.setFont(new Font(20));

        vBox.getChildren().addAll(yieldTxt, yieldPercentTxt);

        return vBox;
    }

    public String getCommodity() {
        return commodity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getAmount() {
        return amount;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getYield() {
        return yield;
    }

    public Label getYieldTxt() {
        return yieldTxt;
    }

    public double getFullPurchasePrice() {
        return purchasePrice * amount;
    }

    public double getFullCurrentPrice() {
        return currentPrice * amount;
    }

    public Button getExitBtn() {
        return exitBtn;
    }
}
