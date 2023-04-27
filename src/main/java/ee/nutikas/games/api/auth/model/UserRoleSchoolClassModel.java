package ee.nutikas.games.api.auth.model;

import ee.nutikas.games.api.model.BaseModel;
import ee.nutikas.games.api.school.model.ClassModel;
import ee.nutikas.games.api.school.model.SchoolModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
public class UserRoleSchoolClassModel extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roles_id")
    private RoleModel role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private UserModel user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private SchoolModel school;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private ClassModel classModel;

    @Column(name = "expire_date")
    private Timestamp expirationDate = Timestamp.valueOf(LocalDateTime.now().plusYears(1));

    @Column(name = "disabled")
    private Boolean disabled = true;
}
