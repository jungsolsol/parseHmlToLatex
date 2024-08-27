package nsd.open.service;

import lombok.RequiredArgsConstructor;
import nsd.open.dao.QuestionDao;
import nsd.open.dto.ParseQuestionDetailDto;
import nsd.open.dto.ParseQuestionDto;
import nsd.open.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


        Map<String, ParseQuestionDetailDto> detailDtoMap = questionDetailDtoList.stream()
                .collect(Collectors.toMap(ParseQuestionDetailDto::getQuestionId, dto -> dto));

        System.out.println(detailDtoMap);
        /**
         * Todo questionList와 questionDetailDtoList의 questionId 값이
         *  같은 Dto들 끼리 더해 QuestionDto에 추가하여 insert하는 기능
         */
        List<QuestionDto> questionDtos = new ArrayList<>();

        for (ParseQuestionDto parseQuestionDto : questionList) {
            String questionId = parseQuestionDto.getQuestionId();
            ParseQuestionDetailDto detailDto = detailDtoMap.get(questionId);

//            if (detailDto != null) {
//                QuestionDto mergedDto = questionDtos(parseQuestionDto, detailDto);
//                questionDtos.add(mergedDto);
//            } else {
//                System.out.println("No matching detail found for questionId: " + questionId);
//            }
        }

//        QuestionDto.builder()
//                .originId()
//                .achCode()
//                .useYn()
//                .schLvlCode()
//                .diffCode()
//                .


    }


}
