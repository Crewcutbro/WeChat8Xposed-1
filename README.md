# 项目说明

本项目拷贝自 [https://github.com/Gh0u1L5/WechatSpellbook](https://github.com/Gh0u1L5/WechatSpellbook)

个人尝试做新版微信的适配计划

因为原来的项目因为已经停更了很久 在新版的微信上会有些问题
本人针对新版的微信78做了全新的适配 使其在新版的微信上也可以使用


## 更新说明

|  更新时间   | 更新内容  | 更新说明 |
|  ----  | ----  | ---- |
|2021-6-20 | 修正部分类的特征值 包括有 NotificationManagerCompat 等类的视频 即v4兼容包切换到 androidx 兼容包||
|2021-6-19  |测试代码测试最新版微信的适配程度||
|2021-6-18  |项目结构调整||


## 测试你要写的版本功能是否可用


### 下载apk文件

值得注意的是 原作者的库里留下了测试文件 `MirrorUnitTest` 针对一些目标版本做了测试

从6.60版本一直到6.73版本的适配测试

如何做一个新版的适配测试呢 这就要拉下来我们要适配的版本apk 今天是2021-6-21日，官网最新的版本是 v806版本 

下载链接 https://dldir1.qq.com/weixin/android/weixin806android1900_arm64.apk

我们把它拉下来放到文件下`apks/domestic`目录下面  

### 编写测试可用性脚本

测试脚本新增一行

```kotlin
 @Test fun verifyPlayStorePackage8_0_6() {
        verifyPackage("$DOMESTIC_DIR/wechat-v8.0.6.apk")
    }
```

然后点击运行测试 查看输出日志 分析通过特征值是否找到了指定的目标类

一测就出问题了 没有找到 `NotificationManagerCompat`

```shell script
java.lang.Error: Failed to evaluate NotificationManagerCompat
	at com.gh0u1l5.wechatmagician.spellbook.mirror.android.support.v4.app.Classes$$special$$inlined$wxLazy$1.invoke(WechatGlobal.kt:112)
	at kotlin.SynchronizedLazyImpl.getValue(LazyJVM.kt:74)
	at com.gh0u1l5.wechatmagician.spellbook.WechatGlobal$UnitTestLazyImpl.getValue(WechatGlobal.kt:138)
	at com.gh0u1l5.wechatmagician.spellbook.util.MirrorUtil.generateReportWithForceEval(MirrorUtil.kt:64)
	at com.gh0u1l5.wechatmagician.spellbook.MirrorUnitTest.verifyPackage(MirrorUnitTest.kt:84)
	at com.gh0u1l5.wechatmagician.spellbook.MirrorUnitTest.verifyPlayStorePackage8_0_6(MirrorUnitTest.kt:152)
	at java.lang.reflect.Method.invoke(Native Method)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at androidx.test.internal.runner.junit4.statement.RunBefores.evaluate(RunBefores.java:80)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at androidx.test.ext.junit.runners.AndroidJUnit4.run(AndroidJUnit4.java:104)
	at org.junit.runners.Suite.runChild(Suite.java:128)
	at org.junit.runners.Suite.runChild(Suite.java:27)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
	at androidx.test.internal.runner.TestExecutor.execute(TestExecutor.java:56)
	at androidx.test.runner.AndroidJUnitRunner.onStart(AndroidJUnitRunner.java:388)
	at android.app.Instrumentation$InstrumentationThread.run(Instrumentation.java:2091)
```

### 如何修复这个异常

这个异常意味着特征类没有找到 需要重新制作特征类 也就是要重新适配路径

核心就在mirror文件夹下面 有所有通用映射规则的 也就是wechat应用程序包的类结构映射。

哪个类出现异常就要修复哪个类的问题




## 使用说明

Wechat Spellbook 是一个使用Kotlin编写的开源微信插件框架

详情可以原来项目的README说明[ORGIN_README.md](ORGIN_README.md)

## 其他说明

个人认为这套框架非常有意思，但是作者停更了非常可惜。
两个有意思的地方在于：

1.提供一套合理的框架给第三方开发者 简化开发步骤
2.通过apk的dex分析做出一套忽略版本的差异的 并且可以添加新的特征规则

## 一些相关的笔记 如何依赖 Wechat78Spellbook 做插件开发

[1.WeChat78Xposed通用hook框架适配新版微信-单元测试适配]()
[2.WeChat78Xposed通用hook框架适配新版微信-修复NotificationManagerCompat]()
[3.WeChat78Xposed通用hook框架适配新版微信]()
[4.WeChat78Xposed通用hook框架适配新版微信]()
[5.WeChat78Xposed通用hook框架适配新版微信]()
[6.WeChat78Xposed通用hook框架适配新版微信]()
[7.WeChat78Xposed通用hook框架适配新版微信]()
[8.WeChat78Xposed通用hook框架适配新版微信]()
[9.WeChat78Xposed通用hook框架适配新版微信]()
[10.WeChat78Xposed通用hook框架适配新版微信]()
[11.WeChat78Xposed通用hook框架适配新版微信]()
[12.WeChat78Xposed通用hook框架适配新版微信]()