package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        //List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();
        List<UserListDTO> userMinimalDTOS = userService.allUsersForList();


        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }

    @Test
    void create(){
        roleRepository.save(Role.builder().name(ERole.EMPLOYEE).build());

        UserListDTO user = UserListDTO.builder()
                .username("Test")
                .password("password")
                .email("a@a.com")
                .build();

        user = userService.create(user);
        Assertions.assertNotNull(user.getId());

    }

    @Test
    void delete(){
        roleRepository.save(Role.builder().name(ERole.EMPLOYEE).build());

        UserListDTO user = UserListDTO.builder()
                .username("Test")
                .password("password")
                .email("a@a.com")
                .build();

        user = userService.create(user);
        userService.deleteById(user.getId());

        List<UserListDTO> users = userService.allUsersForList();

        Assertions.assertEquals(0, users.size());
    }

    @Test
    void edit(){
        roleRepository.save(Role.builder().name(ERole.EMPLOYEE).build());

        UserListDTO user = UserListDTO.builder()
                .username("Test")
                .password("password")
                .email("a@a.com")
                .build();

        user = userService.create(user);
        user.setUsername("Update");
        UserListDTO updated = userService.update(user.getId(), user);

        Assertions.assertEquals(updated.getUsername(), "Update");
    }
}