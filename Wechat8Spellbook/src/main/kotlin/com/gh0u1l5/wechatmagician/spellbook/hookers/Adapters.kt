package com.gh0u1l5.wechatmagician.spellbook.hookers

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.base.EventCenter
import com.gh0u1l5.wechatmagician.spellbook.base.Hooker
import com.gh0u1l5.wechatmagician.spellbook.interfaces.IAdapterHook
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.contact.Classes.AddressAdapter
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.conversation.Classes.ConversationWithCacheAdapter
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.hookAllConstructors
import de.robv.android.xposed.XposedBridge.log
import de.robv.android.xposed.XposedHelpers.findAndHookMethod

object Adapters : EventCenter() {

    override val interfaces: List<Class<*>>
        get() = listOf(IAdapterHook::class.java)

    override fun provideEventHooker(event: String) = when (event) {
        "onAddressAdapterCreated" ->
            onAddressAdapterCreateHooker
        "onConversationAdapterCreated" ->
            onConversationWithCacheAdapterCreateHooker
        "onHeaderViewListAdapterGettingView", "onHeaderViewListAdapterGotView" ->
            onHeaderViewListAdapterGetViewHooker
        else ->
            throw IllegalArgumentException("Unknown event: $event")
    }

    private val onAddressAdapterCreateHooker = Hooker {
        hookAllConstructors(AddressAdapter, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val adapter = param.thisObject as? BaseAdapter
                if (adapter == null) {
                    log("Expect address adapter to be BaseAdapter, get ${param.thisObject::class.java}")
                    return
                }
                notify("onAddressAdapterCreated") { plugin ->
                    (plugin as IAdapterHook).onAddressAdapterCreated(adapter)
                }
            }
        })
    }

    private val onConversationWithCacheAdapterCreateHooker = Hooker {
        hookAllConstructors(ConversationWithCacheAdapter, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val adapter = param.thisObject as? BaseAdapter
                if (adapter == null) {
                    log("Expect conversation adapter to be BaseAdapter, get ${param.thisObject::class.java}")
                    return
                }
                notify("onConversationAdapterCreated") { plugin ->
                    (plugin as IAdapterHook).onConversationAdapterCreated(adapter)
                }
            }
        })
    }

    private val onHeaderViewListAdapterGetViewHooker = Hooker {
        findAndHookMethod(
                C.HeaderViewListAdapter, "getView",
                C.Int, C.View, C.ViewGroup, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val adapter     = param.thisObject
                val position    = param.args[0] as Int
                val convertView = param.args[1] as View?
                val parent      = param.args[2] as ViewGroup
                notifyForOperations("onHeaderViewListAdapterGettingView", param) { plugin ->
                    (plugin as IAdapterHook).onHeaderViewListAdapterGettingView(adapter, position, convertView, parent)
                }
            }
            override fun afterHookedMethod(param: MethodHookParam) {
                val adapter     = param.thisObject
                val position    = param.args[0] as Int
                val convertView = param.args[1] as View?
                val parent      = param.args[2] as ViewGroup
                val result      = param.result as View?
                notifyForOperations("onHeaderViewListAdapterGotView", param) { plugin ->
                    (plugin as IAdapterHook).onHeaderViewListAdapterGotView(adapter, position, convertView, parent, result)
                }
            }
        })
    }
}