package ee.nutikas.games.api.school.dto;

import lombok.Data;

import java.util.List;

@Data
public class SchoolResponse {

    private Long id;
    private String name;
    private String shortName;
    private String address;
    private String email;
    private String phone;
    private List<ClassResponse> classes;
}
