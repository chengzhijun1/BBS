package cn.edu.ncu.bbs.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Data
@Repository
public class Question {
    private Integer questionId;     // 用于标识的ID
    private Integer userId;         //哪个用户提的问题
    private String questionTitle;   //问题标题
    private String questionDescribe;//问题描述
    private Timestamp questionTime; //提问时间
    private Integer questionWard;   //悬赏
    private String answerNum;       //回答数
    private String readNum;         //阅读量
    private Integer flag;           //1为已解决，2为未解决，默认为2
    private User user;              //提问的用户
}
