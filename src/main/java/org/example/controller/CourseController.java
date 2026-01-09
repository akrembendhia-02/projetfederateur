package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.example.dto.CourseRequest;
import org.example.dto.CourseResponse;
import org.example.service.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Gestion des cours créés par les enseignants")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @Operation(
            summary = "Créer un cours",
            description = "Permet à un enseignant (TeacherProfile) de créer un nouveau cours"
    )
    @ApiResponse(responseCode = "201", description = "Cours créé avec succès")
    @PostMapping
    public ResponseEntity<CourseResponse> create(@RequestBody CourseRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @Operation(
            summary = "Liste de tous les cours",
            description = "Récupère tous les cours créés par n'importe quel enseignant"
    )
    @ApiResponse(responseCode = "200", description = "Liste renvoyée avec succès")
    @GetMapping
    public List<CourseResponse> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Modifier un cours",
            description = "Permet à un enseignant de modifier ses propres cours"
    )
    @PutMapping("/{id}")
    public CourseResponse update(
            @Parameter(description = "ID du cours à modifier") @PathVariable Long id,
            @RequestBody CourseRequest req) {

        return service.update(id, req);
    }

    @Operation(
            summary = "Supprimer un cours",
            description = "Un enseignant peut supprimer un de ses cours"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID du cours à supprimer") @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
