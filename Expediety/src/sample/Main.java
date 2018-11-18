package sample;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Main extends Application {

        static Stage Expediety;
        static Scene s1, s2, s3, s4;

        static Button signUp = new Button("Sign Up");
        static Button logIn = new Button("Log In");
        static Button next = new Button("NEXT");
        static Button back = new Button("BACK");
        static Button exit = new Button("EXIT");
        static Button reg = new Button("REGISTER");
        static Button search = new Button("SEARCH");

        // s1
        static TextField username = new TextField();
        static PasswordField password = new PasswordField();
        //s2
        static TextField name = new TextField();
        static DatePicker dob = new DatePicker();
        static TextArea address = new TextArea();
        static TextField email = new TextField();
        static TextField phone = new TextField();
        //s3
        static TextField newuser = new TextField();
        static PasswordField newpass = new PasswordField();
        static PasswordField newconpass = new PasswordField();
        //s4
        static ComboBox<String> presloc = new ComboBox<>();
        static DatePicker checkin = new DatePicker();
        static DatePicker checkout = new DatePicker();
        static TextField adults = new TextField();
        static TextField adults2 = new TextField();


        static Label labelresponse1 = new Label();
        static Label labelresponse2 = new Label();
        static Label labelresponse3 = new Label();
        static Label labelresponse4 = new Label();
        static Label labelresponse5 = new Label();

    //controllers
        static RegisterController c1 = new RegisterController();
        static loginController c2 = new loginController();


        static CheckBox addroom = new CheckBox("ADD ROOM");

    @Override
    public void start(Stage primaryStage) {
        Expediety = primaryStage;
        primaryStage.setTitle("Expediety");

        // Welcome Page
        Image exp = new Image("File:\\C:\\Practice\\Expediety\\src\\sample\\EXPEDIETY.png");
        ImageView logo = new ImageView(exp);
        BorderPane b = new BorderPane();
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.addColumn(1, new Label("USERNAME :"), new Label("PASSWORD :"), signUp);
        layout.addColumn(2, username, password, logIn);
        layout.setHgap(20);
        layout.setVgap(15);
        layout.add(labelresponse1,1,4,2,1);
        layout.setHalignment(logIn, HPos.RIGHT);
        layout.setAlignment(Pos.CENTER);
        b.setCenter(layout);
        s1 = new Scene(b, 900, 450);
        primaryStage.setScene(s1);
        primaryStage.show();


        logIn.setOnAction(event -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()){
                labelresponse1.setText("*Username or password cannot be left");
                labelresponse1.setTextFill(Color.valueOf("RED"));}
            else { JDBCDriverConnection.connect();
                boolean authentication = JDBCDriverConnection.authenticateUser(username.getText(), password.getText());
                JDBCDriverConnection.disconnect();
                if(authentication){Expediety.setScene(s4);}else {labelresponse1.setText("*Username or Password incorrect!");
                    labelresponse1.setTextFill(Color.valueOf("RED"));}
            }
        });
        signUp.setOnAction(event -> {Expediety.setScene(s2);username.setText("");password.setText("");  });

        // Sign-Up page-1
        GridPane layout2 = new GridPane();
        layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(5, 5, 5, 5));
        HBox h2 = new HBox(800, exit, next);
        BorderPane b2 = new BorderPane(layout2);
        b2.setBottom(h2);
        h2.setAlignment(Pos.TOP_CENTER);
        s2 = new Scene(b2, 900, 450);
        layout2.addColumn(1, new Label("NAME :"), new Label("DOB :"), new Label("E-MAIL ID :"), new Label(" RESIDENTIAL ADDRESS :"), new Label("PHONE NO :"));
        layout2.addColumn(2, name, dob, email, address, phone);
        dob.setPromptText("mm/dd/yyyy");
        address.setPromptText("D.no /n");
        layout2.setHgap(20);
        layout2.setVgap(15);
        layout2.add(labelresponse2,2,6,2,1);
        labelresponse2.setAlignment(Pos.BASELINE_RIGHT);

            next.setOnAction(event ->  {if (name.getText().isEmpty() || email.getText().isEmpty() || address.getText().isEmpty() || phone.getText().isEmpty()|| dob.getValue()== null) { labelresponse2.setText("*All the fields are MANDATORY");
                labelresponse2.setTextFill(Color.valueOf("RED"));}
            else {Expediety.setScene(s3);labelresponse2.setText("");}});
            exit.setOnAction(event -> Expediety.setScene(s1));



        //Sign-Up page-2
        GridPane layout3 = new GridPane();
        layout3.setAlignment(Pos.CENTER);
        HBox h3 = new HBox(650, back, reg);
        BorderPane b3 = new BorderPane(layout3);
        layout3.setPadding(new Insets(5, 5, 5, 5));
        b3.setBottom(h3);
        h3.setAlignment(Pos.TOP_CENTER);
        s3 = new Scene(b3, 900, 450);
        layout3.addColumn(1, new Label("USERNAME :"), new Label("PASSWORD :"), new Label("CONFIRM PASSWORD:"));
        layout3.addColumn(2, newuser, newpass, newconpass);
        layout3.setHgap(20);
        layout3.setVgap(15);
        layout3.add(labelresponse3,2,4,2,1);

        reg.setOnAction( event -> {
            if(!newpass.getText().equals(newconpass.getText()))
        { labelresponse3.setText("*PASSWORD and CONFIRM PASSWORD are not MATCHING");
            labelresponse3.setTextFill(Color.valueOf("RED")); }
        else if (newuser.getText().isEmpty() || newpass.getText().isEmpty()|| newconpass.getText().isEmpty())
        {
            labelresponse3.setText("*Username or password cannot be left");
            labelresponse3.setTextFill(Color.valueOf("RED"));
        }
        else if(newpass.getText().length()<8)
            {
                labelresponse3.setText("*Password should be a minimum of 8 characters.");
                labelresponse3.setTextFill(Color.valueOf("RED"));
            }
        else {
                    Main.Expediety.setScene(Main.s1);
                    JDBCDriverConnection.connect();
                    JDBCDriverConnection.createUser(name.getText(), dob.getValue().toString(), email.getText(), address.getText(), phone.getText(), newuser.getText(), newpass.getText());
                    JDBCDriverConnection.disconnect();
                    Main.name.setText("");
                    Main.dob.setValue(null);
                    Main.address.setText("");
                    Main.email.setText("");
                    phone.setText("");
                    Main.newuser.setText("");
                    Main.newpass.setText("");
                    Main.newconpass.setText("");
                    Main.labelresponse3.setText("");
                }
             }
        );
        back.setOnAction( event1 -> Expediety.setScene(s2));

        // Room Requirements Page
        GridPane layout4 = new GridPane();
        layout4.setAlignment(Pos.CENTER);
        layout4.setPadding(new Insets(5, 5, 5, 5));
        BorderPane b4 = new BorderPane(layout4);
        s4 = new Scene(b4, 900, 450);
        layout4.addColumn(0, new Label("LOCATION :"), new Label("CHECK-IN :"), new Label("CHECK-OUT:"), new Label("ADULTS/room:"));
        layout4.addColumn(1, presloc, checkin, checkout, adults, addroom);
        layout4.setHgap(20);
        layout4.setVgap(15);
        refinecheckinCell(checkin);
        refinecheckoutcell(checkout);
        presloc.setPromptText("DESTINATION");
        checkin.setPromptText("mm/dd/yyyy");
        checkout.setPromptText("mm/dd/yyyy");
        adults.setPromptText("Not more than 4.");
        presloc.getItems().addAll("option 1", "option 2", "option 3");
        Label l = new Label("ADULTS/room:");
        presloc.setEditable(true);
        layout4.add(labelresponse4,2,3,2,1);
        layout4.add(search, 1, 6);
        adults.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                adults.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        adults2.textProperty().addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                adults2.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


            addroom.setOnAction(event -> {
                        if (addroom.isSelected()) {
                            layout4.addRow(5,l,adults2,labelresponse5);
                            adults2.setPromptText("Not more than 4.");
                        } else {
                            layout4.getChildren().remove(l);
                            layout4.getChildren().remove(adults2);
                            layout4.getChildren().remove(labelresponse5);
                        }
                    }
            );

        search.setOnAction(event ->
        {

                    if (Integer.parseInt(adults.getText()) > 4 || Integer.parseInt(adults.getText()) < 1|| adults.getText().equals(""))
                    {
                        labelresponse4.setText("*INVALID INPUT");
                        labelresponse4.setTextFill(Paint.valueOf("RED"));
                        if(Integer.parseInt(adults2.getText()) > 4 || Integer.parseInt(adults2.getText()) < 1|| adults2.getText().equals(""))
                        {
                            labelresponse5.setText("*INVALID INPUT");
                            labelresponse5.setTextFill(Paint.valueOf("RED"));
                        }
                        else if(Integer.parseInt(adults2.getText()) < 5 || Integer.parseInt(adults2.getText()) > 0|| !adults2.getText().equals(""))
                        {
                            labelresponse5.setText("");
                        }
                    }
                    else if(Integer.parseInt(adults.getText()) < 5 || Integer.parseInt(adults.getText()) > 0|| !adults.getText().equals(""))
                    {
                        labelresponse4.setText("");
                        if(Integer.parseInt(adults2.getText()) > 4 || Integer.parseInt(adults2.getText()) < 1|| adults2.getText().equals(""))
                        {
                            labelresponse5.setText("*INVALID INPUT");
                            labelresponse5.setTextFill(Paint.valueOf("RED"));
                        }
                        else if(Integer.parseInt(adults2.getText()) < 5 || Integer.parseInt(adults2.getText()) > 0|| !adults2.getText().equals(""))
                        {
                            labelresponse5.setText("");
                        }
                    }


        }
        );
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        launch(args);
    }

    public void refinecheckinCell(DatePicker checkin)
    {
        final Callback<DatePicker, DateCell> dateCellCallback = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                        }
                        }
                    };
                }
            };

        checkin.setDayCellFactory(dateCellCallback);
    }
    public void refinecheckoutcell(DatePicker checkout)
    {
        final Callback<DatePicker, DateCell> dateCellCallback = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(checkin.getValue().plusDays(1))) {
                            setDisable(true);
                        }
                        int p = (int) ChronoUnit.DAYS.between(checkin.getValue(),item);
                        setTooltip(new Tooltip("you are selecting "+p+" days to stay"));
                    }
                };
            }
        };
        checkout.setDayCellFactory(dateCellCallback);
    }
}

class scene4 extends Thread
{

}



