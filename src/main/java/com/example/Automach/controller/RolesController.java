package com.example.Automach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Automach.entity.Roles;
import com.example.Automach.repo.RolesRepo;

import java.util.List;
import java.util.Optional;

@RestController
public class RolesController {

    @Autowired
    RolesRepo rolesRepo;

    @PostMapping("/api/roles")
    public ResponseEntity<Roles> saveRole(@RequestBody Roles role) {
        return new ResponseEntity<>(rolesRepo.save(role), HttpStatus.CREATED);
    }

    @GetMapping("/api/roles")
    public ResponseEntity<List<Roles>> getRoles() {
        return new ResponseEntity<>(rolesRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/roles/{roleId}")
    public ResponseEntity<Roles> getRole(@PathVariable Long roleId) {
        Optional<Roles> role = rolesRepo.findByRoleId(roleId);
        if (role.isPresent()) {
            return new ResponseEntity<>(role.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/roles/{roleId}")
    public ResponseEntity<Roles> updateRole(@PathVariable Long roleId, @RequestBody Roles roleDetails) {
        Optional<Roles> role = rolesRepo.findByRoleId(roleId);
        if (role.isPresent()) {
            Roles existingRole = role.get();
            existingRole.setRoles(roleDetails.getRoleName());
            return new ResponseEntity<>(rolesRepo.save(existingRole), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
