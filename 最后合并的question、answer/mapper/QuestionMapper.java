package cn.edu.ncu.bbs.mapper;

import cn.edu.ncu.bbs.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    /**
     * 无权限的，所以人都可以查看的问题
     * @param flag
     * @return
     */

    @Select("SELECT count(1) FROM question WHERE flag != #{flag}")
    Integer countByFlag(@Param("flag") int flag);       //根据flag查询数据库中总共有多少个问题

    @Select("select count(1) from question where question_describe like '%${keyWord}%' or question_title like '%${keyWord}%'")
    Integer countByKeyWord(String keyWord);             //根据搜索词查询有多少匹配到的结果

    List<Question> findQuestionByKeyWord(int start, int size, String keyWord);//根据关键词取出一页结果

    List<Question> getPageQuestionByFlagOrderBy(int start,int size,int flag,String orderBy);//根据flag取出一页根据orderBy排序

    Question getQuestionById(@Param("questionId") Integer questionId);//  通过questionId获取question对象

    @Select("select * from question where question_id = #{questionId}")
    Question getQuestionById1(int questionId);//通过questionId获取question对象

    /**
     * 有权限的，自己登录了才可以查看的问题，如用户看我的问题，我的回答
     * @return
     */

    @Insert("insert into question (user_id,question_title,question_describe,question_time,question_ward) values(#{userId},#{questionTitle},#{questionDescribe},now(),#{questionWard})")
    void addQuestion(Question question);  //新建一个问题

    @Select("select question_id,question_title,read_num,answer_num,question_ward,question_time,flag from question " +
            "where user_id=#{userId} and flag!=#{flag} order by ${orderBy} limit #{start},#{size}")
    List<Question> getUsersPageQuestionByFlagOrderBy(int start,int size,int flag,String orderBy,int userId);//查找自己发的问题

    @Select("select count(1) from question where user_id = #{userId} and flag != #{flag}")
    Integer countByUserIdByFlag(int userId,Integer flag);//查找我的问题的总数

    @Update("update question set question_title=#{questionTitle},question_describe=#{questionDescribe},question_ward=#{questionWard} where question_id = #{questionId}")
    void alertQuestion(Question question);//对自己的问题进行修改

}
