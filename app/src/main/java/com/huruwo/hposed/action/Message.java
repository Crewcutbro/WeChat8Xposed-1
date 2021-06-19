package com.huruwo.hposed.action;

import android.content.ContentValues;

import com.gh0u1l5.wechatmagician.spellbook.base.Operation;
import com.gh0u1l5.wechatmagician.spellbook.interfaces.IDatabaseHook;
import com.huruwo.hposed.utils.LogXUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Message implements IDatabaseHook   {
    @NotNull
    @Override
    public Operation<Long> onDatabaseInserted(@NotNull Object thisObject, @NotNull String table, @Nullable String nullColumnHack, @Nullable ContentValues initialValues, int conflictAlgorithm, @Nullable Long result) {
        if (table == "message") {
            LogXUtils.e("New Message:"+initialValues);
        }
        return null;
    }

    @NotNull
    @Override
    public Operation<Object> onDatabaseOpening(@NotNull String path, @Nullable Object factory, int flags, @Nullable Object errorHandler) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Object> onDatabaseOpened(@NotNull String path, @Nullable Object factory, int flags, @Nullable Object errorHandler, @Nullable Object result) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Object> onDatabaseQuerying(@NotNull Object thisObject, @Nullable Object factory, @NotNull String sql, @Nullable String[] selectionArgs, @Nullable String editTable, @Nullable Object cancellationSignal) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Object> onDatabaseQueried(@NotNull Object thisObject, @Nullable Object factory, @NotNull String sql, @Nullable String[] selectionArgs, @Nullable String editTable, @Nullable Object cancellationSignal, @Nullable Object result) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Long> onDatabaseInserting(@NotNull Object thisObject, @NotNull String table, @Nullable String nullColumnHack, @Nullable ContentValues initialValues, int conflictAlgorithm) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Integer> onDatabaseUpdating(@NotNull Object thisObject, @NotNull String table, @NotNull ContentValues values, @Nullable String whereClause, @Nullable String[] whereArgs, int conflictAlgorithm) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Integer> onDatabaseUpdated(@NotNull Object thisObject, @NotNull String table, @NotNull ContentValues values, @Nullable String whereClause, @Nullable String[] whereArgs, int conflictAlgorithm, int result) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Integer> onDatabaseDeleting(@NotNull Object thisObject, @NotNull String table, @Nullable String whereClause, @Nullable String[] whereArgs) {
        return null;
    }

    @NotNull
    @Override
    public Operation<Integer> onDatabaseDeleted(@NotNull Object thisObject, @NotNull String table, @Nullable String whereClause, @Nullable String[] whereArgs, int result) {
        return null;
    }

    @Override
    public boolean onDatabaseExecuting(@NotNull Object thisObject, @NotNull String sql, @Nullable Object[] bindArgs, @Nullable Object cancellationSignal) {
        return false;
    }

    @Override
    public void onDatabaseExecuted(@NotNull Object thisObject, @NotNull String sql, @Nullable Object[] bindArgs, @Nullable Object cancellationSignal) {

    }
}
