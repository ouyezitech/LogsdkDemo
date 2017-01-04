package tech.ouyezi.logsdk;

import org.apache.log4j.Layout;
import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;

import java.io.IOException;

/**
 * Created by Daniel on 16/12/17.
 * daniel@eggplant-tech.com
 */

public class MultiLevelRollingFileAppender extends RollingFileAppender {
    protected Priority[] thresholds;

    public MultiLevelRollingFileAppender(Layout layout, String filename) throws IOException {
        super(layout, filename);
    }

    public MultiLevelRollingFileAppender() {
        super();
    }

    public MultiLevelRollingFileAppender(Layout layout, String filename, boolean append) throws IOException {
        super(layout, filename, append);
    }

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        if(thresholds == null){
            return true;
        }else{
            for(Priority threshold: thresholds){
                if(priority.equals(threshold)){
                    return true;
                }
            }
            return false;
        }
    }

    public void setThresholds(Priority[] thresholds) {
        this.thresholds = thresholds;
    }

}
