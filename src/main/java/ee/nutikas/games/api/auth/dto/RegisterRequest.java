package ee.nutikas.games.api.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class RegisterRequest {

    @NotEmpty(message = "Nickname must be set")
    private String nickname;
    @NotEmpty(message = "Password must be set")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    @NotNull(message = "School must be chosen")
    private Long schoolId;
    @NotNull(message = "Class must be chosen")
    private Long classId;

}
