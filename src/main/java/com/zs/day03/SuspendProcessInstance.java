package com.zs.day03;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * 全部流程实例( 流程定义 )的挂起与激活
 */
public class SuspendProcessInstance {

    public static void main(String[] args) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("holiday").singleResult();

        // 判断这个流程定义是否是暂停
        boolean suspended = processDefinition.isSuspended();

        // 流程定义的id
        String processDefinitionId = processDefinition.getId();

        if(suspended){
            // 是暂停，那么就激活
            repositoryService.activateProcessDefinitionById(processDefinitionId,true,null);
            System.out.println("流程定义： "+processDefinitionId+"被激活");
        }else{
            // 不是暂停那么就挂起所有流程实例
            repositoryService.suspendProcessDefinitionById(processDefinitionId,true,null);
            System.out.println("流程定义： "+processDefinitionId+"被挂起");
        }
    }
}
