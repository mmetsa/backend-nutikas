package ee.nutikas.games.api.school.model;

import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import ee.nutikas.games.api.model.BaseModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "school")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SchoolModel extends BaseModel {

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "school")
    private Set<UserRoleSchoolClassModel> schoolUsers;

    @OneToMany(mappedBy = "school")
    private List<ClassModel> classes;

}



