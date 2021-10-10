package com.zscreate.study;

import com.zs.day04.Holiday;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class StudyActBase {

    /**
     * 流程的部署：
     *  部署资源：
     *  1、流程定义bpmn文件
     *  2、流程定义的图片 png
     *
     *  整个部署的流程改变的表就三张表
     *  act_re_deployment
     *  act_re_procdef
     *  act_ge_bytearray
     *
     *  act_re_deployment 流程部署信息表 (id name)
     *  act_re_procdef 流程定义表  (启动流程 key )
     *  act_ge_bytearray  流程资源信息表  (bpmn  png)
     */

    // 公有信息提取出来
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void test(){

        // 1、获取流程引擎
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2、获取对应的service接口 repositoryService runtimeService historyService managerService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3、部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagrams/holiday.bpmn")
                .addClasspathResource("diagrams/holiday.png")
                .name("请假流程")
                .deploy();

        // 4.打印一下流程部署对象的信息
        System.out.println("流程定义的ID:"+deployment.getId());
        System.out.println("流程定义的Name:"+deployment.getName());
    }

    /**
     * 通过zip形式去部署流程
     * ZipInputStream
     */
    @Test
    public void testZip(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("diagrams/activitiBPMN.zip");

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("请假流程")
                .deploy();

        System.out.println("流程定义的ID: "+deployment.getId());
        System.out.println("流程定义的Name: "+deployment.getName());
    }

    /**
     * 启动流程
     *
     * 流程实例的信息打印
     * act_hi_actinst  活动实例表 节点
     * act_hi_identitylink 历史参与者
     * act_hi_procinst 历史流程实例信息
     * act_hi_taskinst 历史任务实例信息
     * act_ru_execution 流程执行信息
     * act_ru_identitylink 参与者
     * act_ru_task 任务节点
     */
    @Test
    public void testStartProcess(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday");

        System.out.println("流程部署ID: "+processInstance.getDeploymentId());
        System.out.println("流程定义ID: "+processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID: "+processInstance.getId());  // 表 act_hi_procinst 历史流程实例表
        System.out.println("活动节点ID: "+processInstance.getActivityId());
    }


    /**
     * 完成指定的任务
     * 1、需要查询指定人的任务
     *
     * 完成complete背后操作的表信息
     * act_hi_actinst 增加操作的节点的信息
     * act_hi_identitylink 增加参与人
     * act_hi_taskinst 增加了一个任务节点信息
     * act_ru_execution 改变了当前任务节点信息
     * act_ru_identitylink 增加了执行的参与人信息
     * act_ru_task 改变当前待执行的任务信息
     */
    @Test
    public void testCompleteTask(){

        TaskService taskService = processEngine.getTaskService();

        String assignmentName = "wangwu";  // zhangsan lisi wangwu

        // 查询任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee(assignmentName)
                .singleResult();

        System.out.println("流程定义ID: "+task.getProcessDefinitionId());
        System.out.println("流程实例ID: "+task.getProcessInstanceId());
        System.out.println("任务的名称: "+task.getName());
        System.out.println("任务的操作人: "+task.getAssignee());
        System.out.println("任务的ID: "+task.getId());

        // 完成任务
        taskService.complete(task.getId());
    }

    /**
     * 流程定义的查询
     * act_re_procdef
     */
    @Test
    public void testProcessDefinitionQuery(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("holiday")
                .list();

        for (ProcessDefinition processDefinition:processDefinitionList){
            System.out.println(processDefinition.getId());
            System.out.println(processDefinition.getKey());
            System.out.println(processDefinition.getName());
            System.out.println(processDefinition.getDeploymentId());
        }
    }

    /**
     * 流程定义的删除
     * 删除有情况：
     * 1、没有正在执行的任务 act_ru_ 表没有数据
     * 2、有正在执行的任务，需要强制删除， 增加形参变量 true
     */
    @Test
    public void testProcessDefinitionDelete(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService
                .createDeploymentQuery()
                .processDefinitionKey("holiday")
                .singleResult();

        System.out.println(deployment.getId());
        repositoryService.deleteDeployment(deployment.getId(),true);
    }

    /**
     * 查询历史节点信息
     */
    @Test
    public void testQueryHistoryInfo(){
        //启动一个流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday", "1001");

        //查询流程实例的历史节点信息
        HistoryService historyService = processEngine.getHistoryService();
        String processInstanceId = processInstance.getId();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();

        for(HistoricActivityInstance historicActivityInstance:list){
            System.out.println(historicActivityInstance.getActivityId());
            System.out.println(historicActivityInstance.getActivityName());
            System.out.println(historicActivityInstance.getAssignee());
            System.out.println(historicActivityInstance.getStartTime());
            System.out.println(historicActivityInstance.getEndTime());
            System.out.println("=========================================");
        }
    }

    /**
     * 针对uel表达式动态设置assignee
     *
     * uel表达式动态设置变量值
     *  Global 变量的四种设置方式
     */
    @Test
    public void testDeploymentVariableModel(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagrams/holidayvariable.bpmn")
                .addClasspathResource("diagrams/holidayvariable.png")
                .name("请假2.0流程uel")
                .deploy();

        System.out.println(deployment.getId() + "=======" +deployment.getName());
    }

    /**
     * 流程变量的第一种写法
     *  1、启动流程去设置
     */
    @Test
    public void testStartProcessInstance(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        HashMap<String, Object> map = new HashMap<>();

        map.put("holiday",new Holiday(4F));  // 全局变量的设置

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayvariable",map);

        System.out.println(processInstance.getId());
        System.out.println(processInstance.getProcessDefinitionId());
        System.out.println(processInstance.getName());

    }

    /**
     * 先启动流程
     *  2、通过流程实例id 设置变量
     */
    @Test
    public void testStartProcessInstance2(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        HashMap<String, Object> map = new HashMap<>();

        map.put("holiday",new Holiday());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayvariable",map);

        // 由于操作人也是动态设置了，操作启动流程时候就需要设置
        runtimeService.setVariable(processInstance.getId(),"holiday",new Holiday(2F));

    }

    /**
     * 针对剩下的完成任务的时候去设置Global变量不做演示
     * taskService.complete(,)
     *
     * taskService.setVariable()
     */

    @Test
    public void testCompleteUELTask(){
        TaskService taskService = processEngine.getTaskService();

        String assigneeName = "zhaoliu"; // zhangsan lisi wangwu zhaoliu

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayvariable")
                .taskAssignee(assigneeName)
                .singleResult();

        taskService.complete(task.getId());
    }

    @Test
    public void testDeleteProcessDefinition(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        repositoryService.deleteDeployment("7501",true);
    }

    /**
     * 组任务的部署
     */
    @Test
    public void testDeploymentCandidateUsers(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagrams/candidate/holidayuelcandidate.bpmn")
                .name("UEL组任务测试")
                .deploy();

        System.out.println(deployment.getId() + "=======" +deployment.getName());
    }

    /**
     * 启动流程
     */
    @Test
    public void testStartProcessInstanceCandidateUsers(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        HashMap<String, Object> map = new HashMap<>();

        map.put("assignee0","zhangsan");
        map.put("candidateUsers","lisi,wangwu");
        map.put("assignee1","zhaoliu");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayuelcandidate",map);

        System.out.println(processInstance.getId());
        System.out.println(processInstance.getProcessDefinitionId());
        System.out.println(processInstance.getName());

    }

    /**
     * 完成个人任务
     */
    @Test
    public void testCandidateCompleteTask(){
        TaskService taskService = processEngine.getTaskService();

        String assigneeName = "zhaoliu";

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayuelcandidate")
                .taskAssignee(assigneeName)
                .singleResult();

        taskService.complete(task.getId());
    }

    /**
     * 组任务的拾取
     */
    @Test
    public void testClaimTask(){
        TaskService taskService = processEngine.getTaskService();

        String candidateUserName = "lisi";

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayuelcandidate")
                .taskCandidateUser(candidateUserName)
                .singleResult();

        System.out.println(task.getName());
        System.out.println(task.getId());
        System.out.println(task.getProcessInstanceId());
        System.out.println(task.getProcessDefinitionId());

        taskService.claim(task.getId(),candidateUserName);
    }

    /**
     * 组任务的归还
     */
    @Test
    public void testBackTask(){
        TaskService taskService = processEngine.getTaskService();

        String assigneeName = "lisi";

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayuelcandidate")
                .taskAssignee(assigneeName)
                .singleResult();

        if(task != null){
            taskService.setAssignee(task.getId(),null);
        }
    }

    /**
     * 组任务的委派
     */
    @Test
    public void testAcceptTask(){
        TaskService taskService = processEngine.getTaskService();

        String assigneeName = "lisi";

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayuelcandidate")
                .taskAssignee(assigneeName)
                .singleResult();

        if(task != null){
            taskService.setAssignee(task.getId(),"wangwu");
        }
    }
}
