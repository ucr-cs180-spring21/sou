/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;

import java.io.Serializable;

/**
 *
 * @author patri
 */
public class Uber implements Serializable{
    private String date,time,state,pickup,address,street,id;

    public String getID(){
        return id;
    }
    
    public void setID(String id){
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date.toUpperCase();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state.toUpperCase();
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup.toUpperCase();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address.toUpperCase();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street.toUpperCase();
    }
    
    public String getFullAddress(){
        return address + " " + street;
    }
    
    public Uber(String d, String t, String se, String p, String a, String st, String i){
        this.date = d;
        this.time = t;
        this.state = se.toUpperCase();
        this.pickup = p.toUpperCase();
        this.address = a.toUpperCase();
        this.street = st.toUpperCase();
        this.id = i;
        
    }
    
    public Uber(Uber foo){
        this.date = foo.getDate();
        this.time = foo.getTime();
        this.state = foo.getState();
        this.pickup = foo.getPickup();
        this.address = foo.getAddress();
        this.street = foo.getStreet();
        this.id = foo.getID();
    }
    
    @Override
    public String toString(){
        return "Date: " + date + "\t Time: " + time + "\t State: " + state + "\t Pickup: " + pickup + "\t Address: " + address + "\t Street: " + street + "\n";
    }
}
