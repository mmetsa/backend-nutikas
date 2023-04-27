package ee.nutikas.games.api.auth.model;

import ee.nutikas.games.api.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserModel extends BaseModel {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birthday")
    private LocalDate birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "coins")
    private Long coins;

    @Column(name = "experience")
    private Long experience;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<UserRoleSchoolClassModel> userRoles = new HashSet<>();

}
