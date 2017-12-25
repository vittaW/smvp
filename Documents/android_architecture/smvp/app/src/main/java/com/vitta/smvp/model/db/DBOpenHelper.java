package com.vitta.smvp.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 15:27
 * 描述：DBOpenHelper
 */

public class DBOpenHelper extends DatabaseOpenHelper {

    public DBOpenHelper(Context context, String name, int version) {
        super(context, name, version);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
