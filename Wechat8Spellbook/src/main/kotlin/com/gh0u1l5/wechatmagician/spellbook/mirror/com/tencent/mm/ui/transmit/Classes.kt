package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.transmit

import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLoader
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxPackageName
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassIfExists

object Classes {
    val SelectConversationUI: Class<*> by wxLazy("SelectConversationUI") {
        findClassIfExists("$wxPackageName.ui.transmit.SelectConversationUI", wxLoader!!)
    }
}