package nsd.open.service;

import nsd.open.dto.ParseQuestionDto;
import nsd.open.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto>  getAllQuestion();

    void insertParseXmlQuestion(List<ParseQuestionDto> questionList);
}
