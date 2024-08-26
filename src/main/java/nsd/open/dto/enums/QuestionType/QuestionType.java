package nsd.open.dto.enums.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {

    LISTENING_SINGLE_ANSWER(4, "듣기", "L", "오디오를 듣고 특정 시간동안 하나의 정답을 선택", "LA"),
    LISTENING_SHORT_ANSWER(5, "듣기", "L", "오디오를 듣고 특정 시간동안 텍스트 답안 입력", "LB"),
    SEQUENCE_ORDER(14, "순서/서열", "O", "학생이 올바른 순서에 맞게 답안을 드래그하여 배치", "OA"),
    PERFORMANCE_ASSESSMENT(10, "수행평가", "P", "평가기준 채점", "PA"),
    MULTIPLE_CHOICE_MULTIPLE_ANSWERS(2, "객관식", "Q", "복수 정답을 선택", "QB"),
    MULTIPLE_CHOICE_SINGLE_ANSWER(1, "객관식", "Q", "하나의 정답을 선택", "QA"),
    READING_MULTIPLE_ANSWERS(7, "독해", "R", "지문을 읽고 주어진 질문에 맞는 복수 정답 선택", "RB"),
    READING_SINGLE_ANSWER(6, "독해", "R", "지문을 읽고 주어진 질문에 맞는 하나의 정답 선택", "RA"),
    READING_WORD_INSERTION(8, "독해", "R", "지문을 읽고 빈칸에 알맞은 낱말 선택", "RC"),
    READING_SHORT_ANSWER(9, "독해", "R", "지문을 읽고 주어진 질문에 맞는 텍스트 답안 입력", "RD"),
    SPEAKING_DIRECT_ANSWER(13, "말하기", "S", "학생이 질문에 대한 간단한 답변을 녹음", "SA"),
    TRUE_FALSE(3, "참/거짓", "T", "문제의 내용이 참인지 거짓인지 판단", "TA"),
    WRITING_SHORT_ANSWER(11, "쓰기", "W", "주어진 질문에 맞는 텍스트 답안 입력", "WA"),
    WRITING_ESSAY(12, "쓰기", "W", "100/300/600자 등 글자수 제한 쓰기 답안", "WB");


    private final int id;
    private final String major;
    private final String majorCode;
    private final String middle;
    private final String middleCode;

}
