package org.example.dto;


public class TeacherProfileResponse {

    private Long id;
    private Long userId;
    private String name;
    private String specialty;

    public TeacherProfileResponse(Long id, Long userId, String name, String specialty ) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.specialty = specialty;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    }
