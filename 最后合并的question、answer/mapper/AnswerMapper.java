package cn.edu.ncu.bbs.mapper;


import cn.edu.ncu.bbs.entity.Answer;
import cn.edu.ncu.bbs.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnswerMapper {

    List<Answer> getAnswerByQuestion(@Param("questionId") Integer questionId);


    //通过起始位置和一页的数量查找回答
    @Select("select * from answer where user_id=#{userId} limit #{offset},#{size}")
    List<Answer> listById(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);


    //查找我的回答有多少个
    @Select("select count(1) from answer where user_id = #{userId}")
    Integer countByUserId(@Param("userId") Integer id);
    //查找我的回答
//    @Select("select * from answer where user_id = #{userId} limit #{start},#{size}")
    List<Answer> getAnswerPageByUserId(int userId,int start,int size);

    /**
     * 通过回答编号来查询
     */
    @Select("SELECT * FROM answer WHERE answer_unique = #{answer_unique}")
    Answer findAnswerByUnique(@Param("answer_unique") String answer_unique);

    /**
     * 查询所有回答
     */
    @Select("SELECT * FROM answer")
    List<Answer> findAllAnswer();

    /**
     * 通过回答的id来查询
     */
    @Select("SELECT * FROM answer WHERE answer_id = #{answer_id}")
    Answer findAnswerByid(@Param("answer_id") Integer answer_id);

    /**
     * 通过answer_id在表中删除一条数据
     */
    @Delete("DELETE from answer WHERE answer_id = #{answer_id}")
    void deleteAnswerByid(@Param("answer_id") Integer answer_id);


    /*-------------------------------------------------------------------------*/
    /*sstealer*/

    @Select("select * from answer where question_id = #{questionId}")
    List<Answer> listByQuestionId(@Param(value = "questionId") Integer questionId); // 查询对应问题的所有回答
    @Select("select count(1) from answer where question_id = #{questionId}")
    Integer countByQuestionId(@Param("questionId") Integer id);         //根据probId查询对应问题的有几个回答


    @Insert("insert into answer (user_id, question_id, answer_describe, answer_time) values (#{userId}, #{questionId}, #{answerDescribe}, now() )")
    public void insert(Answer answer);//添加一个回答

    //是否采纳
    @Update("update answer set is_accept = 1 where answer_id = #{aid}")
    void setIsAcceptTrue(@Param(value = "aid") Integer aid);
    //提问者减去积分
    @Update("update user set score = score-#{score} where user_id = #{userId}")
    void delScore(int userId,int score);
    //提问者减去积分
    @Update("update user set score = score+#{score} where user_id = #{userId}")
    void addScore(int userId,int score);

}
