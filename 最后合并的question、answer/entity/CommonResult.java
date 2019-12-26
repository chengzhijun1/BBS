package cn.edu.ncu.bbs.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@Repository
public class CommonResult {
    private String code;    //结果码
    private String msg;     //结果信息
    private Object data;    //查询数据
}
