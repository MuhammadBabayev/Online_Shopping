package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.entity.User;
import org.example.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
//@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveOneUser(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        //CustomException
        return userService.getByUserId(userId);
    }

//    @GetMapping("/{userName}")
//    public User getByUserName(@PathVariable String userName){
//        return userService.getByUserName(userName);
//    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId,@RequestBody User newUser){
        return userService.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
