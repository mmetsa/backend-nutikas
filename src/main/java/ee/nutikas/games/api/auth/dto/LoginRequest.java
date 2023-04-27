package ee.nutikas.games.api.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String nickname;
    private String password;

}
