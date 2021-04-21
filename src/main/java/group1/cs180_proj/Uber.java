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
    private String date,time,state,pickup,address,street;

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
    
    public Uber(String d, String t, String se, String p, String a, String st){
        date = d;
        time = t;
        state = se.toUpperCase();
        pickup = p.toUpperCase();
        address = a.toUpperCase();
        street = st.toUpperCase();
    }
    
    @Override
    public String toString(){
        return "Date: " + date + "\t Time: " + time + "\t State: " + state + "\t Pickup: " + pickup + "\t Address: " + address + "\t Street: " + street + "\n";
    }
}
