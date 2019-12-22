package cn.edu.ncu.bbs.controller;

import cn.edu.ncu.bbs.entity.Answer;
import cn.edu.ncu.bbs.entity.CommonResult;
import cn.edu.ncu.bbs.entity.Page;
import cn.edu.ncu.bbs.service.AnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/answer")

public class AnswerController {
    @Resource
    AnswerService answerService;
    @Resource
    CommonResult result;
    @Resource
    Page page;

    /**
     * 对一个问题进行回答
     *
     * @param session
     * @param answer
     * @return
     */
    @GetMapping("/addAnswer")
    public CommonResult addAnswer(HttpSession session, Answer answer) {
        return answerService.addAnswer(session, answer);
    }

    /**
     * 默认为采纳的回答置顶，
     *
     * @return
     */
    @GetMapping("/findAnswer")
    public List<Answer> findAnswer(String probId) {
        return answerService.findAnswer(probId);
    }

    /**
     * 用户对自己提出的问题的回答选择采纳
     */
    @GetMapping("/setAccept")
    public CommonResult setAccept(HttpSession session, int answerId) {
        return answerService.setAccept(session, answerId);
    }

    /**
     * 用户查看自己所有有过的回答
     */
    @GetMapping("/myAnswer")
    public Page<Answer> getMyAnswerPage(HttpSession session,int currentPage){
        return answerService.getMyAnswerPage(session,currentPage);
    }
}
