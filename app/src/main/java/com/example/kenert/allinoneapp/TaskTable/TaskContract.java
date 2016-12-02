package com.example.kenert.allinoneapp.TaskTable;

/**
 * Created by kenert on 27.11.2016.
 */
import android.provider.BaseColumns;
public class TaskContract {
    public static final String DB_NAME = "com.example.kenert.allinoneapp";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns{
        public static final String TABLE = "tasks";

        public static final String TASK_TITLE= "title";
    }
}
