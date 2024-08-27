package nsd.open.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HtmlRenderDto {
     private AnswerDto answer;
     private LatexDto latex;
     private ParseQuestionDto parseQuestion;

}
