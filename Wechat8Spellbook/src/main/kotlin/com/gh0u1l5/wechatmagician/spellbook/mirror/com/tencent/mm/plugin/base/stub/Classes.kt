package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.plugin.base.stub

import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLoader
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxPackageName
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassIfExists

object Classes {
    val WXCustomScheme: Class<*> by wxLazy("WXCustomScheme") {
        findClassIfExists("$wxPackageName.plugin.base.stub.WXCustomSchemeEntryActivity", wxLoader!!)
    }
}
