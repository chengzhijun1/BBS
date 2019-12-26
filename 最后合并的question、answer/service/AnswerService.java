package cn.edu.ncu.bbs.service;

import cn.edu.ncu.bbs.entity.Answer;
import cn.edu.ncu.bbs.entity.CommonResult;
import cn.edu.ncu.bbs.entity.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public interface AnswerService {
    public CommonResult addAnswer(HttpSession session, Answer answer);
    public List<Answer> findAnswer(Integer questionId);
    public CommonResult setAccept(HttpSession session,int answerId);
    public Page<Answer> getMyAnswerPage(Integer userId, int currentPage);
}
