package com.example.silvia.dbproject;

/**
 * Created by Silvia on 30/07/2016.
 */
public class Persona {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int id;
    private String name;
    private String surname;
    private String number;
    private String address;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public Persona (int _id, String _name, String _surname, String _number, String _address, String _image ){
        this.id = _id;
        this.name = _name;
        this.surname = _surname;
        this.number = _number;
        this.address = _address;
        this.address = _image;
    }

    public Persona (String _name, String _surname, String _number, String _address, String _image ){

        this.name = _name;
        this.surname = _surname;
        this.number = _number;
        this.address = _address;
        this.image = _image;
    }
    public Persona(){}
}
