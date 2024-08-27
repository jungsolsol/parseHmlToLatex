package nsd.open.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParseQuestionDto{
    private String questionId;
    private String questionText;
    private String answer;
}
