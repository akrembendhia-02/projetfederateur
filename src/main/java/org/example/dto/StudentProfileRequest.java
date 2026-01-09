package org.example.dto;

public class StudentProfileRequest {

    private Long userId;     // utilisé seulement pour create
    private String level;
    private Integer age;

    public Long getUserId() { return userId; }
    public String getLevel() { return level; }
    public Integer getAge() { return age; }
}
