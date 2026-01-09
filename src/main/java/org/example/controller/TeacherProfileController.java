package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.TeacherProfileRequest;
import org.example.dto.TeacherProfileResponse;
import org.example.service.TeacherProfileService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher-profiles")
@Tag(name = "Teacher Profiles", description = "Gestion des profils enseignants")
public class TeacherProfileController {

    private final TeacherProfileService service;

    public TeacherProfileController(TeacherProfileService service) {
        this.service = service;
    }

    @Operation(
            summary = "Créer un profil enseignant",
            description = "Crée un nouveau TeacherProfile pour un utilisateur ayant le rôle TEACHER"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profil enseignant créé avec succès"),
            @ApiResponse(responseCode = "400", description = "L'utilisateur n'est pas un enseignant"),
            @ApiResponse(responseCode = "404", description = "User introuvable")
    })
    @PostMapping
    public ResponseEntity<TeacherProfileResponse> create(
            @RequestBody TeacherProfileRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }


    @Operation(
            summary = "Récupérer tous les profils enseignants",
            description = "Retourne la liste complète des TeacherProfiles existants"
    )
    @GetMapping
    public List<TeacherProfileResponse> getAll() {
        return service.getAll();
    }


    @Operation(
            summary = "Modifier un profil enseignant",
            description = "Met à jour les informations d'un profil enseignant existant"
    )
    @PutMapping("/{id}")
    public TeacherProfileResponse update(
            @Parameter(description = "ID du profil enseignant à modifier")
            @PathVariable Long id,
            @RequestBody TeacherProfileRequest req) {
        return service.update(id, req);
    }


    @Operation(
            summary = "Supprimer un profil enseignant",
            description = "Efface définitivement un TeacherProfile"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID du profil enseignant à supprimer")
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

