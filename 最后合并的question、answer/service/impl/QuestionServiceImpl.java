package cn.edu.ncu.bbs.service.impl;

import cn.edu.ncu.bbs.entity.*;
import cn.edu.ncu.bbs.mapper.AnswerMapper;
import cn.edu.ncu.bbs.mapper.QuestionMapper;
import cn.edu.ncu.bbs.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    QuestionMapper questionMapper;
    @Resource
    AnswerMapper answerMapper;
    @Resource
    CommonResult result;

    /**
     * 在session中获取用户，然后添加问题
     * @param session
     * @param question
     * @return
     */
    @Override
    public CommonResult addQuestion(HttpSession session, Question question) {
        try{
            User user = (User) session.getAttribute("loginUser");
            // 用户设置的悬赏不能大于积分余额
            if (user.getScore() < question.getQuestionWard()){
                result.setCode("404");
                result.setMsg("添加问题失败,用户不能发布悬赏多于自己余额的问题");
                return result;
            }
            question.setUserId(1);
            questionMapper.addQuestion(question);
            result.setCode("200");
            result.setMsg("成功发布问题");
        } catch (Exception e) {
            result.setCode("404");
            result.setMsg("添加问题失败");
        }
        return result;
    }

    /**
     * 查找到所有的问题，默认以阅读量进行排序显示
     * @param keyWord currentPage
     * @return
     */
    @Override
    public Page<Question> findQuestionByKeyWord(String keyWord, int currentPage) {
        Page<Question> pageQuestion  = new Page<Question>(questionMapper.countByKeyWord(keyWord),10);
        pageQuestion.setCurrentPage(currentPage);
        int start = pageQuestion.getStart();
        int size = pageQuestion.getPageSize();
        pageQuestion.setList(questionMapper.findQuestionByKeyWord(start,size,keyWord));
        return pageQuestion;
    }

    /**
     * 从指定的页数开始查找一页问题,并根据参数orderBy通过0为默认时间，1为奖励，2为回答数，3为阅读数进行排序，flag默认为2
     * @param currentPage
     * @param flag
     * @return
     */
    @Override
    public Page<Question> getPageQuestion(int currentPage, Integer orderBy, Integer flag) {
        // flag1和orderBy1，对参数进行变换，剩下写sql语句
        int[] flag1 = new int[]{1,0,2};    //不想用if语句变换flag也可以通过flag进行变换
        String[] orderBy1 = new String[]{"question_time","question_ward","answer_num","read_num"};

        Page<Question> pageQuestion  = new Page<Question>(questionMapper.countByFlag(flag1[flag]));//new一个有count数的page对象
        pageQuestion.setCurrentPage(currentPage);   //加入穿过来的当前页面的参数
        int start = pageQuestion.getStart();
        int size = pageQuestion.getPageSize();      //将start和多少天记录size进行查询
        /**
         * 进行排序返回，首先默认为时间，默认所有回答
         */

        pageQuestion.setList(questionMapper.getPageQuestionByFlagOrderBy(start,size,flag1[flag],orderBy1[orderBy]));

        return pageQuestion;
    }

    /**查找自己发布的问题
     * 用户从指定的页数开始查找自己的一页问题,并通过0为默认时间，1为奖励，2为回答数，3为阅读数进行排序，以及flag进行筛选
     * @param currentPage
     * @param orderBy
     * @param flag
     * @return
     */
    @Override
    public Page<Question> getUsersPageQuestion(int userId, int currentPage, Integer orderBy, Integer flag) {
        // flag1和orderBy1，对参数进行变换，剩下写sql语句
        int[] flag1 = new int[]{1,0,2};    //不想用if语句变换flag也可以通过flag进行变换
        String[] orderBy1 = new String[]{"question_time","question_ward","answer_num","read_num"};

        Page<Question> pageQuestion  = new Page<Question>(questionMapper.countByUserIdByFlag(userId,flag1[flag]),10);
        pageQuestion.setCurrentPage(currentPage);
        int start = pageQuestion.getStart();
        int size = pageQuestion.getPageSize();
        pageQuestion.setList(questionMapper.getUsersPageQuestionByFlagOrderBy(start,size,flag,orderBy1[orderBy],userId));

        return pageQuestion;
    }

    /**
     * 点击一个具体的问题，传入问题Id，返回该Question和对应的全部answer，和answer对应的user
     * @param questionId
     * @return
     */
    @Override
    public Map<String,Object> getQuestionDetail(Integer questionId) {
        Map<String,Object> map = new HashMap<>();
        Question question = questionMapper.getQuestionById(questionId);
        List<Answer> answers = answerMapper.getAnswerByQuestion(questionId);
        Collections.sort(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer o1, Answer o2) {
                return o1.getIsAccept().compareTo(o2.getIsAccept());
            }
        });
        map.put("Question", question);
        map.put("假装是个回答",answers);
        return map;
    }

    /**
     * 用户对自己发布的问题进行修改
     *
     * @param question
     * @return
     */
    @Override
    public CommonResult alertQuestion(HttpSession session, Question question) {
        User user = (User) session.getAttribute("loginUser");
        // 当用户尝试修改不是自己的文章并且自己不是管理员时，不允许修改
        if (user.getUserId() != question.getUserId() && user.getFlag() != 0) {
            result.setCode("405");
            result.setMsg("您没有权限进行修改！");
        }
        else {
            questionMapper.alertQuestion(question);
            result.setCode("400");
            result.setMsg("修改成功！");
        }
        return result;
    }
}
