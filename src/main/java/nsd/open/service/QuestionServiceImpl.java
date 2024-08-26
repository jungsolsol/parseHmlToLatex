package nsd.open.service;

import lombok.RequiredArgsConstructor;
import nsd.open.dao.QuestionDao;
import nsd.open.dto.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionDao questionDao;


    @Override
    public List<Question> getAllQuestion() {

        return questionDao.getAllQuestion();
    }
}
