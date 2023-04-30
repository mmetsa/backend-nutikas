package ee.nutikas.games.api.security.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomAuthority implements Serializable {

    private Long schoolId;
    private Long userId;
    private String role;
    private Boolean disabled;

}
