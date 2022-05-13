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

public class AdminView implements Initializable {

    @FXML
    private Button change_role;
    @FXML
    private Button add_room;
    @FXML
    private Button delete_room;
    @FXML
    private Button add_toy;
    @FXML
    private Button delete_toy;

    @FXML
    private ScrollPane rooms;
    @FXML
    private VBox vrooms;
    @FXML
    private ScrollPane toys;
    @FXML
    private VBox vtoys;

    @FXML
    private Pane pn_add_room;
    @FXML
    private TextField room_name;
    @FXML
    private TextField room_age;
    @FXML
    private TextField room_cost;
    @FXML
    private Button btn_add_room;

    @FXML
    private Pane pn_dlt_room;
    @FXML
    private TextField room_dlt_name;
    @FXML
    private TextField room_dlt_age;
    @FXML
    private TextField room_dlt_cost;
    @FXML
    private TextField room_dlt_toy_amount;
    @FXML
    private Button btn_dlt_room;

    @FXML
    private Pane pn_add_toy;
    @FXML
    private TextField add_toy_room_name;
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
    private TextField toy_cost;
    @FXML
    private Button btn_add_toy;

    @FXML
    private Pane pn_dlt_toy;
    @FXML
    private TextField toy_dlt_room_name;
    @FXML
    private TextField toy_dlt_name;
    @FXML
    private TextField toy_dlt_cost;
    @FXML
    private Button btn_dlt_toy;

    static Centre centre;

    Room chosenRoom = new Room(null, 0, 0);
    Toy chosenToy = new Toy(null, null, 0);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        centre = new Centre();
//        createDefault();
        try {
            readFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        showRooms(centre);
        pn_add_room.setVisible(false);
        pn_dlt_room.setVisible(false);
        pn_add_toy.setVisible(false);
        pn_dlt_toy.setVisible(false);


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
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == add_room) {
            pn_add_room.setVisible(true);
            pn_dlt_room.setVisible(false);
            pn_add_toy.setVisible(false);
            pn_dlt_toy.setVisible(false);
            addRoom();
        }
        if (actionEvent.getSource() == delete_room) {
            pn_add_room.setVisible(false);
            pn_dlt_room.setVisible(true);
            pn_add_toy.setVisible(false);
            pn_dlt_toy.setVisible(false);
            deleteRoom();
        }
        if (actionEvent.getSource() == add_toy) {
            pn_add_room.setVisible(false);
            pn_dlt_room.setVisible(false);
            pn_add_toy.setVisible(true);
            pn_dlt_toy.setVisible(false);
            addToy();
        }
        if (actionEvent.getSource() == delete_toy) {
            pn_add_room.setVisible(false);
            pn_dlt_room.setVisible(false);
            pn_add_toy.setVisible(false);
            pn_dlt_toy.setVisible(true);
            deleteToy();
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
                    deleteRoom();
                    addToy();
                    deleteToy();
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
                    toyName.setOnAction(e -> {chosenToy = toy; deleteToy();});
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

    public void deleteRoom(){
        if (chosenRoom.getTitle()!=null){
            room_dlt_name.setText(chosenRoom.getTitle());
            room_dlt_age.setText(chosenRoom.getAge() + "");
            room_dlt_cost.setText(chosenRoom.strPrice());
            room_dlt_toy_amount.setText(chosenRoom.getToys().size() + "");
            btn_dlt_room.setVisible(true);
            btn_dlt_room.setOnAction(e -> {
                try {
                    centre.deleteRoom(chosenRoom);
                    chosenRoom = new Room(null, 0, 0);
                    showRooms(centre);
                    showToys(chosenRoom);
                    deleteRoom();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        }else {
            room_dlt_name.setText("Выберите комнату в списке");
            btn_dlt_room.setVisible(false);
            room_dlt_age.setText("");
            room_dlt_cost.setText("");
            room_dlt_toy_amount.setText("");
        }
    }

    public void addRoom(){
        room_cost.setTextFormatter(roomPriceFormatter);
        room_age.setTextFormatter(intFormatter);
        btn_add_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (!room_name.getText().isEmpty() && centre.findRoomByName(room_name.getText())){
                        String title = room_name.getText();
                        if (!room_age.getText().isEmpty() && Integer.parseInt(room_age.getText())<12 && Integer.parseInt(room_age.getText())>2){
                            int age = Integer.parseInt(room_age.getText());
                            if (!room_cost.getText().isEmpty()){
                                int price = moneyToInt(room_cost.getText());
                                centre.addRoom(new Room(title, age, price));
                                room_name.setText("");
                                room_age.setText("");
                                room_cost.setText("");
                            }
                        }
                    }
                    showRooms(centre);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteToy(){
        if (chosenRoom.getTitle()!=null){
            toy_dlt_room_name.setText(chosenRoom.getTitle());
            if (chosenToy.getName()!=null){
                toy_dlt_name.setText(chosenToy.getSize() + " " + chosenToy.getName());
                toy_dlt_cost.setText(chosenToy.strPrice());
                btn_dlt_toy.setVisible(true);
                btn_dlt_toy.setOnAction(e -> {
                    try {
                        chosenRoom.deleteToy(chosenToy);
                        chosenToy = new Toy(null, null, 0);
                        showRooms(centre);
                        showToys(chosenRoom);
                        deleteToy();
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }else {
                toy_dlt_name.setText("Выберите игрушку в списке");
                btn_dlt_toy.setVisible(false);
                toy_dlt_cost.setText("");
            }
        }else {
            toy_dlt_room_name.setText("Выберите комнату в списке");
            btn_dlt_toy.setVisible(false);
            toy_dlt_name.setText("");
            toy_dlt_cost.setText("");
        }
    }

    public void addToy() {
        toy_cost.setTextFormatter(toyPriceFormatter);
        ToggleGroup nameGroup = new ToggleGroup();
        car.setToggleGroup(nameGroup);
        ball.setToggleGroup(nameGroup);
        cube.setToggleGroup(nameGroup);
        doll.setToggleGroup(nameGroup);
        ToggleGroup sizeGroup = new ToggleGroup();
        small.setToggleGroup(sizeGroup);
        medium.setToggleGroup(sizeGroup);
        big.setToggleGroup(sizeGroup);
        if (chosenRoom.getTitle()!=null){
            add_toy_room_name.setText(chosenRoom.getTitle());
            btn_add_toy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (nameGroup.getSelectedToggle()!=null){
                            RadioButton nameBuff = (RadioButton) nameGroup.getSelectedToggle();
                            String name = nameBuff.getId();
                            if (sizeGroup.getSelectedToggle()!=null){
                                RadioButton sizeBuff = (RadioButton) sizeGroup.getSelectedToggle();
                                String size = sizeBuff.getId();
                                if (!toy_cost.getText().isEmpty()){
                                    int price = moneyToInt(toy_cost.getText());
                                    chosenRoom.addToy(new Toy(name, size, price));
                                    toy_cost.setText("");
                                    nameBuff.setSelected(false);
                                    sizeBuff.setSelected(false);
                                }
                            }
                        }
                        showToys(chosenRoom);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            add_toy_room_name.setText("Выберите комнату в списке");
        }
    }

    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            try {
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
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

    private int moneyToInt(String inp){
        StringBuilder Money = new StringBuilder();
        for (int i = 0; i < inp.length(); i++){
            if (Character.isDigit(inp.charAt(i))){
                Money.append(inp.charAt(i));
            }
        }
        return Integer.parseInt(Money.toString());
    }

    DecimalFormat format = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
    TextFormatter<Object> roomPriceFormatter = new TextFormatter<>(c ->
    {
        if ( c.getControlNewText().isEmpty() )
        {
            return c;
        }

        ParsePosition parsePosition = new ParsePosition( 0 );
        Object object = format.parse( c.getControlNewText(), parsePosition );

        if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
        {
            return null;
        }
        else
        {
            return c;
        }
    });

    TextFormatter<Object> toyPriceFormatter = new TextFormatter<>(c ->
    {
        if ( c.getControlNewText().isEmpty() )
        {
            return c;
        }

        ParsePosition parsePosition = new ParsePosition( 0 );
        Object object = format.parse( c.getControlNewText(), parsePosition );

        if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
        {
            return null;
        }
        else
        {
            return c;
        }
    });

    DecimalFormat intFormat = new DecimalFormat("0");
    TextFormatter<Object> intFormatter = new TextFormatter<>(c ->
    {
        if ( c.getControlNewText().isEmpty() )
        {
            return c;
        }

        ParsePosition parsePosition = new ParsePosition( 0 );
        Object object = intFormat.parse( c.getControlNewText(), parsePosition );

        if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
        {
            return null;
        }
        else
        {
            return c;
        }
    });

//    public static void createDefault(){
//        Room room1 = new Room("Sunny", 7, 56890);
//        centre.addRoom(room1);
//        room1.addToy(new Toy("car", "small", 506));
//        room1.addToy(new Toy("doll", "big", 873));
//        room1.addToy(new Toy("cube", "medium", 1250));
//        room1.addToy(new Toy("ball", "small", 2669));
//        Room room2 = new Room("Moony", 11, 7809);
//        centre.addRoom(room2);
//        room2.addToy(new Toy("cube", "medium", 1508));
//        room2.addToy(new Toy("doll", "small", 2687));
//        room2.addToy(new Toy("cube", "medium", 1956));
//        Room room3 = new Room("Lollipop", 6, 11265);
//        centre.addRoom(room3);
//        room3.addToy(new Toy("ball", "big", 326));
//        room3.addToy(new Toy("car", "medium", 1836));
//        room3.addToy(new Toy("car", "big", 982));
//    }

}