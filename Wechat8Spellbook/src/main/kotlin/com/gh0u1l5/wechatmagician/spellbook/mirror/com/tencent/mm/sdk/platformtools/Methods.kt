package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.sdk.platformtools

import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.sdk.platformtools.Classes.XmlParser
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findMethodsByExactParameters
import java.lang.reflect.Method

object Methods {
    val XmlParser_parse: Method by wxLazy("XmlParser_parse") {
        findMethodsByExactParameters(XmlParser, C.Map, C.String, C.String).firstOrNull()
    }
}