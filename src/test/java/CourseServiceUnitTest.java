import org.example.dto.CourseRequest;
import org.example.model.Course;
import org.example.model.TeacherProfile;
import org.example.model.User;
import org.example.repository.CourseRepository;
import org.example.repository.TeacherProfileRepository;
import org.example.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.example.dto.CourseRequest;
import org.example.model.Course;
import org.example.model.TeacherProfile;
import org.example.repository.CourseRepository;
import org.example.repository.TeacherProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CourseServiceUnitTest {

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private TeacherProfileRepository teacherRepo;

    @InjectMocks
    private CourseService service;

    private TeacherProfile mockTeacher;
    private Course mockCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockTeacher = new TeacherProfile();
        mockTeacher.setId(1L);

        mockCourse = new Course();
        mockCourse.setTitle("Java Basics");
        mockCourse.setTeacher(mockTeacher);
    }

    @Test
    void shouldThrow404WhenTeacherNotFound() {
        CourseRequest req = new CourseRequest();
        req.setTeacherProfileId(1L);
        req.setTitle("Java Basics");

        when(teacherRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.create(req));
    }

    @Test
    void shouldRejectDuplicateCourseForSameTeacher() {
        CourseRequest req = new CourseRequest();
        req.setTeacherProfileId(1L);
        req.setTitle("Java Basics");

        when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(mockTeacher));
        when(courseRepo.findByTitleAndTeacherId(anyString(), anyLong()))
                .thenReturn(Optional.of(mockCourse));

        assertThrows(ResponseStatusException.class, () -> service.create(req));
    }

    @Test
    void shouldCreateCourseSuccessfully() {
        User mockUser = new User();
        mockUser.setName("John Doe");

        TeacherProfile mockTeacher = new TeacherProfile();
        mockTeacher.setId(1L);
        mockTeacher.setUser(mockUser);

        CourseRequest req = new CourseRequest();
        req.setTeacherProfileId(1L);
        req.setTitle("Spring Boot");
        req.setDescription("Learn Spring Boot");
        req.setCategory("Backend");

        when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(mockTeacher));
        when(courseRepo.findByTitleAndTeacherId(anyString(), anyLong()))
                .thenReturn(Optional.empty());

        Course savedCourse = new Course();
        savedCourse.setId(1L);
        savedCourse.setTitle(req.getTitle());
        savedCourse.setDescription(req.getDescription());
        savedCourse.setCategory(req.getCategory());
        savedCourse.setTeacher(mockTeacher);

        when(courseRepo.save(any(Course.class))).thenReturn(savedCourse);

        var response = service.create(req);

        assert response.getId() == 1L;
        assert response.getTitle().equals(req.getTitle());
        assert response.getDescription().equals(req.getDescription());
        assert response.getCategory().equals(req.getCategory());
        assert response.getTeacherName().equals("John Doe");
    }

}
