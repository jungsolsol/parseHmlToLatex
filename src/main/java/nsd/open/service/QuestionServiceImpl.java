package nsd.open.service;

import lombok.RequiredArgsConstructor;
import nsd.open.dao.QuestionDao;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionDao questionDao;
}
