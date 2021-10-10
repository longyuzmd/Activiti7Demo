package com.zs.create;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程的部署和定义
 */
public class ActivitiDeployment {

    public static void main(String[] args) {
        /**
         * 1. 创建processEngine对象
         *
         * 2. 获取repositoryService对象 并 部署流程
         *
         * 3. 输出部署信息
         *
         * act_re_deployment 流程部署信息表
         * act_re_procdef 流程定义表
         * act_ge_bytearray  流程资源信息表
         */
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagrams/holidayuel.bpmn")
                .addClasspathResource("diagrams/holidayuel.png")
                .name("请假流程")
                .deploy();

        System.out.println(deployment.getId() +"============"+ deployment.getName());
    }

//    /**
//     * 压缩包的方式可去部署
//     */
//    public static void main(String[] args) {
//        /**
//         * 1. 创建processEngine对象
//         *
//         * 2. 获取repositoryService对象 并 部署流程
//         *
//         * 3. 输出部署信息
//         *
//         * act_re_deployment 流程部署信息表
//         * act_re_procdef 流程定义表
//         * act_ge_bytearray  流程资源信息表
//         */
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//        // 类加载器获取压缩文件的inputStream
//        InputStream inputStream = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagrams/activitiBPMN.zip");
//
//        // 将inputStream 转化成 zipInputStream
//        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
//
//        Deployment deployment = repositoryService.createDeployment()
//                .addZipInputStream(zipInputStream)
//                .name("请假审核流程")
//                .deploy();
//
//        System.out.println(deployment.getId() +"============"+ deployment.getName());
//    }
}
