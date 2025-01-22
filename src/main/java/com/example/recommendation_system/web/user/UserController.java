package com.example.recommendation_system.web.user;

import com.example.recommendation_system.dto.SignupRequest;
import com.example.recommendation_system.dto.UserDto;
import com.example.recommendation_system.services.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    ////use it to get the user info in the profile
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    ////// use it to update the user info in the profile
    @PostMapping("/update/{id}")
    public ResponseEntity<?> UpdateUser(@PathVariable Long id,@RequestBody SignupRequest updateUserRequest){
        UserDto createdUserDto = userService.updateUser(id,updateUserRequest);
        if (createdUserDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Updated");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }
}
