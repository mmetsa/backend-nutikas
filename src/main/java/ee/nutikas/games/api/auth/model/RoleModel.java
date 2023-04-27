package ee.nutikas.games.api.auth.model;

import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private Role name;

    @OneToMany(mappedBy = "role")
    private Set<UserRoleSchoolClassModel> roleUsers = new HashSet<>();

}
