package com.atm.client.user.create;

import com.atm.client.dto.UserDTO;
import com.atm.client.user.UserApiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("API. CreateUserService. Negative scenario")
public class NegativeCreateUserTest extends UserApiBaseTest {
    private final String oversizeUsername = "testUsernameOverSizeTEXTTEXTTEXT" +
            "TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT" +
            "TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT";

    private final String oversizePassword = "testPasswordOverSizeTEXTTEXTTEXT" +
            "TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT" +
            "TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT";

    @Test
    @DisplayName("Create user with all null attributes request")
    public void testCreateUserWithAllNullAttributes() {
        UserDTO userDTO = new UserDTO(null, null, null);
        Optional<UserDTO> responseUserDto = createUserService.sendUserCreationRequestToServer(userDTO);

        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }

    @Test
    @DisplayName("Create user with more than maximum username size value")
    public void testCreateUserWithMoreThanMaximumSizeValueUsername() {
        UserDTO userDTO = new UserDTO(oversizeUsername, "testPasswordOversize", null);
        Optional<UserDTO> responseUserDto = createUserService.sendUserCreationRequestToServer(userDTO);

        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }

    @Test
    @DisplayName("Create user with more than maximum password size value")
    public void testCreateUserWithMoreThanMaximumSizeValuePassword() {
        UserDTO userDTO = new UserDTO("testUsernameOversize", oversizePassword, null);
        Optional<UserDTO> responseUserDto = createUserService.sendUserCreationRequestToServer(userDTO);

        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }

    @Test
    @DisplayName("Create user with more than maximum password and username size value")
    public void testCreateUserWithMoreThanMaximumSizeValuePasswordAndUsername() {
        UserDTO userDTO = new UserDTO(oversizeUsername, oversizePassword, null);
        Optional<UserDTO> responseUserDto = createUserService.sendUserCreationRequestToServer(userDTO);

        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }

    @Test
    @DisplayName("Create user with more than empty password and username size value")
    public void testCreateUserWithEmptyPasswordAndUsernameValues() {
        UserDTO userDTO = new UserDTO("", "", null);
        Optional<UserDTO> responseUserDto = createUserService.sendUserCreationRequestToServer(userDTO);

        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }
}
