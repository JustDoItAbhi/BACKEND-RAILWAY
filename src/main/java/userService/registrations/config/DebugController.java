package userService.registrations.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Temporary debug endpoint
@RestController
public class DebugController {

    @GetMapping("/api/debug/cors")
    public String debugCors(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "https://backend-railway-production-f154.up.railway.app");
        return "CORS Debug - Headers should be set";
    }
}
