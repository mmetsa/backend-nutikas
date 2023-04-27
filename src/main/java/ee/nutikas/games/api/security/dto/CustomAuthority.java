package ee.nutikas.games.api.security.dto;

import lombok.Data;

@Data
public class CustomAuthority {

    private Long schoolId;
    private Long userId;
    private String role;

}
