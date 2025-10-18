
# Spring MVC

程序框架搭建流程

> step1 创建项目

1. 创建 java module，使用 maven 管理
2. 修改打包配置为 war 包
3. 引入依赖 [pom.xml](./pom.xml)

> step2 增加 web 支持

在模块的 `src/main` 目录下新建 `webapp` 目录。(默认是带有小蓝点的，没有的话，需要在module设置中增加web支持)。另外，在添加 web 支持的时候需要添加 `web.xml` 支持，添加时需要注意添加的路径;

> step3 增加 spring 支持

在 `web.xml` 文件中配置 `org.springframework.web.servlet.DispatcherServlet` (SpringMVC框架内置的一个类，前端控制器)，所有的请求都应该经过这个 `DispatcherServlet`;

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">

    <!-- 配置 SpringMVC的前端控制器，对浏览器发送的请求进行统一处理 -->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <!--
            /: 表示除了 .jsp 结尾的所有请求路径都可以匹配上 (jsp底层本质上就是一个 servlet，如果是 jsp 的请求，则应交给它自己处理)
            /*: 表示所有的请求路径
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

`DispatcherServlet` 是 `SpringMVC` 框架提供的最核心类，它是整个 `SpringMVC` 框架的前端控制器，负责接收 `HTTP` 请求、将请求路由到处理程序、处理响应信息、最终将响应返回给客户端。

`DispatcherServlet` 是 `Web` 应用程序的主要入口之一，主要职责包括:

1. 接收客户端的 `Http` 请求: `DispatcherServlet` 监听来自 `Web` 浏览器的 http 请求，然后根据请求的 url 将请求数据解析为 `Request` 对象;
2. 处理请求的 `URL`: `DispatcherServlet` 将请求的 `URL` 与处理程序进行匹配，确定需要调用哪个控制器 (`Controller`) 来处理这个请求;
3. 调用响应的控制器: `DispatcherServlet` 将请求分发给找到的控制处理器，执行业务逻辑，然后返回一个模型对象 (`Model`)；
4. 渲染视图: `DispatcherServlet` 将调用视图引擎，将模型对象呈现为用户可以看到的 `HTML` 页面;
5. 返回响应给客户端: `DispathcerServlet` 将为用户生成的响应发送给浏览器，响应可以包括 表单，JSON，XML、HTML等;


> step4 创建控制处理器

新建一个请求处理器 [`HelloController`](./src/main/java/com/example/spring/controller/HelloController.java) ，添加 `@Controller` 注解，将该处理器交给 spring 的 ioc 容器管理;

> step5 配置 SpringMVC 框架自己的配置文件

该配置文件有默认的名字 `<servlet-name>` 标签下的名称加一个 `-servlet.xml`，例如: `springmvc-servlet.xml`

该配置文件有默认的存放位置，在 `WEB-INF` 目录下;



## 总结

浏览器发送请求，若请求地址符合前端控制器的 `url-pattern`，则该请求就会被前端控制器的 `DispatcherSevlet` 处理。
前端控制器会读取 `SpringMVC` 的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中的 `@RequestMapping` 注解的 `value` 属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法，处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过 `thymeleaf` 对视图进行渲染，最终转发到视图对应页面;


