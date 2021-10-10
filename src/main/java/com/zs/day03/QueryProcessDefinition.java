package com.zs.day03;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 查询所有的流程定义
 */
public class QueryProcessDefinition {

    public static void main(String[] args) {
        // 获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("holiday")
                .orderByProcessDefinitionVersion()
                .desc().list();

        for(ProcessDefinition processDefinition:processDefinitionList){
            System.out.println("流程定义的id: " + processDefinition.getId());
            System.out.println("流程定义的名称: " + processDefinition.getName());
            System.out.println("流程定义的key: " + processDefinition.getKey());
            System.out.println("流程定义的版本号: " + processDefinition.getVersion());
        }
    }
}
