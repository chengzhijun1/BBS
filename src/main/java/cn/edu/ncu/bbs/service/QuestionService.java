package cn.edu.ncu.bbs.service;

import cn.edu.ncu.bbs.entity.CommonResult;
import cn.edu.ncu.bbs.entity.Page;
import cn.edu.ncu.bbs.entity.Question;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public interface QuestionService {
    public CommonResult addQuestion(HttpSession session, Question question);
    public Page<Question> findQuestionByKeyWord(String keyWord, int currentPage);
    public Page<Question> getPageQuestion(int currentPage, String orderBy, String flag);
    public Page<Question> getMyPageQuestion(HttpSession session, int currentPage, String orderBy, String flag);
//    public Page<Problem> getUserPageQuestion
//    public Problem findQuestionById(Integer questionId);
    public Map<String,Object> getQuestionDetail(Integer questionId);
    public CommonResult alertQuestion(HttpSession session,Question question);

}
