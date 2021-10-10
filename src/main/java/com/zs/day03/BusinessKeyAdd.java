package com.zs.day03;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 流程实例的启动
 * 增加业务主键 businessKey
 */
public class BusinessKeyAdd {

    public static void main(String[] args) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday", "10001");

        System.out.println("业务主键id: "+processInstance.getBusinessKey());
    }
}
