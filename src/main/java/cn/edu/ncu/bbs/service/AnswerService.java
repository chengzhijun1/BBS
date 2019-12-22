package cn.edu.ncu.bbs.service;

import cn.edu.ncu.bbs.entity.Answer;
import cn.edu.ncu.bbs.entity.CommonResult;
import cn.edu.ncu.bbs.entity.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public interface AnswerService {
    public CommonResult addAnswer(HttpSession session, Answer answer);
    public List<Answer> findAnswer(String probId);
    public CommonResult setAccept(HttpSession session,int answerId);
    public Page<Answer> getMyAnswerPage(HttpSession session, int currentPage);
}
