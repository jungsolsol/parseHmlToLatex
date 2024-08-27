package nsd.open.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParseQuestionDetailDto {

    @Schema(description = "문항ID")
    private String questionId;

    @Schema(description = "성취코드")
    private String achCode;

    @Schema(description = "난이도")
    private String diffCode;

    @Schema(description = "대단원 아이디")
    private String unitCode;

    @Schema(description = "대단원명")
    private String unit;

    @Schema(description = "중단원 아이디")
    private String sessionCode;

    @Schema(description = "중단원명")
    private String session;

    @Schema(description = "소단원 아이디")
    private String sub1sessionCode;

    @Schema(description = "소단원명")
    private String sub1session;

    @Schema(description = "토픽 아이디")
    private String topicCode;

    @Schema(description = "토픽명")
    private String topic;

    @Schema(description = "학교급", name = "sch_lvl_code")
    private String schLvlCode;

    @Schema(description = "학기")
    private String termCode;

}
