spring-cloud-demo是所有项目的父项目，且聚合了所有项目
eureka-server是注册中心
user-provider是提供者，user-provider2是user-provider的副本，只是项目名、构件ID不同，这两个是一个服务
user-consumer是消费者
demo-model是实体类项目，被提供者和消费者共同依赖
zuul是网关，里面有两个拦截器
config-server是远程配置文件服务器，此项目里zuul是远程配置文件客户端，在远程码云配置了token，供zuul的拦截器使用
---
负载均衡：
1. 负载均衡的功能在消费端
2. 负载均衡不需要添加额外的依赖
3. 负载均衡也不需要配置，默认就是开启的，轮询方式
4. 创建一个user-provider2项目，是user-provider项目的副本
    1. 工程名、artifactId、端口号不同
    2. spring.application.name=user-provider必需相同
    3. 测试，启动所有项目，清空并查看两个provider的日志
---    
网关：
1. 网关内过滤器分为pre、route、post及error
2. 执行过程
    1. 浏览器-->pre-->route(route访问消费端，并等待消费端返回)-->post-->返回浏览器
    2. 在pre/route/post任意一个点出错会进入error过滤器，接着再进入一个post对错误处理，最后返回浏览器
---
网关过滤器测试：
1. postman: 127.0.0.1:6660/user/hello    
2. json: {"name":"zs","age":89}
3. headers: myToken 1
4. myToken为1时，拦截器1执行；没有myToken时，拦截器2执行
---
远程集中配置
1. 新建配置并启动config server项目
1. 浏览器访问
    1. 浏览器访问方式：http://xxx:9090/文件名/版本(dev、pro)/分支
    2. 项目名、文件夹在配置文件里已经配置了，这里只需提供文件名、版本、分支名
    3. 如，在码云上建立一个新项目，有两个分支master和feature1，两个分支下建立一个文件夹，文件夹下建立一个demo.properties：
    4. http://localhost:5550/demo/dev 没指定分支，那么默认就是master分支
    5. http://localhost:5550/demo/dev/feature1 访问feature1分支
2. config client读取远程配置
    1. zuul项目是本项目的config client
    2. 在zuul的bootstrap.properties里配置连接config server
        1. bootstrap.properties比application.properties文件级别更高    
        2. bootstrap.properties文件可以给application.properties文件做准备配置
---
远程配置文件更新手动刷新
1. 手动刷新，当远程文件更新后，不需要重启任务项目，但需要我们手动执行一个动作
2. spring cloud也有自动刷新的功能
3. 改造config client项目，即zuul项目
    1. 添加actuator依赖
    2. 在使用远程配置的类上加注解@RefreshScope(spring在启动时会做一次性的依赖注入，所以要加这个注解，以采用不同的策略)
    3. application.properties里添加 management.endpoints.web.exposure.include=refresh
4. 重启zuul项目
5. 更改码云上token的内容
    1. 更改后，config server并不知道git上内容已改，它只是在项目启动时读取了一次远程的配置
    2. 需要config client向config server发起一次刷新请求，逼得config server重读一次远程配置
    3. 使用postman给config client发送post请求，路径是127.0.0.1:6660/actuator/refresh，查看远程配置有没有改变
    4. config client端重新调用普通请求127.0.0.1:6660/user/hello，检查配置有没有生效
---
远程配置文件加密
1. JCE加密
    1. 使用JCE，JDK集成，即 Java Cryptography Extension，它提供对称、不对称加密
    2. 有些国家要求加密强度不能过大，所以jre自带的JCE不支持高强度的加密
    3. 但可以到sun官网下载无限制的版本，然后安装到jdk
2. 安装JEC    
    1. 本地使用jdk1.8，下载对应jdk1.8的JCE压缩包，复制其中的两个jar包到jre/lib/security目录下，这样就解除了加密强度限制
3. 配置环境    
    1. 重启config server项目，在postman访问：127.0.0.1:5550/encrypt/status；返回"status": "INVALID"不行
    2. 在config server里添加bootstrap.properties文件，并添加密钥配置encrypt.key=china，把china做为密钥
    3. 再次访问config server 127.0.0.1:5550/encrypt/status，返回OK，环境可以了
4. 产生密文
    1. post方法访问config server 127.0.0.1:5550/encrypt，body-->raw-->text里写入明文，发送请求，返回密文，密钥是配置文件里的key
    2. post方法访问config server 127.0.0.1:5550/decrypt，body-->raw-->text里写入密文，发送请求，返回明文，密钥是配置文件里的key
    3. 把生成的密文更新到远程配置文件，远程properties文件内容如下：
    ```
    # 字符“1”，密钥china,生成的密文
    # {cipher}表示后面是密文，config server就会解密
    # token={cipher}d687cde5a5c7d3d0d2c6ef434159adbc8af1a773628de96b22bc8d779f81449b
    # 字符“2”，密钥china,生成的密文
    token={cipher}198a316b2d62a9374f2d6a909e71cd8111fa4bcecc8976c4f277b56ea279c50b
   ```
5. 测试    
    1. 访问config client，post请求，路径是127.0.0.1:6660/actuator/refresh，查看远程配置有没有改变
    2. config client端重新调用普通请求127.0.0.1:6660/user/hello，检查配置有没有生效
    
    







    
 








    
    