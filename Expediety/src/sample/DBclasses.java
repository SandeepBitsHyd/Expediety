package sample;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

  class User {

      String name ;
      String dob ;
      String address ;
      String email ;
      int phone ;
      String newuser ;
      String newpass ;

    public User(String name, String dob, String address, String email,int phone,String username,String password) throws ParseException {
        this.name = name;
        this.dob =  dob;
        this.address = address;
        this.email = email;
        this.phone = phone;
        newuser = username;
        newpass = password;
    }
}
 class City {
     int id;
     String name;

    public City(int id,String name) {
        this.id = id;
        this.name = name;
    }
}

 class Hotel {
     int id;
     String name;

     Hotel(int id,String name) {
        this.id = id;
        this.name = name;
    }
}
 class Room {
     int id;
     String name;
     float cost;
     boolean isAvailable;
     boolean isBreakfast;
     boolean isWifi;
     String description;

     Room(int id, String name, float cost, boolean isBreakfast, boolean isWifi, String description) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.isBreakfast = isBreakfast;
        this.isWifi = isWifi;
        this.description = description;
    }
}
