package org.example.model;
import jakarta.persistence.*;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    private String level;   // niveau d'étude
    private int age;

    public StudentProfile() {}

    public StudentProfile(User user, String level, int age) {
        this.user = user;
        this.level = level;
        this.age = age;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getLevel() { return level; }
    public int getAge() { return age; }

    public void setUser(User user) { this.user = user; }
    public void setLevel(String level) { this.level = level; }
    public void setAge(int age) { this.age = age; }
 }
