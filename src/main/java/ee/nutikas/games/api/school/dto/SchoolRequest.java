package ee.nutikas.games.api.school.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SchoolRequest {

    @NotEmpty(message = "Name must not be empty")
    private String name;

    private String shortName;

    private String address;

    private String schoolEmail;

    private String phone;

    @NotEmpty(message = "Admin email must be set")
    private String adminEmail;

    @NotEmpty(message = "Admin username must be set")
    private String username;

    @NotEmpty(message = "Password must be set")
    private String password;

    @NotEmpty(message = "Code must be set")
    private String code;
}
