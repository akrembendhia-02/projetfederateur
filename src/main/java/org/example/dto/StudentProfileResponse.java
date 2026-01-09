package org.example.dto;

public class StudentProfileResponse {

    private Long id;
    private Long userId;
    private String name;
    private String level;
    private Integer age;

    public StudentProfileResponse(Long id, Long userId, String name, String level, Integer age ) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.level = level;
        this.age = age;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getLevel() { return level; }
    public Integer getAge() { return age; }
    }
