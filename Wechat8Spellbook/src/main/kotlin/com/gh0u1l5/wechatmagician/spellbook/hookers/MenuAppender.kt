package com.gh0u1l5.wechatmagician.spellbook.hookers

import android.app.Activity
import android.content.Context
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.WechatStatus
import com.gh0u1l5.wechatmagician.spellbook.base.EventCenter
import com.gh0u1l5.wechatmagician.spellbook.base.Hooker
import com.gh0u1l5.wechatmagician.spellbook.interfaces.IPopupMenuHook
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.base.Classes.MMListPopupWindow
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.contact.Classes.AddressUI
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.contact.Classes.ContactLongClickListener
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.conversation.Classes.ConversationCreateContextMenuListener
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.conversation.Classes.ConversationLongClickListener
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers.*

object MenuAppender : EventCenter() {

    override val interfaces: List<Class<*>>
        get() = listOf(IPopupMenuHook::class.java)

    data class PopupMenuItem (
            val groupId: Int,
            val itemId: Int,
            val order: Int,
            val title: String,
            val onClickListener: (context: Context) -> Unit
    )

    @Volatile var currentUsername: String? = null
    @Volatile var currentMenuItems: List<PopupMenuItem>? = null

    override fun provideStaticHookers(): List<Hooker>? {
        return listOf(onMMListPopupWindowShowHooker, onMMListPopupWindowDismissHooker)
    }

    override fun provideEventHooker(event: String): Hooker? {
        return when (event) {
            "onPopupMenuForContactsCreating" -> onPopupMenuForContactsCreateHooker
            "onPopupMenuForConversationsCreating" -> onPopupMenuForConversationsCreateHooker
            else -> throw IllegalArgumentException("Unknown event: $event")
        }
    }

    private val onMMListPopupWindowShowHooker = Hooker {
        findAndHookMethod(MMListPopupWindow, "show", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val listenerField = findFirstFieldByExactType(MMListPopupWindow, C.AdapterView_OnItemClickListener)
                val listener = listenerField.get(param.thisObject) as AdapterView.OnItemClickListener
                listenerField.set(param.thisObject, AdapterView.OnItemClickListener { parent, view, position, id ->
                    val title = parent.adapter.getItem(position)
                    val context = getObjectField(param.thisObject, "mContext") as Context
                    currentMenuItems?.forEach {
                        if (title == it.title) {
                            it.onClickListener(context)
                        }
                    }
                    listener.onItemClick(parent, view, position, id)
                })
            }
        })
    }

    private val onMMListPopupWindowDismissHooker = Hooker {
        findAndHookMethod(MMListPopupWindow, "dismiss", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                currentUsername = null
                currentMenuItems = null
            }
        })
    }

    private val onPopupMenuForContactsCreateHooker = Hooker {
        findAndHookMethod(
                ContactLongClickListener, "onItemLongClick",
                C.AdapterView, C.View, C.Int, C.Long, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val parent = param.args[0] as AdapterView<*>
                val position = param.args[2] as Int
                val item = parent.adapter?.getItem(position)
                currentUsername = getObjectField(item, "field_username") as String?
            }
        })

        findAndHookMethod(
                AddressUI, "onCreateContextMenu",
                C.ContextMenu, C.View, C.ContextMenuInfo, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val menu = param.args[0] as ContextMenu
                val view = param.args[1] as View

                currentMenuItems = notifyForResults("onPopupMenuForContactsCreating") { plugin ->
                    (plugin as IPopupMenuHook).onPopupMenuForContactsCreating(currentUsername ?: "")
                }.flatten().sortedBy { it.itemId }

                currentMenuItems?.forEach {
                    val item = menu.add(it.groupId, it.itemId, it.order, it.title)
                    item.setOnMenuItemClickListener { _ ->
                        it.onClickListener(view.context)
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        })

        WechatStatus.toggle(WechatStatus.StatusFlag.STATUS_FLAG_CONTACT_POPUP)
    }

    private val onPopupMenuForConversationsCreateHooker = Hooker {
        findAndHookMethod(
                ConversationLongClickListener, "onItemLongClick",
                C.AdapterView, C.View, C.Int, C.Long, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                val parent = param.args[0] as AdapterView<*>
                val position = param.args[2] as Int
                val item = parent.adapter?.getItem(position)
                currentUsername = getObjectField(item, "field_username") as String?
            }
        })

        findAndHookMethod(
                ConversationCreateContextMenuListener, "onCreateContextMenu",
                C.ContextMenu, C.View, C.ContextMenuInfo, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                val menu = param.args[0] as ContextMenu

                currentMenuItems = notifyForResults("onPopupMenuForConversationsCreating") { plugin ->
                    (plugin as IPopupMenuHook).onPopupMenuForConversationsCreating(currentUsername ?: "")
                }.flatten().sortedBy { it.itemId }

                currentMenuItems?.forEach {
                    val item = menu.add(it.groupId, it.itemId, it.order, it.title)
                    item.setOnMenuItemClickListener { _ ->
                        it.onClickListener(param.thisObject as Activity)
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        })

        WechatStatus.toggle(WechatStatus.StatusFlag.STATUS_FLAG_CONVERSATION_POPUP)
    }
}