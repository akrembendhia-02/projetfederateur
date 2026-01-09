package org.example.dto;


public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String teacherName;

    public CourseResponse(Long id, String title, String description,
                          String category, String teacherName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.teacherName = teacherName;
    }

    // Getters only (response)

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
