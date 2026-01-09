package org.example.service;



import org.example.dto.TeacherProfileRequest;
import org.example.dto.TeacherProfileResponse;
import org.example.model.Role;
import org.example.model.TeacherProfile;
import org.example.model.User;
import org.example.repository.TeacherProfileRepository;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TeacherProfileService {

    private final TeacherProfileRepository teacherRepo;
    private final UserRepository userRepo;

    public TeacherProfileService(TeacherProfileRepository teacherRepo, UserRepository userRepo) {
        this.teacherRepo = teacherRepo;
        this.userRepo = userRepo;
    }

    public TeacherProfileResponse create(TeacherProfileRequest req) {

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getRole() != Role.TEACHER)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a teacher");

        TeacherProfile profile = new TeacherProfile(
                user,
                req.getSpecialty()
        );

        return toResponse(teacherRepo.save(profile));
    }

    public List<TeacherProfileResponse> getAll() {
        return teacherRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public TeacherProfileResponse update(Long id, TeacherProfileRequest req) {

        TeacherProfile profile = teacherRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        if (req.getSpecialty() != null) profile.setSpecialty(req.getSpecialty());


        return toResponse(teacherRepo.save(profile));
    }

    public void delete(Long id) {
        teacherRepo.deleteById(id);
    }

    private TeacherProfileResponse toResponse(TeacherProfile p) {
        return new TeacherProfileResponse(
                p.getId(),
                p.getUser().getId(),
                p.getUser().getName(),
                p.getSpecialty()
        );
    }
}
