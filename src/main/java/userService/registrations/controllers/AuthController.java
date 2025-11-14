package userService.registrations.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://backend-railway-production-49cf.up.railway.app", allowCredentials = "true")
public class AuthController {
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email,
//                                   @RequestParam String password,
//                                   HttpServletRequest request) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (authentication != null && authentication.isAuthenticated()) {
//                // User is already authenticated (likely via form login)
//                Map<String, Object> response = new HashMap<>();
//                response.put("message", "Login successful");
//                response.put("authenticated", true);
//                response.put("user", authentication.getPrincipal());
//
//                return ResponseEntity.ok(response);
//            } else {
//                // Authentication failed
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body("{\"error\": \"Invalid credentials\", \"authenticated\": false}");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("{\"error\": \"Login failed: " + e.getMessage() + "\"}");
//        }
//    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Return user details
            return ResponseEntity.ok(authentication.getPrincipal());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/session-info")
    public ResponseEntity<?> getSessionInfo(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(authentication.getPrincipal());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"authenticated\": false}");
    }

    @PostMapping("/check-session")
    public ResponseEntity<?> checkSession(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body("{\"authenticated\": true}");
        }
        return ResponseEntity.ok().body("{\"authenticated\": false}");
    }

    // Add this endpoint to manually check session
    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            return ResponseEntity.ok().body("{\"authenticated\": true}");
        }
        return ResponseEntity.ok().body("{\"authenticated\": false}");
    }
}