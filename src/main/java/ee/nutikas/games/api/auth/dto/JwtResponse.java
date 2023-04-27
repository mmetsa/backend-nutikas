package ee.nutikas.games.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private List<String> roles;

}
