# LogsdkDemo
add configure in gradle：
 manifestPlaceholders = [
                    LOG_DIR_NAME   : "AndroidMoveItSDK",
                    LOG_FILE_NAME    : "moveitsdk_debug",
                    LOG_ROOT_LEVEL    : "DEBUG"
            ]
call LogManager.init(context);
