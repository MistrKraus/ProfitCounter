import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public VBox commodityVbox;
    public Label fullYield;
    public Label fullPercentYield;
    private ObservableList<Node> commodities = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commodities = commodityVbox.getChildren();

        commodityVbox.getChildren().addAll(commodities);

        addCom(null);
    }

    public void addCom(MouseEvent mouseEvent) {
        CommodityForm cf = new CommodityForm();
        cf.getExitBtn().setOnAction(event -> {
            for (int i = 0; i < commodities.size(); i++)
                if (cf.equals(commodities.get(i)))
                    commodities.remove(i);
        });

        commodities.add(cf);
    }

    public void saveAll(MouseEvent mouseEvent) {

    }
}
