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


    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setTeacherProfileId(Long teacherProfileId) { this.teacherProfileId = teacherProfileId; }

}
