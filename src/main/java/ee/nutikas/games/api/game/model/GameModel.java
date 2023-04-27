package ee.nutikas.games.api.game.model;

import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.enums.GameType;
import ee.nutikas.games.api.model.BaseModel;
import ee.nutikas.games.api.questionset.model.QuestionSetModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "game")
public class GameModel extends BaseModel {

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "name")
    private String name;

    @Column(name = "starting_time")
    private LocalDateTime startingTime;

    @Column(name = "ending_time")
    private LocalDateTime endingTime;

    @Column(name = "hints_enabled")
    private Boolean hintsEnabled;

    @Column(name = "allowed_play_times")
    private Integer allowedPlayTimes;

    @Column(name = "point_penalty")
    private Integer pointPenalty;

    @Column(name = "points_per_question")
    private Integer pointsPerQuestion;

    @Column(name = "power_ups_enabled")
    private Boolean powerUpsEnabled;

    @Column(name = "streaks_enabled")
    private Boolean streaksEnabled;

    @Column(name = "game_time")
    private Integer gameTime;

    @Column(name = "game_type")
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @Column(name = "code")
    private String code;

    @ManyToOne
    private UserModel creator;

    @ManyToOne
    private QuestionSetModel questionSet;

}
