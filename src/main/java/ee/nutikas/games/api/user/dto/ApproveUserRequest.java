package ee.nutikas.games.api.user.dto;

import ee.nutikas.games.api.enums.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApproveUserRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Role role;

    private Long classId;

}
