package nsd.open.dto;

import lombok.Builder;

@Builder
public record ParseQuestion(String questionId, String questionText, String answer){
}
