package com.zs.day04;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  对象必须实现 Serializable 接口
 */
@Data
public class Holiday implements Serializable {

    private String id;

    private Date startTime;

    private Date endTime;

    private Float num;

    private String reason;

    private String type;

    private String assignee0;

    private String assignee1;

    private String assignee2;

    private String assignee3;

    public Holiday(Float num){
        this.num = num;
        this.assignee0 = "zhangsan";
        this.assignee1 = "lisi";
        this.assignee2 = "wangwu";
        this.assignee3 = "zhaoliu";
    }

    public Holiday(){
        this.assignee0 = "zhangsan";
        this.assignee1 = "lisi";
        this.assignee2 = "wangwu";
        this.assignee3 = "zhaoliu";
    }
}
