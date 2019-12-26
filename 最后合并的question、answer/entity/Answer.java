package cn.edu.ncu.bbs.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Data
@Repository
public class Answer {
    private Integer answerId;       //问答ID
    private Integer questionId;     //回答的问题的Id
    private Integer userId;         //回答的用户的Id
    private String answerDescribe;  //回答内容
    private Timestamp answerTime;   //回答时间
    private Integer isAccept;       //默认为2，即为未采纳，1为采纳
    private User user;              //回答的用户
    private Question question;      //回答的问题
}
