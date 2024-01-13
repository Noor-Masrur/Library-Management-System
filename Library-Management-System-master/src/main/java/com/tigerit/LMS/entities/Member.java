package com.tigerit.LMS.entities;

import java.time.LocalDate;
import java.util.Date;

public class Member {
    private Long id;
    private String name;
    private String email;
    private String contact;
    private LocalDate registeredDate;

    public Member() {
    }

    public Member(Long id, String name, String email, String contact, LocalDate registeredDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.registeredDate = registeredDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    // Add more attributes as needed

    // Constructors, getters, and setters

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", registeredDate=" + registeredDate +
                '}';
    }
}
