package ee.nutikas.games.api.school.model;

import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import ee.nutikas.games.api.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "class")
public class ClassModel extends BaseModel {

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolModel school;

    @OneToMany(mappedBy = "classModel")
    private List<UserRoleSchoolClassModel> userRoleSchoolClass;
}
