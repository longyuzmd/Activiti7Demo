package com.zscreate.day05gateway;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;

public class GateWayTest {

    // 获取流程引擎
    private final static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    private static String classpathResource = "diagrams/gateway/inclusivegateway.bpmn";

    private static String name = "体检包含网关";

    private static String processDefineKey = "examineinclusive";  // holidayexclusive holidayparallel examineinclusive

    /**
     * 流程部署测试类
     */
    @Test
    public void processDeploy(){
        // 获取repositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService
                .createDeployment()
                .addClasspathResource(classpathResource)
                .name(name)
                .deploy();

        System.out.println("部署id " + deployment.getId());
    }

    /**
     * 流程实例的启动
     * 这里为了方便，在启动流程实例的时候就设置流程变量，且是Global变量
     */
    @Test
    public void processInstanceStart(){

        RuntimeService runtimeService = processEngine.getRuntimeService();

        HashMap<String, Object> map = new HashMap<>();
//        map.put("holiday",new Holiday(5F));

        // 测试包含网关的知识
        map.put("userType",2);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefineKey, "bussKey10001", map);

        System.out.println("流程实例id: " + processInstance.getId());
    }

    /**
     * 完成个人任务
     */
    @Test
    public void completeTask(){

        String assignee = "xiaomao"; // zhangsan lisi wangwu zhaoliu xiaomao xiaozuo

        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefineKey)
                .taskAssignee(assignee)
                .singleResult();

        if(task != null){
            taskService.complete(task.getId());
            System.out.println("任务完成成功！");
        }
    }
}
