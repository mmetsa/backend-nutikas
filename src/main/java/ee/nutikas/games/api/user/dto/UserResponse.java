package ee.nutikas.games.api.user.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
    private String className;


}
