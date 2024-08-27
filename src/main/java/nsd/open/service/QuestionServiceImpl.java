package nsd.open.service;

import lombok.RequiredArgsConstructor;
import nsd.open.dao.QuestionDao;
import nsd.open.dto.ParseQuestionDetailDto;
import nsd.open.dto.ParseQuestionDto;
import nsd.open.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionDao questionDao;
    @Override
    public List<QuestionDto> getAllQuestion() {

        return questionDao.getAllQuestion();
    }

    @Override
    public void insertParseXmlQuestion(List<ParseQuestionDto> questionList, List<ParseQuestionDetailDto> questionDetailDtoList) {


    }


}
