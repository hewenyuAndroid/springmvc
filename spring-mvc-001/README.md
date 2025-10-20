# Spring MVC

程序框架搭建流程

> step1 创建项目

1. 创建 java module，使用 maven 管理
2. 修改打包配置为 war 包
3. 引入依赖 [pom.xml](./pom.xml)

> step2 增加 web 支持

在模块的 `src/main` 目录下新建 `webapp` 目录。(默认是带有小蓝点的，没有的话，需要在module设置中增加web支持)。另外，在添加 web
支持的时候需要添加 `web.xml` 支持，添加时需要注意添加的路径;

> step3 增加 spring 支持

在 `web.xml` 文件中配置 `org.springframework.web.servlet.DispatcherServlet` (SpringMVC框架内置的一个类，前端控制器)
，所有的请求都应该经过这个 `DispatcherServlet`;

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

`DispatcherServlet` 是 `SpringMVC` 框架提供的最核心类，它是整个 `SpringMVC` 框架的前端控制器，负责接收 `HTTP`
请求、将请求路由到处理程序、处理响应信息、最终将响应返回给客户端。

`DispatcherServlet` 是 `Web` 应用程序的主要入口之一，主要职责包括:

1. 接收客户端的 `Http` 请求: `DispatcherServlet` 监听来自 `Web` 浏览器的 http 请求，然后根据请求的 url 将请求数据解析为
   `Request` 对象;
2. 处理请求的 `URL`: `DispatcherServlet` 将请求的 `URL` 与处理程序进行匹配，确定需要调用哪个控制器 (`Controller`)
   来处理这个请求;
3. 调用响应的控制器: `DispatcherServlet` 将请求分发给找到的控制处理器，执行业务逻辑，然后返回一个模型对象 (`Model`)；
4. 渲染视图: `DispatcherServlet` 将调用视图引擎，将模型对象呈现为用户可以看到的 `HTML` 页面;
5. 返回响应给客户端: `DispathcerServlet` 将为用户生成的响应发送给浏览器，响应可以包括 表单，JSON，XML、HTML等;

> step4 创建控制处理器

新建一个请求处理器 [`HelloController`](./src/main/java/com/example/spring/controller/HelloController.java) ，添加
`@Controller` 注解，将该处理器交给 spring 的 ioc 容器管理;

> step5 配置 SpringMVC 框架自己的配置文件

该配置文件有默认的名字 `<servlet-name>` 标签下的名称加一个 `-servlet.xml`，例如: `springmvc-servlet.xml`

该配置文件有默认的存放位置，在 `WEB-INF` 目录下;

## 总结

浏览器发送请求，若请求地址符合前端控制器的 `url-pattern`，则该请求就会被前端控制器的 `DispatcherSevlet` 处理。
前端控制器会读取 `SpringMVC` 的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中的 `@RequestMapping` 注解的
`value` 属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法，处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过
`thymeleaf` 对视图进行渲染，最终转发到视图对应页面;

# @RequestMapping 注解

`@RequestMapping` 注解的作用是将请求和处理请求的控制器方法关联起来，建立映射关系。`Spring MVC`
接收到指定的请求，通过映射关系找到对应的控制器方法来处理请求;

```java
// 可以作用在类上和方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
@Reflective({ControllerMappingReflectiveProcessor.class})
public @interface RequestMapping {
    String name() default "";

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};

    RequestMethod[] method() default {};

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
```

## `@RequestMapping` 注解位置

- 作用在类上: 设置映射请求的请求路径的初始信息;
- 作用在方法上: 设置映射请求请求路径的具体信息;

```java

@Controller
@RequestMapping("/test")
public class TestRequestMappingController {

    /**
     * 由于 @RequestMapping 注解在类上有配置，因此，资源的 URI 为 /springmvc/test/testSuccess.html
     */
    @RequestMapping("/testSuccess")
    public String testSuccess() {
        return "testSuccess";
    }
}
```

## `@RequestMapping` 注解的 `value` 属性

`@RequestMapping` 注解的 `value` 属性通过请求的请求地址匹配请求映射;

`@RequestMapping` 注解的`value` 属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址所对应的请求;

`@RequestMapping` 注解的 `value` 属性必须设置，至少通过请求地址匹配请求映射;

```java
/**
 * @RequestMapping.value 属性能够配置多个 uri，表示多个uri访问的是同一个资源
 */
@RequestMapping(
        value = {"/test", "/test2"}
)
public String testMappingValue() {
    return "testSuccess";
}
```

## `@RequestMapping` 注解的method属性

`@RequestMapping` 注解的method属性通过请求的请求方式（get或post）匹配请求映射

`@RequestMapping` 注解的method属性是一个`RequestMethod` 类型的数组，表示该请求映射能够匹配多种请求方式的请求

若当前请求的请求地址满足请求映射的 `value` 属性，但是请求方式不满足`method`属性，则浏览器报错
`405：Request method 'POST' not supported`;

如果没有填写 `method` 属性，则 `get` `post` 请求方式都可以访问;


## 路径参数

原始方式：/deleteUser?id=1

rest方式：/deleteUser/1

`SpringMVC` 路径中的占位符常用于 `RESTful` 风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，在通过 `@PathVariable` 注解，将占位符所表示的数据赋值给控制器方法的形参

```java
@RequestMapping("/save/{userId}/{username}")
public String testPathVariable(@PathVariable("userId") String userId, @PathVariable("username") String username) {
  System.out.println("userId = " + userId + ", username = " + username);
  return "testSuccess";
}
```

# `SpringMVC` 获取请求参数

## 通过 `ServletAPI` 获取

将HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的参数表示封装了当前请求的请求报文的对象;

```java
@RequestMapping("/use_servlet_api")
public String useServletApi(HttpServletRequest request) {
  String username = (String) request.getParameter("username");
  String password = (String) request.getParameter("password");
  System.out.println("username: " + username + " password: " + password);
  return "testSuccess";
}
```

## 通过控制器方法的形参获取

在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在 `DispatcherServlet` 中就会将请求参数赋值给相应的形参;

若请求参数中出现多个同名的请求参数，可以在形参位置设置字符串类型或字符串数组类型接收此参数，若使用字符串类型参数接收同名参数，则 `spring` 会使用 `,` 拼接多个同名的参数 

```java
@RequestMapping("/use_method_param")
public String useMethodParam(@RequestParam("username") String username, @RequestParam("password") String password) {
   System.out.println("username: " + username + " password: " + password);
   return "testSuccess";
}

@RequestMapping("/use_method_param")
public String useMethodParam(@RequestParam("username") String username, @RequestParam("class") String clazz) {
    // 假设 clazz 有多个同名的参数，则会使用 , 拼接，例如: java,c++,go
    return "testSuccess";
}

@RequestMapping("/use_method_param")
public String useMethodParam(@RequestParam("username") String username, @RequestParam("class") String[] clazz) {
    // 也可以使用字符串数组接收
    return "testSuccess";
}
```

`@RequestParam` 是将请求参数和控制器方法的形参创建映射关系，`@RequestParam`注解一共有三个属性：

- `value`：指定为形参赋值的请求参数的参数名;
- `required`：设置是否必须传输此请求参数;
  - 若设置为`true`时  (默认值为`true`)，则当前请求必须传输value所指定的请求参数，若没有传输该请求参数，且没有设置defaultValue属性，则页面报错 `400：Required String parameter 'xxx' is not present`;
  - 若设置为`false`，则当前请求不是必须传输`value`所指定的请求参数，若没有传输，则注解所标识的形参的值为null;
- `defaultValue`：不管 `required` 属性值为 `true`或`false`，当`value`所指定的请求参数没有传输或传输的值为""时，则使用默认值为形参赋值;


## `@RequestHeader`

`@RequestHeader` 是将请求头信息和控制器方法的形参创建映射关系;
`@RequestHeader` 注解一共有三个属性：`value`、`required`、`defaultValue`，用法同`@RequestParam`;


## `@CookieValue`

`@CookieValue` 是将`cookie`数据和控制器方法的形参创建映射关系；
`@CookieValue`注解一共有三个属性：`value`、`required`、`defaultValue`，用法同`@RequestParam`;


## 通过 `POJO` 获取请求参数

可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

```java
@RequestMapping("/use_pojo")
public String usePojo(User user) {
    System.out.println("user: " + user);
    return "testSuccess";
}
```




