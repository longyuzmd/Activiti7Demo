package com.zs.day05;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 *  组任务 候选人
 *  组任务候选人的设置
 *  候选人的组任务查询
 *  候选人的任务拾取
 *  查询任务负责人
 *  完成拾取的任务
 *  任务的归还
 *  任务的委派
 */
public class CandiTest {

    /**
     * 部署流程
     * @param args
     */
//    public static void main(String[] args) {
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//        Deployment deployment = repositoryService
//                .createDeployment()
//                .addClasspathResource("diagrams/holidayclaim.bpmn")
//                .deploy();
//
//        System.out.println("流程定义id: " + deployment.getId());
//    }

    /**
     * 启动流程实例
     * @param args
     */
//    public static void main(String[] args) {
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayclaim");
//
//        System.out.println("流程实例的id: " + processInstance.getId());
//    }

    /**
     * 完成个人任务
     */
    public static void main(String[] args) {

        String assignee = "xiaowang";  // 负责人 xiaozhang zhangsan,lisi xiaowang

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayclaim")
                .taskAssignee(assignee)
                .singleResult();

        if(task != null){
            taskService.complete(task.getId());
        }
    }

    /**
     * 查询候选组成员的候选任务 并 进行拾取
     */
//    public static void main(String[] args) {
//
//        String candidateUser = "zhangsan";
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        TaskService taskService = processEngine.getTaskService();
//
//        List<Task> taskList = taskService.createTaskQuery()
//                .processDefinitionKey("holidayclaim")
//                .taskCandidateUser(candidateUser)
//                .list();
//
//        for(Task task : taskList){
//            System.out.println("任务的id:" + task.getId());
//            System.out.println("任务的名称:" + task.getName());
//            System.out.println("流程实例id:" + task.getProcessInstanceId());
//            // 拾取任务
//            taskService.claim(task.getId(),candidateUser);
//            System.out.println(candidateUser + "用户任务拾取成功！");
//        }
//    }

    /**
     * 候选任务的归还
     * @param args
     */
//    public static void main(String[] args) {
//
//        String assignee = "zhangsan";
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        TaskService taskService = processEngine.getTaskService();
//
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("holidayclaim")
//                .taskAssignee(assignee)
//                .singleResult();
//
//        if(task != null){
//            // 候选人组任务的归还
//            taskService.setAssignee(task.getId(),null);
//            System.out.println(task.getName() + "任务归还成功！");
//        }
//    }

    /**
     * 候选任务的委派
     * @param args
     */
//    public static void main(String[] args) {
//
//        String assignee = "zhangsan";
//
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        TaskService taskService = processEngine.getTaskService();
//
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("holidayclaim")
//                .taskAssignee(assignee)
//                .singleResult();
//
//        if(task != null){
//            // 候选人组任务的委派
//            taskService.setAssignee(task.getId(),"lisi");
//        }
//    }
}
