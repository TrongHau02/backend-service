package com.javabackend.controller;

import com.javabackend.controller.request.UserCreateRequest;
import com.javabackend.controller.request.UserPasswordRequest;
import com.javabackend.controller.request.UserUpdateRequest;
import com.javabackend.controller.response.UserResponse;
import com.javabackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@Tag(name = "User Controller")
@Slf4j(topic = "USER-CONTROLLER")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user list", description = "Api retrieve user from database")
    @GetMapping("/users")
    public Map<String, Object> getAllUser(@RequestParam(required = false) String keyword,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1L);
        userResponse1.setFirstName("NTH");
        userResponse1.setLastName("Dev");
        userResponse1.setGender("MALE");
        userResponse1.setBirthday(new Date());
        userResponse1.setUsername("admin");
        userResponse1.setEmail("admin@gmail.com");
        userResponse1.setPhone("123456789");
        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2L);
        userResponse2.setFirstName("User");
        userResponse2.setLastName("Client");
        userResponse2.setGender("FEMALE");
        userResponse2.setBirthday(new Date());
        userResponse2.setUsername("client");
        userResponse2.setEmail("user@gmail.com");
        userResponse2.setPhone("1111111111");
        List<UserResponse> userList = List.of(userResponse1, userResponse2);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userList);
        return result;
    }

    @Operation(summary = "Get user detail", description = "Api retrieve user by id from database")
    @GetMapping("/users/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable Long userId) {
        UserResponse userDetail = new UserResponse();
        userDetail.setId(1L);
        userDetail.setFirstName("NTH");
        userDetail.setLastName("Dev");
        userDetail.setGender("MALE");
        userDetail.setBirthday(new Date());
        userDetail.setUsername("admin");
        userDetail.setEmail("admin@gmail.com");
        userDetail.setPhone("123456789");
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user");
        result.put("data", userDetail);
        return result;
    }

    @Operation(summary = "Create user", description = "Api add new user to database")
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserCreateRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "user created successfully");
        result.put("data", this.userService.save(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Update user", description = "Api update user to database")
    @PutMapping("/users")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserUpdateRequest request) {
        log.info("Update user: {}", request);

        this.userService.update(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "User updated successfully");
        result.put("data", "");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @Operation(summary = "Change password", description = "Api update password user to database")
    @PatchMapping("/users/change-password")
    public Map<String, Object> changePassword(@RequestBody UserPasswordRequest request) {
        log.info("Changing password for user: {}", request);
        this.userService.changePassword(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "Password updated successfully");
        result.put("data", "");
        return result;
    }

    @Operation(summary = "Inactivate user", description = "Api activate user from database")
    @DeleteMapping("/users/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        log.info("Delete user: {}", userId);
this.userService.delete(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "User deleted successfully");
        result.put("data", null);
        return result;
    }
}
