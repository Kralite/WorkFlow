package com.kralite.workflow.common;

/**
 * Created by Kralite on 2019/1/20.
 */
public class LineStatus {
    private boolean finished = false;

    public LineStatus(boolean finished) {
        this.finished = finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}
