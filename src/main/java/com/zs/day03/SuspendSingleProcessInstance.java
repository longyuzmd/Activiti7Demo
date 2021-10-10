package com.zs.day03;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 单个流程实例的挂起
 */
public class SuspendSingleProcessInstance {

    public static void main(String[] args) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("holiday").singleResult();

        // 判断这个流程定义是否是暂停
        boolean suspended = processInstance.isSuspended();

        // 流程定义的id
        String processInstanceId = processInstance.getId();

        if(suspended){
            // 是暂停，那么就激活
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程实例： " + processInstanceId + "被激活");
        }else{
            // 不是暂停那么就挂起所有流程实例
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程实例： " + processInstanceId + "被挂起");
        }
    }
}
