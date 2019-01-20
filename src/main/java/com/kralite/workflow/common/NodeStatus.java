package com.kralite.workflow.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kralite on 2019/1/20.
 */
public class NodeStatus {
    private Integer runCount = 0;
    private List<Date> runTime = new ArrayList<Date>();

    public void clear(){
        runCount = 0;
        runTime.clear();
    }

    public synchronized void addRunRecord(Date time){
        runCount += 1;
        runTime.add(time);
    }
}
