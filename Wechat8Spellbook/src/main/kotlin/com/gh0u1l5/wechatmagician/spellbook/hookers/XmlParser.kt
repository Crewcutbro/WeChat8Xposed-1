package com.gh0u1l5.wechatmagician.spellbook.hookers

import com.gh0u1l5.wechatmagician.spellbook.WechatStatus
import com.gh0u1l5.wechatmagician.spellbook.base.EventCenter
import com.gh0u1l5.wechatmagician.spellbook.base.Hooker
import com.gh0u1l5.wechatmagician.spellbook.interfaces.IXmlParserHook
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.sdk.platformtools.Methods.XmlParser_parse
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.hookMethod

object XmlParser : EventCenter() {

    override val interfaces: List<Class<*>>
        get() = listOf(IXmlParserHook::class.java)

    override fun provideEventHooker(event: String): Hooker? {
        return when (event) {
            "onXmlParsing", "onXmlParsed" -> onXmlParseHooker
            else -> throw IllegalArgumentException("Unknown event: $event")
        }
    }

    private val onXmlParseHooker = Hooker {
        hookMethod(XmlParser_parse, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val xml  = param.args[0] as String
                val root = param.args[1] as String
                notifyForOperations("onXmlParsing", param) { plugin ->
                    (plugin as IXmlParserHook).onXmlParsing(xml, root)
                }
            }
            @Suppress("UNCHECKED_CAST")
            override fun afterHookedMethod(param: MethodHookParam) {
                val xml    = param.args[0] as String
                val root   = param.args[1] as String
                val result = param.result as MutableMap<String, String>? ?: return
                notify("onXmlParsed") { plugin ->
                    (plugin as IXmlParserHook).onXmlParsed(xml, root, result)
                }
            }
        })

        WechatStatus.toggle(WechatStatus.StatusFlag.STATUS_FLAG_XML_PARSER)
    }
}