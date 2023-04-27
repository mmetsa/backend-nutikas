package ee.nutikas.games.api.auth.dto;

import lombok.Data;

@Data
public class RefreshRequest {

    private String refreshToken;

}
