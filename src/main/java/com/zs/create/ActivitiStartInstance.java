package com.zs.create;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;

/**
 * 流程实例的启动
 */
public class ActivitiStartInstance {

//    public static void main(String[] args) {
//        /**
//         * 1. 创建流程引擎对象 processEngine
//         *
//         * 2. 创建 runtimeService 对象 并 启动流程实例
//         *
//         * 3. 流程实例的信息打印
//         * act_hi_actinst  活动实例表 节点
//         * act_hi_identitylink 历史参与者
//         * act_hi_procinst 历史流程实例信息
//         * act_hi_taskinst 历史任务实例信息
//         * act_ru_execution 流程执行信息
//         * act_ru_identitylink 参与者
//         * act_ru_task 任务节点
//         */
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        // key = holiday
//        ProcessInstance holiday = runtimeService.startProcessInstanceByKey("holiday");
//
//        System.out.println("流程部署id: " + holiday.getDeploymentId()
//                            +" 流程定义id:" + holiday.getProcessDefinitionId()
//                            +" 流程实例id:" + holiday.getId()
//                            +" 活动id:" + holiday.getActivityId());
//
//    }

    /**
     * uel 表达式动态分配任务负责人 流程启动方式
     */
    public static void main(String[] args) {
        /**
         * 1. 创建流程引擎对象 processEngine
         *
         * 2. 创建 runtimeService 对象 并 启动流程实例
         *
         * 3. 流程实例的信息打印
         * act_hi_actinst  活动实例表 节点
         * act_hi_identitylink 历史参与者
         * act_hi_procinst 历史流程实例信息
         * act_hi_taskinst 历史任务实例信息
         * act_ru_execution 流程执行信息
         * act_ru_identitylink 参与者
         * act_ru_task 任务节点
         */
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        HashMap<String, Object> map = new HashMap<>();
        map.put("assignee0","zhangsan");
        map.put("assignee1","lisi");
        map.put("assignee2","wangwu");

        // key = holiday
        ProcessInstance holiday = runtimeService.startProcessInstanceByKey("holidayuel","10001",map);

        System.out.println("流程部署id: " + holiday.getDeploymentId()
                +" 流程定义id:" + holiday.getProcessDefinitionId()
                +" 流程实例id:" + holiday.getId()
                +" 活动id:" + holiday.getActivityId());

    }
}
