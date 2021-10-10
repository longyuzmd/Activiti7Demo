package com.zs.day03;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 删除流程定义
 * 删除分为两种情况：
 *  1. 该流程的所有环节均完成，及没有未完成的流程实例，就是act_ru_ 表没有该流程定义的数据
 *  2. 我需要强制删除，就是不管你有没有未完成的流程实例，repositoryService.deleteDeployment("1",true);
 *  级联删除，将删除所有的关联信息 act_ru_
 */
public class DeleteProcessDefinition {

    public static void main(String[] args) {
        // 获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        repositoryService.deleteDeployment("1");
    }
}
