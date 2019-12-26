package cn.edu.ncu.bbs.controller;

import cn.edu.ncu.bbs.entity.Answer;
import cn.edu.ncu.bbs.entity.CommonResult;
import cn.edu.ncu.bbs.entity.Page;
import cn.edu.ncu.bbs.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController

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
    @PostMapping("/alert/answer")
    public CommonResult addAnswer(HttpSession session, Answer answer) {
        return answerService.addAnswer(session, answer);
    }

    /**
     * 采纳的回答置顶，
     *
     * @return
     */
    @GetMapping("/answer")
    public List<Answer> findAnswer(Integer questionId) {
        return answerService.findAnswer(questionId);
    }

    /**
     * 用户对自己提出的问题的回答选择采纳
     */
    @PatchMapping("/alert/answer/setAccept")
    public CommonResult setAccept(HttpSession session, int answerId) {
        return answerService.setAccept(session, answerId);
    }

    /**
     * 查看用户所有有过的回答,返回一个问题和用户对该问题的回答
     */
    @GetMapping("/user/{user}/answer")
    public Page<Answer> getUsersAnswerPage(Integer userId,
                                        @RequestParam(defaultValue = "1",required = false) int pageNum){
        return answerService.getMyAnswerPage(userId,pageNum);
    }
}
