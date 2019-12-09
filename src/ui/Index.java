package ui;

import astar.Noeud;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Index {

    private static String LB_VALUE = "(Remplissez les champs à gauche et cliquez sur \"Calculer\")" ;
    private static int NBR_SOLUTION = 1 ;
    private static int NBR_DEFAUT = 1 ;

    private Thread threadCalcul = null ;

    @FXML
    private TextField txtNbrSolution;
    @FXML
    private TextField txtNbrTarget;
    @FXML
    private VBox vboxDataList;
    @FXML
    private Label lbSolution;
    @FXML
    private ProgressBar pgbCalcul;
    @FXML
    private Button btNew;
    @FXML
    private Button btGo;
    @FXML
    private Button btStop;
    @FXML
    private TextArea txtSolution;

    @FXML
    public void initialize() {

//        this.createNew();
    }

    @FXML
    public void createNew() {

        txtNbrSolution.setText(String.valueOf(NBR_SOLUTION));
        txtNbrTarget.setText(String.valueOf(NBR_DEFAUT));
        vboxDataList.getChildren().forEach(e -> ((TextField)e).setText(String.valueOf(NBR_SOLUTION)));
        txtSolution.setText("");

    }

    @FXML
    public void calculer() {

        List list = vboxDataList.getChildren() ;

        try{

            int nbr_data = list.size(),
                    data[] = new int[nbr_data],
                    nbr_target = Integer.valueOf(txtNbrTarget.getText()),
                    nbr_solution = Integer.valueOf(txtNbrSolution.getText());

            this.btGo.setDisable(true);
            this.btNew.setDisable(true);
            this.btStop.setDisable(false);

            for (int i = 0; i < list.size(); i++) {
                data[i] = Integer.valueOf(((TextField)list.get(i)).getText()) ;
            }

            txtSolution.setText("");

            Noeud noeud = new Noeud(data, nbr_target) ;
            Platform.runLater(()-> {
                pgbCalcul.setDisable(false);
                pgbCalcul.setVisible(true);
            });

            this.threadCalcul = new Thread(()->{

                noeud.lancerAStar(nbr_solution, (liste, inc) -> {

                    Platform.runLater(()-> {
                        addTextSolution("Solution " + String.valueOf(inc + 1).concat(" : "));
                        liste.forEach(e -> addTextSolution("\t" + e.getAntecedent().toString()) );
//                        liste.forEach(e -> addTextSolution("\t" + e.toString()) );
                    });

                } ) ;
                Platform.runLater(()->{
                    pgbCalcul.setDisable(true);
                    pgbCalcul.setVisible(false);
                });
                btNew.setDisable(false);
                btGo.setDisable(false);
                btStop.setDisable(true);

            });

            this.threadCalcul.start() ;

        }catch (NumberFormatException numberExc) {

            MsgBox.show(numberExc.getMessage());

        }


    }

    private void addTextSolution(String text) {

        this.txtSolution.setText(this.txtSolution.getText().concat("\n").concat(text));

    }

    @FXML
    public void stop() {

        if (this.threadCalcul != null) this.threadCalcul.stop();
        btNew.setDisable(false);
        btGo.setDisable(false);
        btStop.setDisable(true);
        pgbCalcul.setDisable(true);
        pgbCalcul.setVisible(false);

    }


}
