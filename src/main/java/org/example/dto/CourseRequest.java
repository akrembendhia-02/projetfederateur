package org.example.dto;

public class CourseRequest {

    private String title;
    private String description;
    private String category;
    private Long teacherProfileId;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public Long getTeacherProfileId() { return teacherProfileId; }
}
