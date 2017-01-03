package tech.ouyezi.logsdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.apache.log4j.Logger;
import tech.ouyezi.logsdk.LogManager;

public class MainActivity extends AppCompatActivity {
    Logger mLog = Logger.getLogger(MainActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogManager.init(this);
        mLog.debug("this is a debug log!");
        mLog.info("this is a info log");
        mLog.warn("this is a warning log");
        mLog.error("this is a error log");
        mLog.fatal("this is a fatal log");
    }
}
