package nsd.open.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문항타입 DTO 객체")
public record QuestionType() {
}
