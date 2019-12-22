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
            question.setUserId(user.getUserId());
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
        pageQuestion.setList(questionMapper.findPageQuestionByKeyWord(start,size,keyWord));
        // flag==1为被解决的，2为被解决的
//        if (flag.equals("1") || flag.equals("2")){
//            questions = QuestionMapper.findSolvedQuestion(Integer.valueOf(flag));
//        }
//        else{
//            questions = QuestionMapper.findAllQuestion();
//        }
//        if(orderBy.equals("1")){
//            Collections.sort(questions, new Comparator<Question>() {
//                @Override
//                public int compare(Question o1, Question o2) {
//                    return o2.getProbWard().compareTo(o1.getProbWard());
//                }
//            });
//        } else if (orderBy.equals("2")) {
//            Collections.sort(questions, new Comparator<Question>() {
//                @Override
//                public int compare(Question o1, Question o2) {
//                    return o2.getAnswerNum().compareTo(o1.getAnswerNum());
//                }
//            });
//        }else if (orderBy.equals("3")) {
//            Collections.sort(questions, new Comparator<Question>() {
//                @Override
//                public int compare(Question o1, Question o2) {
//                    return o2.getReadNum().compareTo(o1.getReadNum());
//                }
//            });
//        }
        return pageQuestion;
    }

    /**
     * 从指定的页数开始查找一页问题,并通过0为默认时间，1为奖励，2为回答数，3为阅读数进行排序
     * @param currentPage
     * @return
     */
    @Override
    public Page<Question> getPageQuestion(int currentPage, String orderBy, String flag) {
        String flag1;           // 给数据库识别的
                                //首先通过flag查询有多少条回答,接着每个记录进行排序
        if (flag.equals("1")){
            flag1 = "2";
        }
        else if (flag.equals("2")){
            flag1 = "1";
        }
        else{
            flag1 = "0";
        }
        Page<Question> pageQuestion  = new Page<Question>(questionMapper.countByFlag(Integer.valueOf(flag1)),10);
        pageQuestion.setCurrentPage(currentPage);
        int start = pageQuestion.getStart();
        int size = pageQuestion.getPageSize();
        /**
         * 进行排序返回，首先默认为时间，默认所有回答
         */
        if (orderBy.equals("0")){
            pageQuestion.setList(questionMapper.getPageQuestion(start,size,Integer.valueOf(flag1)));
        }else if (orderBy.equals("1")){
            pageQuestion.setList(questionMapper.getPageQuestionByFlagOrderByWord(start,size,Integer.valueOf(flag1)));
        }else if (orderBy.equals("2")){
            pageQuestion.setList(questionMapper.getPageQuestionByFlagOrderByAnswer(start,size,Integer.valueOf(flag1)));
        }else{
            pageQuestion.setList(questionMapper.getPageQuestionByFlagOrderByRead(start,size,Integer.valueOf(flag1)));
        }

        return pageQuestion;
    }

    /**查找自己发布的问题
     * 用户从指定的页数开始查找自己的一页问题,并通过0为默认时间，1为奖励，2为回答数，3为阅读数进行排序，以及flag进行筛选
     * @param session
     * @param currentPage
     * @param orderBy
     * @param flag
     * @return
     */
    @Override
    public Page<Question> getMyPageQuestion(HttpSession session, int currentPage, String orderBy, String flag) {
        //User user = (User) session.getAttribute("loginUser");
        int userId = 6;//user.getUid();
        String flag1;           // 给数据库识别的
        //首先通过flag查询有多少条回答,接着每个记录进行排序
        if (flag.equals("1")){
            flag1 = "2";
        }
        else if (flag.equals("2")){
            flag1 = "1";
        }
        else{
            flag1 = "0";
        }
        Page<Question> pageQuestion  = new Page<Question>(questionMapper.countByUserIdByFlag(userId,Integer.valueOf(flag1)),10);
        pageQuestion.setCurrentPage(currentPage);
        int start = pageQuestion.getStart();
        int size = pageQuestion.getPageSize();
        /**
         * 进行排序返回，首先默认为时间，默认所有回答
         */
        if (orderBy.equals("0")){
            pageQuestion.setList(questionMapper.getPageQuestionByUserId(userId,start,size,Integer.valueOf(flag1)));
        }else if (orderBy.equals("1")){
            pageQuestion.setList(questionMapper.getPageQuestionByFlagOrderByWordByUserId(userId,start,size,Integer.valueOf(flag1)));
        }else if (orderBy.equals("2")){
            pageQuestion.setList(questionMapper.getPageQuestionByFlagOrderByAnswerByUserId(userId,start,size,Integer.valueOf(flag1)));
        }else{
            pageQuestion.setList(questionMapper.getPageQuestionByFlagOrderByReadByUserId(userId,start,size,Integer.valueOf(flag1)));
        }

        return pageQuestion;
    }

    /**
     * 点击一个具体的问题，传入问题Id，返回该Question和对应的全部answer，answer以是否被采纳排序
     * @param questionId
     * @return
     */
    @Override
    public Map<String,Object> getQuestionDetail(Integer questionId) {
        Map<String,Object> map = new HashMap<>();
        Question question = questionMapper.findQuestionById(questionId);
        List<Answer> answers = answerMapper.listByProbId(questionId);
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
//        User user = (User) session.getAttribute("loginUser");
        // 当用户尝试修改不是自己的文章并且自己不是管理员时，不允许修改
        if (1!=question.getUserId() && 2!=1) {
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
