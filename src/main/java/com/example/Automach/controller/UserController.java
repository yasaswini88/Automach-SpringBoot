package com.example.Automach.controller;

import org.springframework.transaction.annotation.Transactional;
import com.example.Automach.DTO.CodeVerificationRequest;
import com.example.Automach.DTO.PasswordResetRequest;
import com.example.Automach.Service.EmailService;
import com.example.Automach.entity.VerificationCode;
import com.example.Automach.repo.VerificationCodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Automach.entity.Users;
import com.example.Automach.entity.Roles;
import com.example.Automach.repo.UserRepo;
import com.example.Automach.repo.RolesRepo;

import java.util.*;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    RolesRepo rolesRepo;

    @Autowired
    VerificationCodeRepo verificationCodeRepo;

    @Autowired
    private EmailService emailService;


    // Method to generate the verification code and expiration time
    private String generateRandomCode() {
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }

    // Method to generate the expiration time as a Date object
    private Date generateExpirationTime() {
        return new Date(System.currentTimeMillis() + (10 * 60 * 1000)); // 10 minutes
    }

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


    @PutMapping("/api/users/{userId}")
    public ResponseEntity<Users> updateUserDetails(@PathVariable Long userId, @RequestBody Users updatedUser) {
        Optional<Users> existingUser = userRepo.findByUserId(userId);

        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setPosition(updatedUser.getPosition());
            user.setGender(updatedUser.getGender());

            // Password update if provided
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getConfirmPassword() != null && !updatedUser.getConfirmPassword().isEmpty()) {
                user.setConfirmPassword(updatedUser.getConfirmPassword());
            }

            return new ResponseEntity<>(userRepo.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        Optional<Users> user = userRepo.findByUserId(userId);
        if (user.isPresent()) {
            userRepo.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //codes for password reset and forgot generate passcode

//    @PostMapping("/api/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
//        Optional<Users> userOptional = userRepo.findByEmail(email);
//        if (userOptional.isPresent()) {
//            Users user = userOptional.get();
//            String generatedCode = generateRandomCode();
//
//            VerificationCode verificationCode = new VerificationCode();
//            verificationCode.setUser(user);
//            verificationCode.setEmail(user.getEmail());
//            verificationCode.setCode(generatedCode);
//            verificationCode.setExpirationTime(generateExpirationTime());
//
//            verificationCodeRepo.save(verificationCode);
//
//            // Logic to send the code via email goes here
//
//            return new ResponseEntity<>("Passcode sent to your email.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Email not found.", HttpStatus.NOT_FOUND);
//        }
//    }

// Forgot Password Endpoint
    @PostMapping("/api/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        Optional<Users> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            String generatedCode = generateRandomCode();

            VerificationCode verificationCode = new VerificationCode();
            verificationCode.setUser(user);
            verificationCode.setEmail(user.getEmail());
            verificationCode.setCode(generatedCode);
            verificationCode.setExpirationTime(generateExpirationTime());

            verificationCodeRepo.save(verificationCode);

            emailService.sendSimpleEmail(
                    user.getEmail(),
                    "Password Reset Verification Code",
                    "Your verification code is: " + generatedCode
            );

            return new ResponseEntity<>("Passcode sent to your email.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email not found.", HttpStatus.NOT_FOUND);
        }
    }

//    // Verify Code Endpoint
//    @PostMapping("/api/verify-code")
//    public ResponseEntity<String> verifyCode(@RequestBody CodeVerificationRequest request) {
//        Optional<VerificationCode> codeOptional = verificationCodeRepo.findByCodeAndEmail(request.getCode(), request.getEmail());
//
//        if (codeOptional.isPresent() && codeOptional.get().getExpirationTime().after(new Date())) {
//            return new ResponseEntity<>("Code verified. Proceed to reset password.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Invalid or expired code.", HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/api/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody CodeVerificationRequest request) {
        System.out.println("Received request: Email - " + request.getEmail() + ", Code - " + request.getCode());

        Optional<VerificationCode> codeOptional = verificationCodeRepo.findByCodeAndEmail(request.getCode(), request.getEmail());

        if (codeOptional.isPresent() && codeOptional.get().getExpirationTime().after(new Date())) {
            return new ResponseEntity<>("Code verified. Proceed to reset password.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired code.", HttpStatus.BAD_REQUEST);
        }
    }


    // Reset Password Endpoint
    @PostMapping("/api/reset-password")
    @Transactional  // Add this annotation to ensure transactions are handled
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody PasswordResetRequest request) {
        System.out.println("Reset password request received for email: " + request.getEmail());

        Optional<Users> userOptional = userRepo.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            // Validate that newPassword and confirmPassword are provided and match
            if (request.getNewPassword() == null || request.getConfirmPassword() == null ||
                    request.getNewPassword().isEmpty() || request.getConfirmPassword().isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "New password and confirmation cannot be empty.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Passwords do not match.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Update the user's password
            user.setPassword(request.getNewPassword());
            user.setConfirmPassword(request.getConfirmPassword());
            userRepo.save(user);

            // Delete verification code after successful password reset
            verificationCodeRepo.deleteByEmail(request.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    // Handle 405 Method Not Allowed for any other HTTP methods
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<String> handleInvalidMethods() {
        return new ResponseEntity<>("Method not allowed for this endpoint.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(value = "/verify-code", method = RequestMethod.GET)
    public ResponseEntity<String> handleInvalidGetForVerifyCode() {
        return new ResponseEntity<>("GET method not supported on /verify-code", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(method = {RequestMethod.GET})
    public ResponseEntity<String> handleInvalidGetForResetPassword() {
        return new ResponseEntity<>("GET method not supported for /reset-password", HttpStatus.METHOD_NOT_ALLOWED);
    }




}

