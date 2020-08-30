package com.example.igo.data;

public class MemberInfo {
    private String name;
    private String address;
    private String phone;
    private String notes;
    private String gender;
    private String birthdate;

    public MemberInfo(String name, String address, String phone, String notes, String gender, String birthdate){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.notes = notes;
        this.gender = gender;
        this.birthdate = birthdate;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getNotes(){
        return this.notes;
    }
    public void setNotes(String notes){
        this.notes = notes;
    }
    public String getGender(){
        return this.gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getBirthdate(){
        return this.birthdate;
    }
    public void setBirthdate(String birthdate){
        this.birthdate = birthdate;
    }
}