# java小组项目bbs
## 首先关于git上传与下载代码的
### 1.组长或负责人首先创建一个repository仓库
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191124171341872.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)
并由负责人初始化创建一些空的分好任务的代码文件
### 2. 为自己的项目添加成员
进入自己仓库的setting，点击Collaborators即可按用户名或邮箱地址查找添加你的成员了
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191124175458562.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)

### 3.项目组的各个成员将GitHub上的javaweb仓库clone到本地
各个小组的成员将在自己工作的目录下，在导航栏输入cmd回车，即可在当前目录下进入cmd![在这里插入图片描述](https://img-blog.csdnimg.cn/20191124172004904.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)
接着在弹出的cmd窗口输入命令
```shell
git clone [仓库的地址] [自己重命名]
```
仓库的地址可在如图位置进行复制![在这里插入图片描述](https://img-blog.csdnimg.cn/20191124172646751.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019112417271688.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)
运行完命令后，可发现当前目录下新建了项目文件，并且仓库中初始化的文件也一并clone到了本地
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191124172855446.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)
### 4.各个成员之间合作，保持本地与GitHub仓库上的同步
####  将GitHub上别的成员写的文件更新到本地
     一般来说要先进行远程更新到本地这一步操作，否则会报错
	 在工作目录javaweb下打开cmd窗口，运行
```shell
git pull 
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191124173813981.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)如图，在GitHub上新建了index.html文件，运行命令后发现将远程     		的代	码文件更新到了本地
#### 5.将本地的文件更新到GitHub上
本地上传与更新远程到本地不太一样。具体步骤：
1. 依次运行运行命令
```shell
git add .
git commit -m"你要备注的信息"
```
上面第一行代码为进行你的历史区，历史区中会保存你更改的文件，第二行代码为准备上传你在历史区中自动保存的你有修改过的文件，并加上备注内容
	
2. 之后运行命名
```shell
git push
```
便可完成本地上传。
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019112417522852.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMjU3MzU4,size_16,color_FFFFFF,t_70)
如图，为在自己的javaweb文件下创建hello.jsp后本地上传完成的示意图。
## 接着是小组项目master与各个成员合作的分支问题
https://www.jianshu.com/p/4875a0307e23