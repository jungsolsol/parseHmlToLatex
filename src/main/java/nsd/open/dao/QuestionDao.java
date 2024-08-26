package nsd.open.dao;

import nsd.open.dto.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionDao {
    List<Question> getAllQuestion();
}
