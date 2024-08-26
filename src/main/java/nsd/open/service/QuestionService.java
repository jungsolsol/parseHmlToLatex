package nsd.open.service;

import nsd.open.dto.Question;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionService {
    List<Question>  getAllQuestion();
}
