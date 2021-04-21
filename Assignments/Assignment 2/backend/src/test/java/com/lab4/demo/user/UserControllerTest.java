package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USERS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void create() throws Exception {
        UserListDTO user = UserListDTO.builder()
                .username("Test")
                .password("password")
                .email("a@a.com")
                .build();

        when(userService.create(user)).thenReturn(user);
        ResultActions result = performPostWithRequestBody(USERS, user);
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));

    }

    @Test
    void edit() throws Exception {
        UserListDTO user = UserListDTO.builder()
                .id(1L)
                .username("Test")
                .password("password")
                .email("a@a.com")
                .build();

        when(userService.update(user.getId(), user)).thenReturn(user);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS+ENTITY, user, user.getId().toString());
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void deleteUser() throws Exception {
        UserListDTO user = UserListDTO.builder()
                .id(1L)
                .username("Test")
                .password("password")
                .email("a@a.com")
                .build();

        ResultActions result = performDeleteWIthPathVariable(USERS+ENTITY, user.getId());
        result.andExpect(status().isOk());
    }

}