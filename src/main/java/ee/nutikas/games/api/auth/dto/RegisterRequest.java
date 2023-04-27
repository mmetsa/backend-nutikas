package ee.nutikas.games.api.auth.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {

    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private Long schoolId;
    private Long classId;

}
