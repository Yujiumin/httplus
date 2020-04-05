package com.github.httplus;

import com.github.httplus.execute.Executor;
import com.github.httplus.execute.ExecutorBuilder;
import com.github.httplus.execute.Response;

public class MyApplication {
    public static void main(String[] args) throws ClassNotFoundException {
        MyRequest myRequest = new MyRequest();
        Executor executor = new ExecutorBuilder().build(myRequest);
        Response response = executor.execute();
    }
}
