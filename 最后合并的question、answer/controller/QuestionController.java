package cn.edu.ncu.bbs.controller;

import cn.edu.ncu.bbs.entity.CommonResult;
import cn.edu.ncu.bbs.entity.Page;
import cn.edu.ncu.bbs.entity.Question;
import cn.edu.ncu.bbs.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@ResponseBody
//@RequestMapping("/question")
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
    @PostMapping("/alert/question")
    public CommonResult addQuestion(HttpSession session, Question question) {
        return questionService.addQuestion(session,question);
    }


    /**
     * 主界面的问题列表，可根据orderBy参数选择以默认时间，奖励，回答数，阅读数进行排序
     * 还可以筛选被解决、没被解决、或者所有问题，orderBy默认为0，flag默认为2
     * @param
     * @return
     */
    @GetMapping("/questions")
    public Page<Question> getPageQuestion(@RequestParam(defaultValue = "1",required = false) int pageNum,
                                          @RequestParam(defaultValue = "0",required = false) Integer orderBy,
                                          @RequestParam(defaultValue = "2",required = false) Integer flag) {
        return questionService.getPageQuestion(pageNum,orderBy,flag);
    }

    /**
     * 通过关键字搜索题目或者描述
     * @param keyWord
     * @param pageNum
     * @return
     */
    @GetMapping("/search/question")
    public Page<Question> findQuestionByKeyWord(String keyWord,
                                                @RequestParam(defaultValue = "1",required = false) int pageNum) {
        return questionService.findQuestionByKeyWord(keyWord,pageNum);
    }


    /**
     * 用户点击问题，url的最后为问题的Id,返回一个问题和对应的answer List
     * @param questionId
     * @return
     */
    @GetMapping("/questions/{questionId}")
    public Map<String,Object> getQuestionDetail(@PathVariable Integer questionId){
        return questionService.getQuestionDetail(questionId);
    }

    /**
     * 看用户提的问题
     */
    @GetMapping("/user/{username}/question")
    public Page<Question> getUsersPageQuestion(int userId,
                                               @RequestParam(defaultValue = "1",required = false) int pageNum,
                                               @RequestParam(defaultValue = "0",required = false) Integer orderBy,
                                               @RequestParam(defaultValue = "2",required = false) Integer flag){
        return questionService.getUsersPageQuestion(userId,pageNum,orderBy,flag);
    }



    /**
     * 用户对自己提的问题进行修改,前端要默认传回问题原来的title、describe、ward
     */
    @PatchMapping("/alert/{mao}/post")
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
