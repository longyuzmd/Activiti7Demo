package com.zs.day04;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;

/**
 *  Global 全局变量的四种设置方式
 *   1. 流程定义
 *      1)、启动流程实例时去设置变量
 *      2)、通过流程实例id去设置变量 （流程实例必须是进行中的）
 *   2. 任务
 *      1)、执行任务时去设置变量
 *      2)、通过任务id去设置变量
 */
public class VariableTest {

    /**
     * 1、 部署流程
     */
//    public static void main(String[] args) {
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("diagrams/holidayvariable.bpmn")
//                .addClasspathResource("diagrams/holidayvariable.png")
//                .deploy();
//
//        System.out.println(deployment.getId() +"============"+ deployment.getName());
//    }

    /**
     * 2、启动流程实例
     *      1) 尝试启动流程实例的时候去设流程变量
     */
//    public static void main(String[] args) {
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        HashMap<String, Object> map = new HashMap<>();
//
//        map.put("holiday",new Holiday(2F));  // 2天的时间走什么流程
//
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayvariable", map);
//
//        System.out.println(processInstance.getId());
//    }


    /**
     * 不设置
     * 流程变量 holiday.num 值
     * 的启动流程实例的方法
     */
//    public static void main(String[] args) {
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("holiday",new Holiday());
//
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayvariable",map);
//
//        System.out.println(processInstance.getId());
//    }

    /**
     * 先启动实例
     * 然后获取流程实例的id
     * 再设置变量
     *  2) 通过流程实例的id去设置流程变量
     */
//    public static void main(String[] args) {
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        ProcessInstance processInstance = runtimeService
//                .createProcessInstanceQuery()
//                .processDefinitionKey("holidayvariable")
//                .singleResult();
//
//        String processInstanceId = processInstance.getId();
//
//        // 只有一个流程变量的设置方法
//        runtimeService.setVariable(processInstanceId, "holiday", new Holiday(2F));
//
//        // 多个流程变量的设置方法
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("holiday",new Holiday(2F));
//        runtimeService.setVariables(processInstanceId, map);
//    }

    /**
     * 任务：
     *  1) 执行任务时设置流程变量
     */
//    public static void main(String[] args) {
//
//        String assignee = "zhangsan";   // zhangsan lisi wangwu zhaoliu
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        TaskService taskService = processEngine.getTaskService();
//
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("holidayvariable")
//                .taskAssignee(assignee)
//                .singleResult();
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("holiday", new Holiday(2F));
//
//        if(task != null){
//            taskService.complete(task.getId(),map);
//        }
//    }

    /**
     * 任务：
     *  2) 通过任务id去设置流程变量的方式
     */
//    public static void main(String[] args) {
//
//        String assignee = "zhangsan";   // zhangsan lisi wangwu zhaoliu
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        TaskService taskService = processEngine.getTaskService();
//
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("holidayvariable")
//                .taskAssignee(assignee)
//                .singleResult();
//
//        if(task != null){
//
//            // 单个变量的设置方式
//            taskService.setVariable(task.getId(), "holiday", new Holiday(2F));
//
//            // 多个变量的设置方式
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("holiday",new Holiday(3F));
//
//            taskService.setVariables(task.getId(), map);
//        }
//    }

    /**
     * 改进方法 ： 将查询任务和完成任务合并
     * @param args
     */
    public static void main(String[] args) {

        String assignee = "zhaoliu"; // 任务负责人 zhangsan lisi wangwu zhaoliu

        // 1. 获取流程引擎processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 获取taskService 并 完成该流程定义指定的用户任务
        TaskService taskService = processEngine.getTaskService();

        // 3. 查询任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayvariable")
                .taskAssignee(assignee)
                .singleResult();

        // 4.完成任务
        taskService.complete(task.getId());

    }
}
