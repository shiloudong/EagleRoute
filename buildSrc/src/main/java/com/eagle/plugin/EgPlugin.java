package com.eagle.plugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class EgPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        AppExtension android = project.getExtensions().getByType(AppExtension.class);
        android.registerTransform(new EgTransform());
    }
}
