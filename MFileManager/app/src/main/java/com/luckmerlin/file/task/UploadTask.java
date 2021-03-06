package com.luckmerlin.file.task;

import com.luckmerlin.file.Folder;
import com.luckmerlin.file.Path;
import com.luckmerlin.task.OnTaskUpdate;
import com.luckmerlin.task.Result;
import com.luckmerlin.task.Task;

import java.util.List;

public class UploadTask extends ActionTask{
    private final Folder mTargetFolder;

    public UploadTask(List<Path> paths, Folder folder) {
        super(paths);
        mTargetFolder=folder;
    }

    public final Folder getTargetFolder() {
        return mTargetFolder;
    }

    @Override
    protected Result onExecute(Task task, OnTaskUpdate callback) {
        return null;
    }
}
