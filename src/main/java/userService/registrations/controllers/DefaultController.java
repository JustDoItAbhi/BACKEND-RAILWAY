package userService.registrations.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DefaultController implements ErrorController {


        private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

        @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
        public ResponseEntity<Map<String, String>> chromeCheck() {
            logger.info("Chrome devtools well-known endpoint called");
            Map<String, String> response = Map.of(
                    "status", "ok",
                    "message", "Chrome DevTools configuration"
            );
            return ResponseEntity.ok(response);
        }

        // Handle error properly
        @RequestMapping("/error")
        public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", Instant.now());
            errorDetails.put("status", getErrorStatus(request));
            errorDetails.put("error", "Not Found");
            errorDetails.put("message", "The requested resource was not found");
            errorDetails.put("path", getOriginalUrl(request));

            logger.warn("Error handled for path: {}", getOriginalUrl(request));
            return new ResponseEntity<>(errorDetails, getHttpStatus(request));
        }

        private HttpStatus getHttpStatus(HttpServletRequest request) {
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            if (statusCode != null) {
                return HttpStatus.valueOf(statusCode);
            }
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        private int getErrorStatus(HttpServletRequest request) {
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            return statusCode != null ? statusCode : 500;
        }

        private String getOriginalUrl(HttpServletRequest request) {
            return (String) request.getAttribute("javax.servlet.error.request_uri");
        }
    }
