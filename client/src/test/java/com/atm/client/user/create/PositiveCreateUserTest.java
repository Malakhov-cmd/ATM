package com.atm.client.user.create;

import com.atm.client.dto.UserDTO;
import com.atm.client.user.UserApiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("API. CreateUserService. Positive scenario")
public class PositiveCreateUserTest extends UserApiBaseTest {

    @Test
    @DisplayName("Create user with default data")
    public void testCreationUser() {
        UserDTO userDTO = new UserDTO("testName", "testPassword", null);
        Optional<UserDTO> responseUserDto = createUserService.sendUserCreationRequestToServer(userDTO);

        assertEquals("testName", responseUserDto.get().getUsername());
    }

    @Test
    @DisplayName("Create user with data from Google")
    public void testCreationUserWithGoogleData(){
        OAuth2User oAuth2User = mock(OAuth2User.class);

        when(oAuth2User.getAttribute("name")).thenReturn("testNameGoogle");
        when(oAuth2User.getAttribute("sub")).thenReturn("testPasswordGoogle");

        Optional<UserDTO> responseUserDto = createUserService.createUserFromOauthData(oAuth2User);

        assertEquals("testNameGoogle", responseUserDto.get().getUsername());
    }

    @Test
    @DisplayName("Create user with data from Github")
    public void testCreationUserWithGithubData(){
        OAuth2User oAuth2User = mock(OAuth2User.class);

        when(oAuth2User.getAttribute("name")).thenReturn("testNameGithub");
        when(oAuth2User.getAttribute("id")).thenReturn("testPasswordGithub");

        Optional<UserDTO> responseUserDto = createUserService.createUserFromOauthData(oAuth2User);

        assertEquals("testNameGithub", responseUserDto.get().getUsername());
    }
}
