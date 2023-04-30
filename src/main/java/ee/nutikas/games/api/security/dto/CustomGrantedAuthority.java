package ee.nutikas.games.api.security.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class CustomGrantedAuthority implements GrantedAuthority {

    private final CustomAuthority customAuthority;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomGrantedAuthority(String role, Long schoolId, Long userId, Boolean disabled) {
        this.customAuthority = new CustomAuthority();
        customAuthority.setRole(role);
        customAuthority.setSchoolId(schoolId);
        customAuthority.setUserId(userId);
        customAuthority.setDisabled(disabled);
    }

    @Override
    public String getAuthority() {
        try {
            return objectMapper.writeValueAsString(this.customAuthority);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}