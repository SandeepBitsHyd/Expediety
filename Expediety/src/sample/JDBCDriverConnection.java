package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

 class JDBCDriverConnection {
    static Connection conn = null;
    static Statement stmt = null;

     static void connect() {

        try {
             Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:\\Practice\\Expediety\\src\\sample\\users.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
     }
     static void disconnect(){
        try {
            if (conn != null) {
                conn.close();
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

     static boolean authenticateUser(String submittedUsername,String submittedPassword){
        boolean authenticated = false;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while ( rs.next() ) {
                String  username = rs.getString("newuser");
                String  password = rs.getString("newpass");
                if(username.equals(submittedUsername) && password.equals(submittedPassword)){
                    authenticated = true;
                }
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException e){
            System.out.println("nope");
            System.out.println(e);
            System.exit(0);
        }
        return authenticated;
    }

     static boolean createUser(String name, String dob,String email,String address,String phone,String newuser,String newpass){
        boolean userCreated= false;

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO users(name,dob,email,address,phone,newuser,newpass) VALUES " + "(" + "'" + name + "'" +","+ "'" + dob + "'"+"," +  "'" + email + "'" +","+ "'" + address + "'" +"," + "'" + phone + "'" +","+ "'" + newuser + "'" +"," + "'" + newpass + "'" +")");
            stmt.close();
            userCreated = true;
        }
        catch (SQLException e){
            System.out.println("nope");
            System.out.println(e);
            userCreated = false;
        }
        return userCreated;
    }

     static List<City> getCitiesList(){
        Stack<City> myCitiesList = new Stack<>();
        try{

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from cities");
            while ( rs.next() ) {
                City myCity ;
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                myCity  = new City(id,name);
                myCitiesList.push(myCity);
            }

            rs.close();
            stmt.close();

        }

        catch (SQLException e){
            System.out.println("nope");
            System.out.println(e);
            System.exit(0);
        }

        return myCitiesList;
    }

     static List<Hotel> getHotelsList(City myCity){
        Stack<Hotel> myHotelsList = new Stack<>();
        try{
            int myCity_id = myCity.id;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from hotels inner join cities on hotels.city_id = cities.id where cities.id =" + myCity_id);
            while ( rs.next() ) {
                Hotel myHotel ;
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                myHotel = new Hotel(id,name);
                myHotelsList.push(myHotel);
            }

            rs.close();
            stmt.close();

        }

        catch (SQLException e){
            System.out.println("nope");
            System.out.println(e);
            System.exit(0);
        }

        return myHotelsList;
    }

     static List<Room> getRoomsList(Hotel myHotel){
        Stack<Room> myRoomsList = new Stack<>();
        try{
            int myHotel_id = myHotel.id;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from rooms inner join hotels on rooms.hotel_id = hotels.id where hotels.id =" + myHotel_id);
            while ( rs.next() ) {
                Room myRoom ;
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                float cost = rs.getFloat("cost");
                boolean isBreakfast = rs.getBoolean("isbreakfast");
                boolean isWifi = rs.getBoolean("iswifi");
                String description = rs.getString("description");

                myRoom = new Room(id,name,cost,isBreakfast,isWifi,description);
                myRoomsList.push(myRoom);
            }

            rs.close();
            stmt.close();

        }
        catch (SQLException e){
            System.out.println("nope");
            System.out.println(e);
            System.exit(0);
        }

        return myRoomsList;
    }
}
