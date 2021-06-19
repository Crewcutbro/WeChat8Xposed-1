# 项目说明

本项目拷贝自 Gh0u1L5/WechatSpellbook


## 更新说明

因为原来的项目因为已经停更了很久 在新版的微信上会有些问题
本人针对新版的微信78做了全新的适配 使其在新版的微信上也可以使用


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



## 使用说明

Wechat Spellbook 是一个使用Kotlin编写的开源微信插件框架

详情可以原来项目的README说明[ORGIN_README.md](ORGIN_README.md)

## 其他说明

个人认为这套框架非常有意思，但是作者停更了非常可惜。
两个有意思的地方在于：

1.提供一套合理的框架给第三方开发者 简化开发步骤
2.通过apk的dex分析做出一套忽略版本的差异的 并且可以添加新的特征规则

## 一些相关的笔记 如何依赖 Wechat78Spellbook 做插件开发

[1.WechatSpellbook通用hook框架适配新版微信-单元测试适配]()
[2.WechatSpellbook通用hook框架适配新版微信-修复NotificationManagerCompat]()
[3.WechatSpellbook通用hook框架适配新版微信]()
[4.WechatSpellbook通用hook框架适配新版微信]()
[5.WechatSpellbook通用hook框架适配新版微信]()
[6.WechatSpellbook通用hook框架适配新版微信]()
[7.WechatSpellbook通用hook框架适配新版微信]()
[8.WechatSpellbook通用hook框架适配新版微信]()
[9.WechatSpellbook通用hook框架适配新版微信]()
[10.WechatSpellbook通用hook框架适配新版微信]()
[11.WechatSpellbook通用hook框架适配新版微信]()
[12.WechatSpellbook通用hook框架适配新版微信]()