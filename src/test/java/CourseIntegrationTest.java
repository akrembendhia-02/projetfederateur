import org.example.Main;

import org.example.model.*;
import org.example.repository.TeacherProfileRepository;
import org.example.repository.UserRepository;
import org.example.repository.CourseRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CourseIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherProfileRepository teacherProfileRepository;
    @Autowired
    private CourseRepository courseRepository;

    private User savedUser;
    private TeacherProfile savedTeacherProfile;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("teacher1");
        user.setPassword("password123");
        user.setEmail("teacher1@example.com");
        user.setRole(Role.TEACHER);

        savedUser = userRepository.save(user);

        TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setUser(savedUser);
        teacherProfile.setSpecialty("Mathematics");

        savedTeacherProfile = teacherProfileRepository.save(teacherProfile);

        Course course = new Course();
        course.setTitle("Algebra 101");
        course.setDescription("Introductory algebra course");
        course.setCategory("Math");
        course.setTeacher(savedTeacherProfile);

        courseRepository.save(course);
    }

    @Test
    void testCourseCreation() {
        List<Course> courses = courseRepository.findAll();

        assertFalse(courses.isEmpty());

        assertEquals("maths", courses.get(0).getTitle());
    }
}
