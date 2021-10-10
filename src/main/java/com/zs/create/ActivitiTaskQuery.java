package com.zs.create;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 任务的查询
 *
 * act_hi_procinst 流程实例表
 * act_ru_task  正在执行的任务信息表
 */
public class ActivitiTaskQuery {

    public static void main(String[] args) {
        // 1. 获取流程引擎processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 获取taskService 并 查询该流程定义指定用户的任务列表
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee("zhangsan")
                .list();

        for(Task task : list){
            System.out.println("流程实例id: "+ task.getProcessInstanceId()
                    +"任务id: "+ task.getId()
                    +"任务负责人: "+ task.getAssignee()
                    +"任务名称: "+ task.getName());
        }

    }
}
