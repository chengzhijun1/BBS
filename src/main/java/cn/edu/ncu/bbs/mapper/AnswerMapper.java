package cn.edu.ncu.bbs.mapper;


import cn.edu.ncu.bbs.entity.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnswerMapper {


    //通过起始位置和一页的数量查找回答
    @Select("select * from answer_list where user_id=#{userId} limit #{offset},#{size}")
    List<Answer> listById(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);


    //查找我的回答有多少个
    @Select("select count(1) from answer_list where user_id = #{userId}")
    Integer countByUserId(@Param("userId") Integer id);
    //查找我的回答有多少个
    @Select("select * from answer_list where user_id = #{userId} limit #{start},#{size}")
    List<Answer> getAnswerPageByUserId(@Param("userId") Integer id,int start,int size);

    /**
     * 通过回答编号来查询
     */
    @Select("SELECT * FROM answer_list WHERE answer_unique = #{answer_unique}")
    Answer findAnswerByUnique(@Param("answer_unique") String answer_unique);

    /**
     * 查询所有回答
     */
    @Select("SELECT * FROM answer_list")
    List<Answer> findAllAnswer();

    /**
     * 通过回答的id来查询
     */
    @Select("SELECT * FROM answer_list WHERE answer_id = #{answer_id}")
    Answer findAnswerByid(@Param("answer_id") Integer answer_id);

    /**
     * 通过answer_id在表中删除一条数据
     */
    @Delete("DELETE from answer_list WHERE answer_id = #{answer_id}")
    void deleteAnswerByid(@Param("answer_id") Integer answer_id);


    /*-------------------------------------------------------------------------*/
    /*sstealer*/

    @Select("select * from answer_list where prob_id = #{id}")
    List<Answer> listByProbId(@Param(value = "id") Integer id); // 查询对应问题的所有回答
    @Select("select count(1) from answer_list where prob_id = #{questionId}")
    Integer countByQuestionId(@Param("questionId") Integer id);         //根据probId查询对应问题的有几个回答


    @Insert("insert into answer_list (answer_unique, user_id, prob_id, answer_describe, answer_time) values ( #{answerUnique} , #{userId}, #{probId}, #{answerDescribe}, #{answerTime} )")
    public void insert(Answer answer);

    //是否采纳
    @Update("update answer_list set is_accept = 1 where answer_id = #{aid}")
    void setIsAcceptTrue(@Param(value = "aid") Integer aid);

}
