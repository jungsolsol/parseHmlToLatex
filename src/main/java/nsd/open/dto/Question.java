package nsd.open.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.LocalDateTime;
@Builder
@Schema(description = "문항 [머리글, 문항줄기] DTO 객체")
public record Question(
        @Schema(description = "문항 ID")
        Long id,
        @Schema(description = "문항 타입 ID")

        Long qtId,
        Character useYn,
        Character openYn,
        String rgstId,
        LocalDateTime rgstDt,
        String uptsrId,
        LocalDateTime uptsrDt,
        Integer passage,
        Integer main,
        Long mainId,
        String controlNo,
        Integer waitingTime,
        Integer audioTime,
        Integer answerTime,
        Long assignCnt,
        Long stareCnt,
        Long correctCnt,
        String answerType,
        String keyword,
        String mediatags,
        Double point,
        Integer answerCnt,
        @Schema(description = "머리글")
        String headerText,
        @Schema(description = "문항줄기")
        String question,
        String schLvlCode,
        String gradeCode,
        String grdGroupCode,
        String termCode,
        String subjCode,
        String unitCode,
        String unit,
        String sessionCode,
        String session,
        String sub1sessionCode,
        String sub1session,
        String topicCode,
        String topic,
        String domainCode,
        String subDomainCode,
        String achCode,
        String subAch,
        String subAchRubric,
        String evalStandardCode,
        String evalStandRubric,
        String funcCode,
        String compDomainCode,
        String compDomain,
        String keyConceptCode,
        String generalCode,
        String contentsCode,
        String diffCode,
        Integer quesAnst,
        @Schema(description = "문항 원본 ID")
        String originId
) {
}
