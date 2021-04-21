package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public UserListDTO create(UserListDTO userListDTO){

        User user = userMapper.userFromListDTO(userListDTO);
        user.setPassword(encoder.encode(userListDTO.getPassword()));

        Set<String> rolesStr = userListDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return userMapper.userListDtoFromUser(user);
    }

    public UserListDTO update(Long id, UserListDTO userListDTO){
        User user = userRepository.findById(id).orElseThrow(()->new EntityExistsException("User not found"));
        user.setUsername(userListDTO.getUsername());

        if(!userListDTO.getPassword().equals(""))
        {
            user.setPassword(encoder.encode(userListDTO.getPassword()));
        }

        return userMapper.userListDtoFromUser(userRepository.save(user));
    }


    public void deleteAll(){
        userRepository.deleteAll();
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

}
