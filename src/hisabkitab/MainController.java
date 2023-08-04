/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisabkitab;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller classS
 *
 * @author aayus
 */
public class MainController implements Initializable {

    @FXML
    private Label tspend;
    @FXML
    private Label dat;
    @FXML
    private TableColumn<model, String> item;
    @FXML
    private TableColumn<model, String> rup;
    @FXML
    private TableColumn<model, String> via;
    @FXML
    private DatePicker dpick;
    @FXML
    private Button find;
    @FXML
    private Label month;
    @FXML
    private TableColumn<model, String> datetable;
    @FXML
    private TextField Eitem;
    @FXML
    private TextField eprice;
    @FXML
    private ComboBox<String> viabox;
    @FXML
    private DatePicker edate;
    @FXML
    private Button data;

    /**
     * Initializes the controller class.
     */
    ObservableList<model> oblist = FXCollections.observableArrayList();
    ObservableList<String> vialist = FXCollections.observableArrayList("Cash", "Online");
    @FXML
    private TableView<model> table;
    ResultSet rs = null;
    hisabdb db = new hisabdb();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            viabox.setItems(vialist);
            //bybox.setItems(bylist);
            //shpee.setItems(shpelist);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter ltf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            LocalDate noy = LocalDate.now();
            dat.setText(dtf.format(now));
            DateTimeFormatter atf = DateTimeFormatter.ofPattern("MM/yyyy  '-'");
            month.setText(atf.format(now));
            edate.setValue(noy);
            dpick.setValue(noy);
            dtf = DateTimeFormatter.ofPattern("MM_yyyy");
            String month = "a" + dtf.format(noy);
            try {
                show(month);
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tspend.setText(Integer.toString(db.showspend(month)));
            System.out.println("tspend");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void entry(ActionEvent event) throws SQLException, ClassNotFoundException {
        Boolean pre = false;
        LocalDate ld = edate.getValue();

        DateTimeFormatter atf = DateTimeFormatter.ofPattern("MM_yyyy");
        String mon = atf.format(ld);
        mon = "a" + mon;
        System.out.println(mon);
        rs = db.showmonth(mon);
        while (rs.next()) {
            System.out.println(rs.getString("idmonth"));
            if (mon.equals(rs.getString("idmonth"))) {
                System.out.println("idmonth");
                pre = true;
                break;
            }
        }
        if (pre == true) {
            System.out.println("true");
            System.out.println(Integer.parseInt(eprice.getText()));
            db.entry(Eitem.getText(), Integer.parseInt(eprice.getText()), viabox.getValue(), ld.toString(), mon);
        } else {
            System.out.println("false");
            db.insert(mon);
            db.entry(Eitem.getText(), Integer.parseInt(eprice.getText()),  viabox.getValue(), ld.toString(), mon);
        }

        show(mon);
        tspend.setText(Integer.toString(db.showspend(mon)));

    }

    void show(String mon) throws SQLException, ClassNotFoundException {
        oblist.clear();
        rs = db.showmonth(mon);
        month.setText(mon.substring(1));
        Boolean pre = false;
        while (rs.next()) {
            System.out.println(rs.getString("idmonth"));
            if (mon.equals(rs.getString("idmonth"))) {
                System.out.println("idmonth");
                pre = true;
                break;
            }
        }
        if (pre == false) {
            db.insert(mon);
        }
        rs = db.tableview(mon);
        while (rs.next()) {
            oblist.add(new model(rs.getString("item"), rs.getInt("price"), rs.getString("via"), rs.getString("datee")));
            item.setCellValueFactory(new PropertyValueFactory<>("item"));
            datetable.setCellValueFactory(new PropertyValueFactory<>("date"));
            rup.setCellValueFactory(new PropertyValueFactory<>("price"));
            via.setCellValueFactory(new PropertyValueFactory<>("via"));
            table.setItems(oblist);
        }
    }

    @FXML
    private void find(ActionEvent event) throws SQLException, ClassNotFoundException {
        LocalDate noy = dpick.getValue();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_yyyy");
        String month = "a" + dtf.format(noy);
        rs = db.showmonth(month);
        boolean pre = false;
        while (rs.next()) {
            System.out.println(rs.getString("idmonth"));
            if (month.equals(rs.getString("idmonth"))) {
                System.out.println("idmonth");
                pre = true;
                break;
            }
        }
        if (pre == false) {
            hisabdb db = new hisabdb();
            db.insert(month);
        }
        show(month);
        tspend.setText(Integer.toString(db.showspend(month)));
        System.out.println("tspend");
    }
}
