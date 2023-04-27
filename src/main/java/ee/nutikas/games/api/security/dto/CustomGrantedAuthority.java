package ee.nutikas.games.api.security.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {

    private final CustomAuthority customAuthority;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomGrantedAuthority(String role, Long schoolId, Long userId) {
        this.customAuthority = new CustomAuthority();
        customAuthority.setRole(role);
        customAuthority.setSchoolId(schoolId);
        customAuthority.setUserId(userId);
    }

    @Override
    public String getAuthority() {
        try {
            return objectMapper.writeValueAsString(this.customAuthority);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}