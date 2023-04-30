package ee.nutikas.games.api.web.validation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvalidFieldError {

    private String fieldName;
    private String message;

}
