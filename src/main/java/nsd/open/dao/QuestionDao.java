package nsd.open.dao;

import nsd.open.dto.QuestionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionDao {
    List<QuestionDto> getAllQuestion();
}
