[TOC]
# Docker笔记

## Docker常用命令

### 帮助命令

```shell
docker COMMAND --help # 查看所有帮助
```



### 镜像命令

###### docker images  查看所有本地主机的镜像

~~~shell
REPOSITORY                   TAG       IMAGE ID       CREATED        SIZE
linuxserver/libreoffice      <none>    e137c22a1823   2 weeks ago    1.2GB

#解释
REPOSITORY 镜像的仓库源
TAG				 镜像的标签
IMAGE ID   镜像ID
CREATED    创建时间
SIZE			 镜像大小

# 参数说明
-a -all    			#列出所有镜像
-q --quiet 			# 只显示镜像ID
~~~

###### docker search 搜索镜像

```shell
[root@iZ8vb3dwh9qaj9jt1nkz36Z ~]# docker search mysql
NAME                              DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
mysql                             MySQL is a widely used, open-source relation…   11354     [OK]
mariadb                           MariaDB Server is a high performing open sou…   4311      [OK]
mysql/mysql-server                Optimized MySQL Server Docker images. Create…   844                  [OK]

# 参数说明
--filter=stars=3000 # 搜索stars数大于3000的
```

###### docker pull 下载镜像

```shell
docker pull 镜像名[:tag] #如果不写tag，默认就是latest
```

###### docker rmi 删除镜像

```shell
docker rmi 镜像ID 							# 删除镜像
docker rmi -f 镜像ID 						# 强制删除镜像
docker rmi -f 镜像ID 	镜像ID 镜像ID 					# 强制删除多个镜像
docker rmi -f ${docker images -aq}   # 强制删除全部的容器
```



### 容器命令

```shell
docker run [可选参数] 镜像ID
# 参数说明
--name="Name" 							# 容器名称（自定义，用于区分容器）tomcat01 tomcat02
-d													# 表示后台运行容器 并返回容器Id
-it 												# 使用交互方式运行，进入容器内部查看内容
-p													# 指定容器的端口 -p 8080:8080（即将容器8080端口映射到主机8080端口）
	-p ip:主机端口:容器端口
	-p 主机端口:容器端口 （常用）
	-p 容器端口
	容器端口
-P 										# 随机指定容器端口
--restart=always 						#代表开启启动
--privileged=true						#代表进入容器内部为管理员身份
-v 										#把mysql产生的数据同步(挂载)到本地 防止数据丢失
-e 										#容器传参  设置mysql的初始密码

docker run -it 容器名称/id /bin/bash      # 启动并进入容器内部
exit									 # 从容器中退回主机
```

###### 列出所有运行的容器

~~~shell
docker ps 命令
# 参数说明
-a 						# 列出所有容器，包含正在运行的容器以及历史容器
-n=? 					# 显示最近创建的容器
-q 						# 只显示容器id
~~~

###### 退出容器

~~~shell
exit  				# 直接退出容器
Ctrl + P + Q  # 容器不停止退出
~~~

###### 删除容器

~~~shell
docker rm 容器ID
docker rm -f ${docker ps -aq} 		# 删除所有的容器
docker ps -a -q|xargs docker rm   # 删除所有的容器
~~~

###### 启动和停止容器

~~~shell
docker start 容器id
docker restart 容器id
docker stop 容器id
docker kill 容器id    # 强制停止当前容器
~~~

###### 运行容器添加新的端口映射

修改要端口映射的容器的配置文件

```bash
#1、查看容器的信息
docker ps -a
 
#2、查看容器的端口映射情况，在容器外执行：
docker port 容器ID 或者 docker port 容器名称
 
#3、查找要修改容器的全ID
docker inspect 容器ID |grep Id
 
#4、进到/var/lib/docker/containers(具体路径通过 docker inspect 容器Id 查看) 目录下找到与全 Id 相同的目录，修改 其中的hostconfig.json 和 config.v2.json文件：
#注意：若该容器还在运行中，需要先停掉
docker stop 容器ID
#再停掉docker服务
systemctl stop docker
 
#5、修改hostconfig.json如下，添加端口绑定"9003/tcp": [{"HostIp": "","HostPort": "9003"}]，表示绑定端口9003
 
#6、修改config.v2.json在ExposedPorts中加上要暴露的端口，即9003

#7、改完之后，重启docker服务和容器
systemctl restart docker
docker start 容器名

#8、此时，可以查看宿主机端口是否和容器内端口映射成功，在容器外执行
netstat -an |grep 宿主机的映射端口
```

###### 实时查看容器日志

```bash
docker logs -f  --tail=100 f41c6a557eb6
docker logs -f -t --since=“2017-05-31” --tail=10 f41c6a557eb6

–since : 此参数指定了输出日志开始日期，即只输出指定日期之后的日志。
-f : 查看实时日志
-t : 查看日志产生的日期
-tail=10 : 查看最后的10条日志。
f41c6a557eb6 : 容器Id
```

### 镜像导入导出

##### docker镜像的导出

~~~shell
docker save [options]  images [images...]

docker save -o dockerdemo.tar  dockerdemo
docker save > dockerdemo.tar dockerdemo
# 参数说明
-o和>表示输出到文件
dockerdemo.tar为导出的目标文件
dockerdemo为源镜像名
如果不指定路径，默认是当前文件夹。 
~~~

##### docker镜像的导入

~~~shell	
docker load [options]

docker load -i dockerdemo.tar
docker load < dockerdemo.tar
# 参数说明
-i(i即imput)和<表示从文件输入,上面的两个命令都会成功导入镜像以及相关元数据，包括tag信息。
~~~



### 容器导入导出

###### docker容器的导出

~~~shell	
docker export [options]  container

docker export -o D:\containers\dockerdemocontainer.tar dockerdemo
# 参数说明
-o表示输出的文件，这里指定了输出的路径，如果没有指定路径，则默认生成到当前文件夹。
dockerdemocontainer.tar为目标文件，
dockerdemo为源容器名。
~~~

###### docker容器的导入

~~~shell
docker import [options] file|URL|- [REPOSITORY[:TAG]]

docker import dockerdemocontainer.tar dockerdemo:imp
# 参数说明
dockerdemocontainer.tar表示要导入的容器，
dockerdemo表示导入后的镜像名称，
imp表示给导入的镜像打tag。
~~~

### 两种导出导入方案的差别

**两种方法不可混用。**
如果使用 **import** 导入 **save** 产生的文件，虽然导入不提示错误，但是启动容器时会提示失败，会出现类似"**docker: Error response from daemon: Container command not found or does not exist**"的错误。

1. 镜像导入是一个复制的过程，容器导入是将当前容器变成一个新的镜像。

2. docker save命令保存的是镜像（image），docker export命令保存的是容器（container）。

3. export命令导出的tar文件略小于save命令导出的。

4. 因为export导出的是容器，export导出的文件在import导入时，无法保留镜像所有的历史（即每一层layer信息），不能进行回滚操作。而save是根据镜像来的，所以导入时可以完整保留下每一层layer信息。如下图所示：dockerdemo:latest是save导出load导入的，dockerdemo:imp是export导出import导入的。

   ![1033738-20200711115830169-1262686556](https://gitee.com/ming-xiangyu/Imageshack/raw/master/img/1033738-20200711115830169-1262686556.png)

5. docker load不能对导入的镜像重命名，而docker import导入可以为镜像指定新名称。例如，上面导入的时候指定dockerdeom:imp。

   > 1. 若是只想备份image，使用save和load。
   > 2. 若是在启动容器后，容器内容有变化，需要备份，则使用export和import

### 常用命令

###### 查看日志

~~~sh
docker logs
~~~

###### 进入当前正在运行的容器

~~~sh
docker exec -it 容器id bashShell
docker attach 容器id

# docker exec 						进入容器后开启一个新的终端，可以在里面操作（常用）
# docker attach 					进入容器正在执行的终端，不会启动新的进程
~~~

###### 从容器内拷贝文件到主机上

~~~sh
docker cp 容器id:容器内路径 目的地路径（如果为宿主机当前文件夹路径，可以直接`.`代替目的地路径）
# 将容器文件拷贝到主机上
#docker cp 402:/home/test.java .
~~~

###### 从主机内拷贝文件到容器上

```sh
docker cp 宿主机目录/文件  容器:/目录/
```

###### docker运行

~~~sh
docker run -d --name nginx01 -p 5566:80 nginx

# -d 后台运行
# --name 指定的容器名称
# -p 指定端口 宿主机端口:容器端口

~~~

###### 查看容器信息

~~~sh
docker inspect 容器id
~~~

~~~sh
docker run -it --rm tomcat:9.0 # 测试时使用，用完即删
~~~

###### 查看CPU状态

~~~sh
docker stats  # 查看CPU状态
~~~



## 使用数据卷

> 方法一：直接使用命令挂载 -v

~~~sh
docker run -it -v 主机地址：容器内目录
~~~



## 安装mysql

~~~sh
# 获取镜像
docker pull mysql

# 运行容器需要数据挂载 需要配置密码
docker run -d -p 3307:3306 -v /data/mysql/conf:/etc/mysql/conf.d -v /data/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 mysql

# 官方启动命令
 docker run --name some-mysql -v /my/custom:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
 
# 如果要将所有表的默认编码和排序规则更改为使用 UTF-8 ( utf8mb4)，只需运行以下命令：
 docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
~~~



## 具名和匿名挂载

**正在运行的容器无法挂载，须在启动容器进行指定： docker run -v 宿主机目录:容器目录  容器镜像**

~~~sh
# 匿名挂载 在-v的时候只写了容器内部的路径，没有写外部的路径
-v 容器内路径
docker run -d -P --name nginx01 -v /etc/nginx nginx

# 查看所有的volume情况
[root@iZ8vb3dwh9qaj9jt1nkz36Z data]# docker volume ls
DRIVER    VOLUME NAME
local     0e1eaf15e16bb7bd67c041cd50f517439c8d269a706cd18cfa49c5dc07c36ada

#具名挂载 在-v的时候加上卷名和容器内部的路径 卷名不能用 / 开头，否则认为宿主机路径


# 通过ocker volume inspect 卷名称可以查看该卷对应宿主机路径
[root@iZ8vb3dwh9qaj9jt1nkz36Z ~]# docker volume inspect 79db5ab3c8f2d90773ea014bd5450ef25d9be7fc205fa607b976563ad20952cf
~~~

所有docker容器内的卷，在没有指定目录的情况下都是在<u>/var/lib/docker/volumes/</u>内

通过具名挂载可以方便的找到卷，建议使用具名挂载

~~~ 
# 如何确定时具名挂载还是匿名挂载，还是指定路径挂载
-v 容器路径       								  	# 匿名挂载
-v 卷名（不能以/开头）：容器内路径			  #  具名挂载
-v /宿主机路径：容器内路径							 # 指定路径挂载
~~~



## dockerFile

构建dockerfile

~~~sh
FROM centos

VOLUME ["volume01","volume02"]

CMD echo "-----end-----"

cmd /bin/bash
~~~

dockerFile说明
~~~shell
FROM　　基础镜像，当前新镜像是基于哪个镜像的
MAINTAINER　　镜像维护者的姓名和邮箱地址
RUN　　容器构建时需要运行的命令
EXPOSE　　当前容器对外暴露出的端口
WORKDIR　　指定在创建容器后，终端默认登录进来的工作目录，一个落脚点
ENV　　用来在构建镜像过程中设置环境变量
AD　　将宿主机目录下的文件拷贝进镜像且ADD命令会自动处理URL和解压tar压缩包
COPY　　类似ADD，拷贝文件和目录到镜像中。将从构建上下文目录中<源路径>的文件/目录复制到新的一层的镜像内的<目标路径>位置
VOLUME　　容器数据卷，用于数据保存和持久化工作
CMD　　指定一个容器启动时要运行的命令。Dockerfile中可以有多个CMD指令，但只要最后一个生效，CMD会被docker run之后的参数替换
ENTRYPOINT　　指定一个容器启动时要运行的命令。ENTRYPOINT的目的和CMD一样，都是在指定容器启动程序及参数
ONBUILD　　当构建一个被继承的Dockerfile时运行命令，父镜像在被子继承后父镜像的onbuild被触发
~~~

## Docker 设置国内镜像源

### 国内源

~~~sh
Docker中国区官方镜像
https://registry.docker-cn.com

网易
http://hub-mirror.c.163.com

ustc 
https://docker.mirrors.ustc.edu.cn

中国科技大学
https://docker.mirrors.ustc.edu.cn
~~~

### 修改方法

创建或修改 `/etc/docker/daemon.json` 文件，修改为如下形式

```xml
{
 "registry-mirrors" : [
   "https://mirror.ccs.tencentyun.com",
   "http://registry.docker-cn.com",
   "http://docker.mirrors.ustc.edu.cn",
   "http://hub-mirror.c.163.com"
 ],
 "insecure-registries" : [
   "registry.docker-cn.com",
   "docker.mirrors.ustc.edu.cn"
 ],
 "debug" : true,
 "experimental" : true
}
```

加载重启docker