package ee.nutikas.games.api.school.dto;

import lombok.Data;

@Data
public class SchoolRequest {

    private String name;
    private String shortName;
    private String address;
    private String email;
    private String phone;
}
