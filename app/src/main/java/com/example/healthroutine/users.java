package com.example.healthroutine;

public class users {

    private String Username;
    private String Phone;
    private long Steps;
    private long CountCups;


    public users(String username, String phone, long steps, long countCups) {
        this.Username = username;
        this.Phone = phone;
        this.Steps = steps;
        this.CountCups = countCups;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public long getSteps() { return Steps; }

    public void setSteps(long Steps) {
        this.Steps = Steps;
    }

    public long getCountCups() { return CountCups; }

    public void setCountCups(long countCups) { CountCups = countCups; }

    @Override
    public String toString() {
        return "users{" +
                "Username='" + Username + '\'' +
                ", phone='" + Phone + '\'' +
                ", Steps='" + Steps + '\'' +
                ", Count of cups='" + CountCups + '\'' +
                '}';
    }
}
