package com.example.ecommerceseller.model;


public class Seller {
    private int id;
    private String name;
    private String email;
    private String password;
    private String state;
    private String city;
    private String address;
    private String mobile;
    private String market_name;

    public Seller(String name, String email, String password, String state, String city, String address, String mobile, String marketName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.state = state;
        this.city = city;
        this.address = address;
        this.mobile = mobile;
        this.market_name = marketName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getMarketName() {
        return market_name;
    }
}
