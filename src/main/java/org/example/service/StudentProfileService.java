package org.example.service;


import org.example.dto.StudentProfileRequest;
import org.example.dto.StudentProfileResponse;
import org.example.model.StudentProfile;
import org.example.model.User;
import org.example.repository.StudentProfileRepository;
import org.example.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentRepo;
    private final UserRepository userRepo;

    public StudentProfileService(StudentProfileRepository studentRepo, UserRepository userRepo) {
        this.studentRepo = studentRepo;
        this.userRepo = userRepo;
    }

    public StudentProfileResponse create(StudentProfileRequest req) {

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        StudentProfile sp = new StudentProfile(
                user,
                req.getLevel(),
                req.getAge()
        );

        return toResponse(studentRepo.save(sp));
    }

    public List<StudentProfileResponse> getAll() {
        return studentRepo.findAll().stream().map(this::toResponse).toList();
    }

    public StudentProfileResponse update(Long id, StudentProfileRequest req) {

        StudentProfile sp = studentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        if (req.getLevel() != null) sp.setLevel(req.getLevel());
        if (req.getAge() != null) sp.setAge(req.getAge());

        return toResponse(studentRepo.save(sp));
    }

    public void delete(Long id) {
        studentRepo.deleteById(id);
    }

    private StudentProfileResponse toResponse(StudentProfile sp) {
        return new StudentProfileResponse(
                sp.getId(),
                sp.getUser().getId(),
                sp.getUser().getName(),
                sp.getLevel(),
                sp.getAge()
        );
    }
}
