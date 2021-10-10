package com.zs.create;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 完成任务
 *
 * act_hi_actinst 历史的节点实例信息表
 * act_hi_identitylink 历史参与者信息
 * act_hi_taskinst 历史任务实例信息表
 * act_ru_execution 执行中节点信息
 * act_ru_identitylink 执行中参与者
 * act_ru_task 执行中当前节点
 */
public class ActivitiTaskComplete {

//    public static void main(String[] args) {
//        // 1. 获取流程引擎processEngine
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        // 2. 获取taskService 并 完成该流程定义指定的用户任务
//        TaskService taskService = processEngine.getTaskService();
//
//        // 3. 任务id 去 完成当前节点的任务
//        taskService.complete("2505");
//
//    }

    /**
     * 改进方法 ： 将查询任务和完成任务合并
     * @param args
     */
    public static void main(String[] args) {

        String assignee = "wangwu"; // 任务负责人 zhangsan lisi wangwu

        // 1. 获取流程引擎processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 获取taskService 并 完成该流程定义指定的用户任务
        TaskService taskService = processEngine.getTaskService();

        // 3. 查询任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayuel")
                .taskAssignee(assignee)
                .singleResult();

        // 4.完成任务
        taskService.complete(task.getId());

    }
}
