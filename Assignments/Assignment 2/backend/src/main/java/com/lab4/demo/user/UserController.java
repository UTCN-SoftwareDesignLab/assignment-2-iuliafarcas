package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO createUser(@RequestBody UserListDTO userListDTO){
        return userService.create(userListDTO);
    }

    @PatchMapping(ENTITY)
    public UserListDTO editUser(@PathVariable Long id, @RequestBody UserListDTO userListDTO){
        return userService.update(id, userListDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll(){
        userService.deleteAll();
    }
}
