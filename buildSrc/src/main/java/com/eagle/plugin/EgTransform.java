package com.eagle.plugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author linquandong
 * @create 2021/10/29
 * @Describe
 **/
public class EgTransform extends Transform {
    //所有的注册类
    static List<String> registerList = new ArrayList<>();

    //Router.class所在的jar包
    static File destFile;

    @Override
    public String getName() {
        return "EagleTranform";
    }
    /**
     * 处理类型
     * @return
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }
    /**
     * 处理范围
     * @return
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        for (TransformInput input:inputs){
            //自己本身的class
           Collection<DirectoryInput> directoryInputs =  input.getDirectoryInputs();
           //本地依赖或者远程仓库依赖的class
           Collection<JarInput> jarInputs = input.getJarInputs();

            processDirect(directoryInputs,outputProvider);
            processJar(jarInputs,outputProvider);


        }
    }

    private void processJar(Collection<JarInput> jarInputs, TransformOutputProvider outputProvider) {
    }

    private void processDirect(Collection<DirectoryInput> directoryInputs, TransformOutputProvider outputProvider) {
        for (DirectoryInput directoryInput:directoryInputs){
            File dir = outputProvider.getContentLocation(directoryInput.getName(),
                    directoryInput.getContentTypes(), directoryInput.getScopes(),
                    Format.DIRECTORY);
            traverstToFiles(dir);
        }
    }

    private void traverstToFiles(File dir) {
        //获取目录下的子文件
        File[] files = dir.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                traverstToFiles(file);
            }else if(file.isFile()){

            }
        }
    }
}
