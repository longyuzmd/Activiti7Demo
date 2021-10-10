package com.zs.day03;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 *  如何通过activiti api去读取资源文件
 *  并 存储到指定位置 （服务器存储目录）
 */
public class QueryResourceFile {

    public static void main(String[] args) throws IOException {
        // 1.processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2.repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.得到查询器：processDefinitionQuery
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        // 4.设置查询条件
        processDefinitionQuery.processDefinitionKey("holiday");

        // 5.执行查询条件，得到想要的流程定义
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();

        // 6.通过流程定义对象 获取 部署id
        String deploymentId = processDefinition.getDeploymentId();

        // 7.通过repositoryService 获的读取资源文件（png\bpmn)文件输入流
        InputStream pngIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());

        InputStream bpmnIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());


        // 8.构建输出流
        OutputStream pngOs = new FileOutputStream("C:\\Users\\longyuzmd\\Desktop\\" + processDefinition.getDiagramResourceName());

        OutputStream bpmnOs = new FileOutputStream("C:\\Users\\longyuzmd\\Desktop\\" + processDefinition.getResourceName());

        // 9.流转化 commons-io 工具
        IOUtils.copy(pngIs,pngOs);
        IOUtils.copy(bpmnIs,bpmnOs);

        // 10.关闭流
        pngOs.close();
        bpmnOs.close();
        pngIs.close();
        bpmnIs.close();
    }
}
