package cn.edu.ncu.bbs.controller;

import cn.edu.ncu.bbs.entity.CommonResult;
import cn.edu.ncu.bbs.entity.Page;
import cn.edu.ncu.bbs.entity.Question;
import cn.edu.ncu.bbs.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Resource
    QuestionService questionService;
    @Resource
    CommonResult result;

    /**
     * 用户添加一个问题，通过返回的result判断是否加入成功
     * 需要的参数：创建问题的user的id，问题的题目、描述、时间、奖励
     *
     * @param question
     * @return
     */
    @GetMapping("/addQuestion")
    public CommonResult addQuestion(HttpSession session, Question question) {
        return questionService.addQuestion(session,question);
    }


    /**
     * 通过关键字搜索题目或者描述
     * @param keyWord
     * @param currentPage
     * @return
     */
    @GetMapping("/findQuestionByKeyWord")
    public Page<Question> findQuestionByKeyWord(String keyWord, int currentPage) {
        return questionService.findQuestionByKeyWord(keyWord,currentPage);
    }


    /**
     * 主界面的问题列表，可根据orderBy参数选择以默认时间，奖励，回答数，阅读数进行排序
     * 还可以筛选被解决、没被解决、或者所有问题
     * @param
     * @return
     */
    @GetMapping("/questionPage")
    public Page<Question> getPageQuestion(int currentPage, String orderBy, String flag){
        return questionService.getPageQuestion(currentPage,orderBy,flag);
    }


    /**
     * 用户点击问题，url的最后为问题的Id,返回一个问题和对应的answer List
     * @param questionId
     * @return
     */
    @GetMapping("/questionPage/{questionId}")
    public Map<String,Object> getQuestionDetail(@PathVariable Integer questionId){
        return questionService.getQuestionDetail(questionId);
    }

    /**
     * 用户看自己提的问题
     */
    @GetMapping("/myQuestionList")
    public Page<Question> getMyPageQuestion(HttpSession session, int currentPage, String orderBy, String flag){
        return questionService.getMyPageQuestion(session,currentPage,orderBy,flag);
    }


    /**
     * 用户对自己提的问题进行修改,前端要默认传回问题原来的title、describe、ward
     */
    @GetMapping("/alertQuestion")
    public CommonResult alertQuestion(HttpSession session,Question question){
        return questionService.alertQuestion(session,question);
    }

    /**
     * 测试
     *
     * @param
     * @return
     */
    @RequestMapping("/test")
    public String hello() {
        return "test";
    }


}
