package com.zs.day03;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;

import java.util.List;

/**
 * 查询历史节点信息
 */
public class QueryHistoryInfo {
    public static void main(String[] args) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        HistoryService historyService = processEngine.getHistoryService();

        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();

        historicActivityInstanceQuery.processInstanceId("2501");

        List<HistoricActivityInstance> list = historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()
                .asc().list();

        for (HistoricActivityInstance historicActivityInstance: list){
            System.out.println("节点id: "+historicActivityInstance.getActivityId());
            System.out.println("节点名称: "+historicActivityInstance.getActivityName());
            System.out.println("流程定义id: "+historicActivityInstance.getProcessDefinitionId());
            System.out.println("流程实例id: "+historicActivityInstance.getProcessInstanceId());
            System.out.println("=====================================================");
        }
    }
}
