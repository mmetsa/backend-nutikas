package ee.nutikas.games.api.school.model;

import ee.nutikas.games.api.model.BaseModel;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "school_code")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolCodeModel extends BaseModel {

    private String code;

    @OneToOne
    @JoinColumn(name = "school_id")
    private SchoolModel schoolModel;

}
