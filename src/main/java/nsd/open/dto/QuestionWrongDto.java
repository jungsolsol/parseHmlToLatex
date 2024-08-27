package nsd.open.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문항 [해설, 개념영상] DTO 객체")
public class QuestionWrongDto {
}
