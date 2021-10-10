package com.zscreate.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * 生成25张表
 */
public class ActivitiTest {

    @Test
    public void testGenTable(){
        // 1.创建ProcessEngineConfiguration对象
        /**
         * createProcessEngineConfigurationFromResource（）参数分析
         * 1、第一参数是 资源路径
         * 2、第二参数是 bean id  不写默认为 processEngineConfiguration
         */
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        // 2.创建ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();

        // 3.输出processEngine对象
        System.out.println(processEngine);

    }

//    @Test
//    public void testGenerateTable(){
//        /**
//         * 第二种创建方式
//         * 条件：1. 配置文件的名称必须是：activiti.cfg.xml
//         *      2. bean id="processEngineConfiguration"
//         */
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        System.out.println(processEngine);
//    }


}
