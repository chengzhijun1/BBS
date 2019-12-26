package cn.edu.ncu.bbs.service.impl;

import cn.edu.ncu.bbs.entity.*;
import cn.edu.ncu.bbs.mapper.AnswerMapper;
import cn.edu.ncu.bbs.mapper.QuestionMapper;
import cn.edu.ncu.bbs.service.AnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Resource
    AnswerMapper answerMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    CommonResult result;

    /**
     * 用户对一个问题进行回答进行
     * @param session
     * @param answer
     * @return
     */
    @Override
    public CommonResult addAnswer(HttpSession session, Answer answer) {
        try{
            User user = (User) session.getAttribute("loginUser");   //首先取出session
            answer.setUserId(user.getUserId());
            answerMapper.insert(answer);
            result.setCode("200");
            result.setMsg("成功回答");
        } catch (Exception e) {
            result.setCode("404");
            result.setMsg("回答失败！");
        }
        return result;
    }

    /**
     * 这个貌似没有用到
     * @param questionId
     * @return
     */
    @Override
    public List<Answer> findAnswer(Integer questionId) {
        List<Answer> answers = answerMapper.listByQuestionId(questionId);
        Collections.sort(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer o1, Answer o2) {
                return o1.getIsAccept().compareTo(o2.getIsAccept());
            }
        });
        return answers;
    }

    /**
     * 用户点击自己发布的问题，将某一个回答设置为采纳
     * @param session
     * @param answerId
     * @return
     */
    @Override
    public CommonResult setAccept(HttpSession session, int answerId) {
        User user = (User) session.getAttribute("loginUser");
        Answer answer = answerMapper.findAnswerByid(answerId);
        Question question = questionMapper.getQuestionById1(answer.getQuestionId());
        System.out.println(question.getUserId());
        if(user.getUserId() == question.getUserId()){
            answerMapper.setIsAcceptTrue(answerId);
            // 该问题的userId进行积分奖励，自己扣除设置的积分
            answerMapper.delScore(1,question.getQuestionWard());    //减去提问人的分数.
            answerMapper.addScore(6,question.getQuestionWard());    //回答者加分。
            /**
             * 要用到usermapper下的setScore
             */
            result.setCode("200");
            result.setMsg("成功设置为已采纳");
        }
        else{
            result.setCode("404");
            result.setMsg("非本人发布的问题！无权限设置为采纳！");
        }
        return result;
    }

    /**
     * 查看用户的所有参与过的回答
     * @return
     */
    @Override
    public Page<Answer> getMyAnswerPage(Integer userId, int currentPage) {
        Page<Answer> answerPage = new Page<>(answerMapper.countByUserId(userId),10);
        answerPage.setCurrentPage(currentPage);
        int start = answerPage.getStart();
        int size = answerPage.getPageSize();
        answerPage.setList(answerMapper.getAnswerPageByUserId(userId,start,size));
        return answerPage;
    }
}
