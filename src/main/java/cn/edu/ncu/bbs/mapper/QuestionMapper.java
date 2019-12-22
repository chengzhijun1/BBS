package cn.edu.ncu.bbs.mapper;

import cn.edu.ncu.bbs.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    /*
    * 查询特定用户提出的问题的page
     */
    @Select("select * from question_list where user_id = #{userId} limit #{offset},#{size}")
    List<Question> listById(@Param("userId") Integer id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);


//    @Select("select count(1) from question_list")
//    Integer count();        // 数据库中总共有多少个问题

    /**
     * 无权限的，所以人都可以查看的问题
     * @param flag
     * @return
     */
    @Insert("insert into question_list (user_id,question_title,question_describe,question_time,question_ward) values(#{userId},#{questionTitle},#{questionDescribe},now(),#{questionWard})")
    void addQuestion(Question question);  //新建一个问题
    @Select("SELECT count(1) FROM question_list WHERE flag != #{flag}")
    Integer countByFlag(@Param("flag") int flag);       //查询总共有多少个问题
    @Select("select * from question_list where flag!=#{flag} order By question_ward desc limit #{start},#{size}")
    List<Question> getPageQuestionByFlagOrderByWord(int start, int size, int flag);//查找回答、没回答的通过奖励排序
    @Select("select * from question_list where flag!=#{flag} order By answer_num desc limit #{start},#{size}")
    List<Question> getPageQuestionByFlagOrderByAnswer(int start, int size, int flag);//查找回答、没回答的通过回答数排序
    @Select("select * from question_list where flag!=#{flag} order By read_num desc limit #{start},#{size}")
    List<Question> getPageQuestionByFlagOrderByRead(int start, int size, int flag);//查找回答、没回答的通过阅读排序
    @Select("select * from question_list where flag!=#{flag} limit #{start},#{size}")
    List<Question> getPageQuestion(int start, int size, int flag);//查找默认按照时间排序的
    @Select("select count(1) from question_list where question_describe like '%${keyWord}%' or question_title like '%${keyWord}%'")
    Integer countByKeyWord(String keyWord);             //根据搜索词查询有多少匹配到的结果
    @Select("select * from question_list where question_describe like '%${keyWord}%' or question_title like '%${keyWord}%' limit #{start},#{size}")
    List<Question> findPageQuestionByKeyWord(int start, int size, String keyWord);//取出一页结果

    /**
     * 有权限的，自己登录了才可以查看的问题
     * @param flag
     * @return
     */
    @Select("select count(1) from question_list where user_id = #{userId} and flag != #{flag}")
    Integer countByUserIdByFlag(int userId,Integer flag);//查找我的问题的总数
    @Select("select * from question_list where user_id=#{userId} and flag!=#{flag} order By question_ward desc limit #{start},#{size}")
    List<Question> getPageQuestionByFlagOrderByWordByUserId(int userId, int start, int size, int flag);//查找回答、没回答的通过奖励排序
    @Select("select * from question_list where user_id = #{userId} and flag!=#{flag} order By answer_num desc limit #{start},#{size}")
    List<Question> getPageQuestionByFlagOrderByAnswerByUserId(int userId, int start, int size, int flag);//查找回答、没回答的通过回答数排序
    @Select("select * from question_list where user_id = #{userId} and flag!=#{flag} order By read_num desc limit #{start},#{size}")
    List<Question> getPageQuestionByFlagOrderByReadByUserId(int userId, int start, int size, int flag);//查找回答、没回答的通过阅读排序
    @Select("select * from question_list where user_id = #{userId} and flag!=#{flag} limit #{start},#{size}")
    List<Question> getPageQuestionByUserId(int userId, int start, int size, int flag);//查找默认按照时间排序的
    @Update("update question_list set question_title=#{questionTitle},question_describe=#{questionDescribe},question_ward=#{questionWard} where question_id = #{questionId}")
    void alertQuestion(Question question);//对自己的问题进行修改

    @Select("select * from question_list where question_id = #{questionId}")
    Question findQuestionById(@Param("questionId") Integer questionId);

    /*
    * 查询所有的问题
    * */
    @Select("select * from question_list")
    List<Question> findAllQuestion();

    /*
     * 通过回答数来查询
     * */
    @Select("SELECT * FROM question_list WHERE answer_num = #{answer_num}")
    List<Question> findZeroQuestion(@Param("answer_num") Integer answer_num);

    /*
     * 通过flag来查询
     * */
    @Select("SELECT * FROM question_list WHERE flag = #{flag}")
    List<Question> findSolvedQuestion(@Param("flag") Integer flag);

    /*-------------------------------------------------------------------------*/
    /*sstealer*/


    @Select("select * from question_list")
    List<Question> list();

    //根据问题编号查找问题
    @Select("select * from question_list where question_id = #{id}")
    Question getByquestionId(@Param("id") Integer id);

    @Update("update question_list set read_num=read_num+1 where question_id = #{id}")
    void updateReadNumById(@Param("id") Integer id);

    @Update("update question_list set answer_num=answer_num+1 where question_id = #{id}")
    void updateAnswerNumById(@Param("id") Integer id);

    @Select("select question_ward from question_list where question_id = #{pid}")
    Integer getWardById(@Param("pid") Integer pid);

    @Update("update question_list set flag = 1 where question_id = #{pid}")
    void setFlagTrue(@Param("pid") Integer pid);

    @Select("select user_id from question_list where question_id= #{pid}")
    Integer getUserIdByPid(@Param("pid") Integer pid);
}
