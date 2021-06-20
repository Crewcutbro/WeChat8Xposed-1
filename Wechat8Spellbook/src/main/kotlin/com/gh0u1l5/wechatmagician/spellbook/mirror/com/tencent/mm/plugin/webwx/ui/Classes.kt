package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.plugin.webwx.ui

import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLoader
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxPackageName
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassIfExists

object Classes {
    val ExtDeviceWXLoginUI: Class<*> by wxLazy("ExtDeviceWXLoginUI") {
        findClassIfExists("$wxPackageName.plugin.webwx.ui.ExtDeviceWXLoginUI", wxLoader!!)
    }
}