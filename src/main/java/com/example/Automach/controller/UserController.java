package com.example.Automach.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Automach.entity.Users;
import com.example.Automach.entity.Roles;
import com.example.Automach.repo.UserRepo;
import com.example.Automach.repo.RolesRepo;

import java.util.List;
import java.util.Optional;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    RolesRepo rolesRepo;

    @PostMapping("/api/users")
    public ResponseEntity<Users> saveUser(@RequestBody Users user) {
    	
    	
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<Users>> getUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }
    
    
    @GetMapping("/api/users/{userId}")
    public ResponseEntity<Users> getUser(@PathVariable Long userId) {
        Optional<Users> user = userRepo.findByUserId(userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/api/users/{userId}/roles/{roleId}")
    public ResponseEntity<Users> updateUser(@PathVariable Long userId,@PathVariable Long roleId,@RequestBody Users usr) {
        Optional<Users> user = userRepo.findByUserId(userId);
        
        if (user.isPresent()) {
        	Users newUser = user.get();
        	newUser.setPosition(usr.getPosition());
        	  // Update password and confirm password if provided
            if (usr.getPassword() != null && !usr.getPassword().isEmpty()) {
                newUser.setPassword(usr.getPassword());
            }
            if (usr.getConfirmPassword() != null && !usr.getConfirmPassword().isEmpty()) {
                newUser.setConfirmPassword(usr.getConfirmPassword());
            }
        	
        	
        	Optional<Roles> role = rolesRepo.findByRoleId(roleId);
        	if(role.isPresent()) {
        		newUser.setRoles(role.get());
        		System.out.println(role.get());
        	}
        
        	System.out.println(usr);
            return new ResponseEntity<>(userRepo.save(newUser), HttpStatus.OK);
        } 
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/api/login")
    public ResponseEntity<Users> loginUser(@RequestBody Users loginUser) {
        Optional<Users> user = userRepo.findByEmail(loginUser.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(loginUser.getPassword())) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }



}

