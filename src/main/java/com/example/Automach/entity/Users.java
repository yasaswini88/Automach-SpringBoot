package com.example.Automach.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String firstName;
    private String lastName;
    private String gender;
    private String position;
    private Date dateOfBirth;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "role_Id")
    private Roles roles;

    // No-argument constructor for JPA
    public Users() {
        super();
    }

    // All-argument constructor
    public Users(Long userId, String firstName, String lastName, String gender, String position, Date dateOfBirth,
                 String email, String password, String confirmPassword, String phoneNumber) {
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Users [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
                + ", position=" + position + ", dateOfBirth=" + dateOfBirth + ", email=" + email + ", password="
                + password + ", confirmPassword=" + confirmPassword + ", phoneNumber=" + phoneNumber + "]";
    }
}
