# 使用httplus发送请求

**1. 定义请求：**

```java
package com.github.httplus;

import com.github.httplus.annotation.Request;
import com.github.httplus.annotation.RequestBody;
import com.github.httplus.annotation.RequestHeader;
import com.github.httplus.annotation.RequestParam;
import com.github.httplus.execute.Executable;

@Request(value="https://domain.com/test", method = "POST")
public class MyRequest implements Executable {

    // 请求头
    @RequestHeader("Content-Type")
    private String contentType = "application/json;charset=UTF-8";

    // 请求参数
    @RequestParam("param_name")
    private String paramName = "param_value";

    // 请求数据
    @RequestBody(type = "JSON")
    private Object data;
}
```



**2. 执行请求：**

```java
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
```



