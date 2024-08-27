package nsd.open.service;

import nsd.open.dto.ParseQuestion;
import nsd.open.dto.Question;

import java.util.List;

public interface QuestionService {
    List<Question>  getAllQuestion();

    void insertParseXmlQuestion(List<ParseQuestion> questionList);
}
