package nsd.open.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ParseQuestionDetailDto {
    private String questionId;


    @Schema(description = "성취코드")
    private String achCode;
}
