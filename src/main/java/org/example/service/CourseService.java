package org.example.service;

import org.example.dto.CourseRequest;
import org.example.dto.CourseResponse;
import org.example.model.Course;
import org.example.model.TeacherProfile;
import org.example.repository.CourseRepository;
import org.example.repository.TeacherProfileRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CourseService
{

    private final CourseRepository courseRepo;
    private final TeacherProfileRepository teacherRepo;

    public CourseService(CourseRepository courseRepo, TeacherProfileRepository teacherRepo) {
        this.courseRepo = courseRepo;
        this.teacherRepo = teacherRepo;
    }

    public CourseResponse create(CourseRequest req) {

        // 1) Vérifier que le teacherProfile existe
        TeacherProfile teacher = teacherRepo.findById(req.getTeacherProfileId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Teacher profile not found"));

        // 2) Vérifier si un cours DU MÊME ENSEIGNANT porte déjà ce titre
        if (courseRepo.findByTitleAndTeacherId(req.getTitle(), req.getTeacherProfileId()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ce cours existe déjà pour cet enseignant !");
        }
        Course course = new Course(
                req.getTitle(),
                req.getDescription(),
                req.getCategory(),
                teacher
        );

        return toResponse(courseRepo.save(course));
    }

    public List<CourseResponse> getAll() {
        return courseRepo.findAll().stream().map(this::toResponse).toList();
    }

    public CourseResponse update(Long id, CourseRequest req) {

        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (req.getTitle() != null) course.setTitle(req.getTitle());
        if (req.getDescription() != null) course.setDescription(req.getDescription());
        if (req.getCategory() != null) course.setCategory(req.getCategory());

        return toResponse(courseRepo.save(course));
    }

    public void delete(Long id) {
        courseRepo.deleteById(id);
    }

    private CourseResponse toResponse(Course c) {
        return new CourseResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getCategory(),
                c.getTeacher().getUser().getName()
        );
    }
}
