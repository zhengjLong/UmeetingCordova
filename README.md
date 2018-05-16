目录结构
（1）目录一览：
umeet-android/
|– config.xml
|– hooks/
|– platforms/
| |– android/
|| – ios/
|– plugins/
| |– org.apache.cordova.console/
| |– org.apache.cordova.device/
|- my-project
|– www/
| |– css/
| |– img/
| |– js/
|– index.html

（2）config.xml ：cordova的配置文件

（3）hooks目录
存放自定义cordova命令的脚本文件。每个project命令都可以定义before和after的Hook，比如：before_build、after_build。
Hook可以采用任何编程语言来写，Cordova CLI采用的是Node.js，所以一般都是用它来写。

（4）platforms目录
各个平台的原生代码工程，不要手动修改，因为在build的时候会被覆盖。

（5）plugins目录
插件目录（cordova提供的原生API也是以插件的形式提供的）。

（6）www目录
源代码目录，其中index.html为应用的入口文件。

（7）my-project目录
H5代码目录，一般编译都会编译到www目录里。


#cordova plugin 插件安装方法：
## cordova plugin add ...

# cordova 项目打包运行
## npm install -g cordova（全局安装cordova）
### cordova build android(打包成apk)
### cordova run android(在安卓手机上运行)


