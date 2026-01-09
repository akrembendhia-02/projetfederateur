package org.example.model;


import jakarta.persistence.*;

@Entity
@Table(name = "teacher_profiles")
public class TeacherProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String specialty;


    private int yearsExperience;

    public TeacherProfile() {}

    public TeacherProfile(User user, String specialty, int yearsExperience) {
        this.user = user;
        this.specialty = specialty;
        this.yearsExperience = yearsExperience;
    }

    public TeacherProfile(User user, String specialty) {
        this.user = user;
        this.specialty = specialty;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }


    public int getYearsExperience() { return yearsExperience; }
    public void setYearsExperience(int yearsExperience) { this.yearsExperience = yearsExperience; }
}
