package cn.edu.ncu.bbs.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class User {
    private Integer userId;     //用户id
    private String account;     //账号
    private String username;    //昵称
    private String password;    //密码
    private String email;       //电话
    private String profession;  //职业
    private String workplace;   //工作地点
    private String head;        //头像
    private Integer score;       //积分
    private Integer flag;       // 1位管理员，2为普通用户

}
