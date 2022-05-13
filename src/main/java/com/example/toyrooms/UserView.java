package com.example.toyrooms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserView implements Initializable {

    @FXML
    private Button change_role;
    @FXML
    private Button sort_toys;
    @FXML
    private Button find_toys;

    @FXML
    private ScrollPane rooms;
    @FXML
    private VBox vrooms;
    @FXML
    private ScrollPane toys;
    @FXML
    private VBox vtoys;

    @FXML
    private Pane pn_find_toys;
    @FXML
    private RadioButton car;
    @FXML
    private RadioButton ball;
    @FXML
    private RadioButton doll;
    @FXML
    private RadioButton cube;
    @FXML
    private RadioButton small;
    @FXML
    private RadioButton medium;
    @FXML
    private RadioButton big;
    @FXML
    private Button btn_find;
    @FXML
    private ScrollPane found_toys;
    @FXML
    private VBox ftoys;

    static Centre centre;

    Room chosenRoom = new Room(null, 0, 0);
    Toy chosenToy = new Toy(null, null, 0);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        centre = new Centre();
        try {
            readFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        showRooms(centre);
        showToys(chosenRoom);

        change_role.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    writeToFile();
                    Stage stage = (Stage) change_role.getScene().getWindow();
                    stage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 300, 210);
                    stage.setTitle("Toy rooms");
                    stage.setScene(scene);
                    stage.show();

                    AdminView controller = fxmlLoader.getController();
                    stage.setOnCloseRequest(controller.getCloseEventHandler());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == sort_toys) {
            pn_find_toys.setVisible(false);
            sortToys();
            showRooms(centre);
            showToys(chosenRoom);
        }
        if (actionEvent.getSource() == find_toys) {
            pn_find_toys.setVisible(true);
            findToys();
        }
    }

    public void showRooms(Centre centre){
        vrooms.getChildren().clear();
        if (centre.getRooms().size() == 0) {
            Label noRooms = new Label("Нет комнат");
            noRooms.setPrefHeight(30.0);
            noRooms.setAlignment(Pos.CENTER);
            noRooms.setTextAlignment(TextAlignment.CENTER);
            noRooms.setPrefWidth(300.0);
            noRooms.setFont(new Font(15.0));
            vrooms.getChildren().add(noRooms);
            return;
        }
        for (Room room : centre.getRooms()){
            try {
                HBox roomContainer = new HBox();
                roomContainer.setPrefHeight(30.0);

                Button roomName = new Button(room.getTitle());
                roomName.setAlignment(Pos.CENTER);
                roomName.setTextAlignment(TextAlignment.CENTER);
                roomName.setPrefWidth(75.0);
                roomName.setFont(new Font(13.0));
                roomName.setOnAction(e -> {
                    chosenRoom = room;
                    chosenToy = new Toy(null, null, 0);
                    showToys(room);
                });
                roomContainer.getChildren().add(roomName);

                Label roomAge = new Label(room.getAge() + "");
                roomAge.setAlignment(Pos.CENTER);
                roomAge.setTextAlignment(TextAlignment.CENTER);
                roomAge.setPrefWidth(75.0);
                roomAge.setFont(new Font(13.0));
                roomContainer.getChildren().add(roomAge);

                Label roomToys = new Label(room.getToys().size() + "");
                roomToys.setAlignment(Pos.CENTER);
                roomToys.setTextAlignment(TextAlignment.CENTER);
                roomToys.setPrefWidth(75.0);
                roomToys.setFont(new Font(13.0));
                roomContainer.getChildren().add(roomToys);

                Label roomCost = new Label(room.strPrice());
                roomCost.setAlignment(Pos.CENTER);
                roomCost.setTextAlignment(TextAlignment.CENTER);
                roomCost.setPrefWidth(75.0);
                roomCost.setFont(new Font(13.0));
                roomContainer.getChildren().add(roomCost);

                vrooms.getChildren().add(roomContainer);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showToys(Room room) {
        vtoys.getChildren().clear();

        if (room.getTitle()!=null) {
            if (room.getToys().size() == 0) {
                Label noToys = new Label("В комнате нет игрушек");
                noToys.setPrefHeight(30.0);
                noToys.setAlignment(Pos.CENTER);
                noToys.setTextAlignment(TextAlignment.CENTER);
                noToys.setPrefWidth(300.0);
                noToys.setFont(new Font(15.0));
                vtoys.getChildren().add(noToys);
                return;
            }
            for (Toy toy : room.getToys()) {
                try {
                    HBox toyContainer = new HBox();
                    toyContainer.setPrefHeight(30.0);

                    Label toySize = new Label(toy.getSize());
                    toySize.setAlignment(Pos.CENTER);
                    toySize.setTextAlignment(TextAlignment.CENTER);
                    toySize.setPrefWidth(100.0);
                    toySize.setFont(new Font(13.0));
                    toyContainer.getChildren().add(toySize);

                    Button toyName = new Button(toy.getName());
                    toyName.setAlignment(Pos.CENTER);
                    toyName.setTextAlignment(TextAlignment.CENTER);
                    toyName.setPrefWidth(100.0);
                    toyName.setFont(new Font(13.0));
                    toyName.setOnAction(e -> {chosenToy = toy;});
                    toyContainer.getChildren().add(toyName);

                    Label toyCost = new Label(toy.strPrice());
                    toyCost.setAlignment(Pos.CENTER);
                    toyCost.setTextAlignment(TextAlignment.CENTER);
                    toyCost.setPrefWidth(100.0);
                    toyCost.setFont(new Font(13.0));
                    toyContainer.getChildren().add(toyCost);

                    vtoys.getChildren().add(toyContainer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void sortToys(){
        for (Room room: centre.getRooms()) {
            room.sortToys(room.getToys());
        }
    }

    private void findToys(){
        found_toys.setVisible(false);
        ftoys.getChildren().clear();
        ToggleGroup nameGroup = new ToggleGroup();
        car.setToggleGroup(nameGroup);
        ball.setToggleGroup(nameGroup);
        cube.setToggleGroup(nameGroup);
        doll.setToggleGroup(nameGroup);
        ToggleGroup sizeGroup = new ToggleGroup();
        small.setToggleGroup(sizeGroup);
        medium.setToggleGroup(sizeGroup);
        big.setToggleGroup(sizeGroup);
        btn_find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (nameGroup.getSelectedToggle()!=null){
                        RadioButton nameBuff = (RadioButton) nameGroup.getSelectedToggle();
                        String name = nameBuff.getId();
                        if (sizeGroup.getSelectedToggle()!=null){
                            RadioButton sizeBuff = (RadioButton) sizeGroup.getSelectedToggle();
                            String size = sizeBuff.getId();
                            ftoys.getChildren().clear();
                            found_toys.setVisible(true);
                            for (Room room : centre.getRooms()){
                                for (Toy toy : room.findToys(name, size)){
                                    HBox toyContainer = new HBox();
                                    toyContainer.setPrefHeight(30.0);

                                    Button toyName = new Button(toy.getSize() + " " + toy.getName());
                                    toyName.setAlignment(Pos.CENTER);
                                    toyName.setTextAlignment(TextAlignment.CENTER);
                                    toyName.setPrefWidth(110.0);
                                    toyName.setFont(new Font(13.0));
                                    toyName.setOnAction(e -> {chosenToy = toy; chosenRoom = room; showRooms(centre); showToys(chosenRoom);});
                                    toyContainer.getChildren().add(toyName);

                                    Label toyPrice = new Label(toy.strPrice());
                                    toyPrice.setAlignment(Pos.CENTER);
                                    toyPrice.setTextAlignment(TextAlignment.CENTER);
                                    toyPrice.setPrefWidth(80.0);
                                    toyPrice.setFont(new Font(13.0));
                                    toyContainer.getChildren().add(toyPrice);

                                    Label toySize = new Label(room.getTitle());
                                    toySize.setAlignment(Pos.CENTER);
                                    toySize.setTextAlignment(TextAlignment.CENTER);
                                    toySize.setPrefWidth(80.0);
                                    toySize.setFont(new Font(13.0));
                                    toyContainer.getChildren().add(toySize);

                                    ftoys.getChildren().add(toyContainer);
                                }
                            }
                            if (ftoys.getChildren().size() == 0){
                                Label noToysFound = new Label("Таких игрушек нет");
                                noToysFound.setAlignment(Pos.CENTER);
                                noToysFound.setTextAlignment(TextAlignment.CENTER);
                                noToysFound.setPrefWidth(265.0);
                                noToysFound.setFont(new Font(15.0));

                                ftoys.getChildren().add(noToysFound);
                            }
                            nameGroup.getSelectedToggle().setSelected(false);
                            sizeGroup.getSelectedToggle().setSelected(false);
                        }
                    }
                    showToys(chosenRoom);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void writeToFile() throws IOException {
        File outFile = new File("output.txt");
        OutputStream output = new FileOutputStream(outFile);
        ObjectOutputStream ooutput = new ObjectOutputStream(output);
        ooutput.writeObject(centre);
        ooutput.close();
    }

    private void readFromFile() throws IOException, ClassNotFoundException, FileNotFoundException {
        try {
            File inFile = new File("output.txt");
            InputStream input = new FileInputStream(inFile);
            ObjectInputStream iinput = new ObjectInputStream(input);
            Centre readCentre = (Centre) iinput.readObject();
            for (Room room : readCentre.getRooms()){
                centre.addRoom(room);
            }
            iinput.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
    }
}