package tech.ouyezi.logsdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import org.apache.log4j.Level;

import java.io.File;

/**
 * Created by Daniel on 15/7/17.
 */
public class LogManager {
    private final static LogConfigurator mLogConfigrator = new LogConfigurator();

    private static String sLogDirName = "";
    private static String sLogFileName = "";
    private static String sLogRootLevel = "debug";

    private static boolean sHasInit = false;
    public synchronized static void init(Context context){
        if(!sHasInit) {
            try {
                ApplicationInfo appInfo = context.getPackageManager()
                        .getApplicationInfo(context.getPackageName(),
                                PackageManager.GET_META_DATA);

                sLogDirName = appInfo.metaData.getString("LOG_DIR_NAME");
                sLogFileName = appInfo.metaData.getString("LOG_FILE_NAME");
                sLogRootLevel = appInfo.metaData.getString("LOG_ROOT_LEVEL");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();

            }
            configureLog4j(context);
            sHasInit = true;
        }
    }

    private static void configureLog4j(Context context) {
        String dirPath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            if(TextUtils.isEmpty(sLogDirName)) {
                dirPath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + File.separator + "logs";
            }else{
                dirPath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + File.separator + sLogDirName + File.separator + "logs";
            }
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            if(TextUtils.isEmpty(sLogDirName)) {
                dirPath = context.getFilesDir().getAbsolutePath() + File.separator + "logs";
            }else{
                dirPath = context.getFilesDir().getAbsolutePath() + File.separator + sLogDirName + File.separator + "logs";
            }
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = dirPath+ File.separator + sLogFileName;
        String filePattern = "%d - [%c] - %p : %m%n";
        int maxBackupSize = 3;
        long maxFileSize = 5*1024 * 1024;
        configure( filePath, filePattern, maxBackupSize, maxFileSize );
    }

    private static void configure(String fileName, String filePattern, int maxBackupSize, long maxFileSize ) {
        mLogConfigrator.setFileName( fileName );
        mLogConfigrator.setMaxFileSize( maxFileSize );
        mLogConfigrator.setFilePattern(filePattern);
        mLogConfigrator.setMaxBackupSize(maxBackupSize);
        mLogConfigrator.setUseLogCatAppender(true);
        mLogConfigrator.setRootLevel(Level.toLevel(sLogRootLevel));
        mLogConfigrator.configure();
    }

    public static org.apache.log4j.Logger getLogger( String name ) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( name );
        return logger;
    }
}
