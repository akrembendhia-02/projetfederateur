package org.example.controller;

import org.example.dto.StudentProfileRequest;
import org.example.dto.StudentProfileResponse;
import org.example.service.StudentProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-profiles")
@Tag(name = "Student Profiles", description = "Gestion des profils étudiants")
public class StudentProfileController {

    private final StudentProfileService service;

    public StudentProfileController(StudentProfileService service) {
        this.service = service;
    }

    @Operation(
            summary = "Créer un profil étudiant",
            description = "Permet de créer un profil pour un utilisateur étudiant"
    )
    @ApiResponse(responseCode = "201", description = "Profil créé avec succès")
    @PostMapping
    public ResponseEntity<StudentProfileResponse> create(@RequestBody StudentProfileRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @Operation(
            summary = "Afficher tous les profils étudiants",
            description = "Retourne la liste complète des profils étudiants"
    )
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping
    public List<StudentProfileResponse> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Modifier un profil étudiant",
            description = "Permet de mettre à jour un profil étudiant existant"
    )
    @ApiResponse(responseCode = "200", description = "Profil modifié avec succès")
    @PutMapping("/{id}")
    public StudentProfileResponse update(@PathVariable Long id, @RequestBody StudentProfileRequest req) {
        return service.update(id, req);
    }

    @Operation(
            summary = "Supprimer un profil étudiant",
            description = "Supprime un profil étudiant par son ID"
    )
    @ApiResponse(responseCode = "204", description = "Profil supprimé avec succès")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
