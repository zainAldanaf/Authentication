package com.example.authentication.Module;

public class Account {

    private String fullname,address,email,phone,password;

    public Account(String fullname, String address, String email, String phone, String password) {
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
    public  Account() {

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}