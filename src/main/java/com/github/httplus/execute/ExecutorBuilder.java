package com.github.httplus.execute;

import com.github.httplus.annotation.AnnotationParser;
import com.github.httplus.Configuration;

public class ExecutorBuilder {

    /**
     * 建造请求工厂
     *
     * @param executable 请求对象
     * @return
     */
    public Executor build(Executable executable) throws ClassNotFoundException {
        AnnotationParser annotationParser = new AnnotationParser(executable);
        return build(annotationParser.parse());
    }

    /**
     * 建造请求工厂
     *
     * @param configuration
     * @return
     */
    private Executor build(Configuration configuration) {
        return new DefaultExecutor(configuration);
    }

}
