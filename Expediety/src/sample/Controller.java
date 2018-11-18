package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

class RegisterController implements Initializable {
     TextField nameTextField = Main.name;
     DatePicker dateofbirthDatePicker = Main.dob;
     TextField emailTextField = Main.email;
     TextArea addressTextField = Main.address;
     TextField phone = Main.phone ;
     TextField usernameTextField = Main.newuser;
     PasswordField passwordPasswordField = Main.newpass;
     Button registerButton =  Main.reg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButton.setOnAction(event ->
        {
            if(!Main.newpass.getText().equals(Main.newconpass.getText()))
            {
                    Main.labelresponse3.setText("*PASSWORD and CONFIRM PASSWORD are not MATCHING");
                    Main.labelresponse3.setTextFill(Color.valueOf("RED"));
            }
            else if (Main.newuser.getText().isEmpty() || Main.newpass.getText().isEmpty()|| Main.newconpass.getText().isEmpty())
                {
                    Main.labelresponse3.setText("*Username or password cannot be left");
                    Main.labelresponse3.setTextFill(Color.valueOf("RED"));
                }
                else
                    {
                        Main.Expediety.setScene(Main.s1);
                        JDBCDriverConnection.connect();
                        JDBCDriverConnection.createUser(nameTextField.getText(),dateofbirthDatePicker.getValue().toString(), emailTextField.getText(), addressTextField.getText(), phone.getText(),usernameTextField.getText(), passwordPasswordField.getText());
                        JDBCDriverConnection.disconnect();
                        Main.name.setText("");Main.dob.setValue(null);Main.address.setText("");Main.email.setText("");phone.setText("");Main.newuser.setText("");Main.newpass.setText("");Main.newconpass.setText("");Main.labelresponse3.setText("");
                    }
        });}}

class loginController implements Initializable {
     TextField usernameTextField = Main.username;
     PasswordField passwordPasswordField;
     Button loginButton;
     Button signupButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage currentWindow = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                boolean authenticated = false;
                JDBCDriverConnection.connect();
                authenticated=JDBCDriverConnection.authenticateUser(usernameTextField.getText(),passwordPasswordField.getText());
                JDBCDriverConnection.disconnect();
                if(authenticated){
                    Parent bookingPortal = null;
                    try{
                        bookingPortal = FXMLLoader.load(getClass().getResource("BookingPortalCity.fxml"));
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }

                    currentWindow.setTitle("Hello World");
                    currentWindow.setScene(new Scene(bookingPortal  , 1280  , 720));
                    currentWindow.show();
                }
                System.out.println(authenticated);
            }
        });
        signupButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                Stage currentWindow = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Parent signup = null;
                try {
                    signup = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                currentWindow.setTitle("Hello World");
                currentWindow.setScene(new Scene(signup,600,800));
                currentWindow.show();


            }
        });
    }

}


