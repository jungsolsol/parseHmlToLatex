package nsd.open.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Schema(description = "문항 [머리글, 문항줄기] DTO 객체")
@Builder
@Data
public class QuestionDto {
        @Schema(description = "문항 ID")
        private Long id;
        @Schema(description = "문항 타입 ID")
        private Long qtId;
        private String useYn;
        private String openYn;
        private String rgstId;
        private LocalDateTime rgstDt;
        private String uptsrId;
        private LocalDateTime uptsrDt;
        private Integer passage;
        private Integer main;
        private Long mainId;
        private String controlNo;
        private Integer waitingTime;
        private Integer audioTime;
        private Integer answerTime;
        private Long assignCnt;
        private Long stareCnt;
        private Long correctCnt;
        private String answerType;
        private String keyword;
        private String mediatags;
        private Double point;
        private Integer answerCnt;
        @Schema(description = "머리글")
        private String headerText;
        @Schema(description = "문항줄기")
        private String question;
        private String schLvlCode;
        private String gradeCode;
        private String grdGroupCode;
        private String termCode;
        private String subjCode;
        private String unitCode;
        private String unit;
        private String sessionCode;
        private String session;
        private String sub1sessionCode;
        private String sub1session;
        private String topicCode;
        private String topic;
        private String domainCode;
        private String subDomainCode;
        private String achCode;
        private String subAch;
        private String subAchRubric;
        private String evalStandardCode;
        private String evalStandRubric;
        private String funcCode;
        private String compDomainCode;
        private String compDomain;
        private String keyConceptCode;
        private String generalCode;
        private String contentsCode;
        private String diffCode;
        private Integer quesAnst;
        @Schema(description = "문항 원본 ID")
        private String originId;
}
