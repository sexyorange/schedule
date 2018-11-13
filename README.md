# 超级课程表
## 完成“按教师查询”，并在手机端显示，不使用缓存技术
- 输入姓名前几位字母，可以智能列表显示，z教师姓名
- 输入校验码
- 按教师查询其某一学期的课程，按周显示，界面易用
### 出现问题
1. Closing socket.: Connection reset by peer<br>
 服务器返回了“RST”时，如果此时客户端正在往Socket套接字的输入流中写数据则会提示“Connection reset by peer”。<br>
2. 启动之后一段时间 屏幕空
- 冷启动：当启动应用时，后台没有该应用的进程，这时系统会重新创建一个新的进程分配给该应用，这个启动方式就是冷启动。<br>
  特点：冷启动因为系统会重新创建一个新的进程分配给它，所以会先创建和初始化application类，再创建和初始化MainActivity类（包括一系列的测量、布局、绘制），最后显示在界面上。
- 热启动：当启动应用时，后台已有该应用的进程（例：按back键、home键，应用虽然会退出，但是该应用的进程是依然会保留在后台，可进入任务列表查看），所以在已有进程的情况下，这种启动会从已有的进程中来启动应用，这个方式叫热启动。<br>
  特点：热启动因为会从已有的进程中来启动，所以热启动就不会走application这步了，而是直接走MainActivity（包括一系列的测量、布局、绘制），所以热启动的过程只需要创建和初始化一个MainActivity就行了，而不必创建和初始化application，因为一个应用从新进程的创建到进程的销毁，application只会初始化一次。
  
3. android4.0 以后不允许主线程直接访问网络 需要new thread<br>
4. Android高版本联网失败报错:Cleartext HTTP traffic to xxx not permitted解决方法<br>
 [https://blog.csdn.net/gengkui9897/article/details/82863966]<br>
5. 子线程更新UI<br>
 [https://www.cnblogs.com/joy99/p/6121280.html]<br>
 在子线程执行某段代码，需要更新UI的时候去通知主线程，让主线程来更新。如何做呢？常见的方法，除了前面提到的在UI线程创建Handler，在子线程发送消息到UI线程，通知UI线程更新UI，还有 handler.post(Runnable r)、 view.post(Runnable r)、activity.runOnUIThread(Runnable r)等方法。
#### 注：
![智能显示教师列表]()
