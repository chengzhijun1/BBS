package cn.edu.ncu.bbs.service.impl;

import cn.edu.ncu.bbs.entity.*;
import cn.edu.ncu.bbs.mapper.AnswerMapper;
import cn.edu.ncu.bbs.mapper.QuestionMapper;
import cn.edu.ncu.bbs.service.AnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        User user = (User) session.getAttribute("loginUser");
//        if (user == null){
////            result.setCode("404");
////            result.setMsg("长时间未登录，session失效！请重新登录");
////            return result;
////        }
//        answer.setUserId(user.getUid());
        try{
            answerMapper.insert(answer);
            result.setCode("200");
            result.setMsg("成功回答");
        } catch (Exception e) {
            result.setCode("404");
            result.setMsg("回答失败！");
        }
        return result;
    }

    @Override
    public List<Answer> findAnswer(String probId) {
        List<Answer> answers = answerMapper.listByProbId(Integer.valueOf(probId));
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
        Question question = questionMapper.findQuestionById(answer.getProbId());
        if(user.getUserId() == question.getUserId()){
            answerMapper.setIsAcceptTrue(answerId);
            // 该问题的userId进行积分奖励，自己扣除设置的积分
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
     * 用户查看自己的所有参与过的回答
     * @return
     */
    @Override
    public Page<Answer> getMyAnswerPage(HttpSession session, int currentPage) {
//        User user = (User) session.getAttribute("loginUser");
        int userId = 1;//user.getUid();
        Page<Answer> answerPage = new Page<>(answerMapper.countByUserId(userId),10);
        answerPage.setCurrentPage(currentPage);
        int start = answerPage.getStart();
        int size = answerPage.getPageSize();
        answerPage.setList(answerMapper.getAnswerPageByUserId(userId,start,size));
        return answerPage;
    }
}
