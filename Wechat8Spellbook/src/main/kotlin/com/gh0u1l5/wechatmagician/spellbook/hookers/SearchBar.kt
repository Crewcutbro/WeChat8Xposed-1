package com.gh0u1l5.wechatmagician.spellbook.hookers

import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.gh0u1l5.wechatmagician.spellbook.WechatStatus
import com.gh0u1l5.wechatmagician.spellbook.base.EventCenter
import com.gh0u1l5.wechatmagician.spellbook.base.Hooker
import com.gh0u1l5.wechatmagician.spellbook.interfaces.ISearchBarConsole
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.tools.Classes.ActionBarEditText
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.hookAllConstructors

object SearchBar : EventCenter() {

    override val interfaces: List<Class<*>>
        get() = listOf(ISearchBarConsole::class.java)

    override fun provideEventHooker(event: String): Hooker? {
        return when (event) {
            "onHandleCommand" -> SearchBarHooker
            else -> throw IllegalArgumentException("Unknown event: $event")
        }
    }

    private val SearchBarHooker = Hooker {
        hookAllConstructors(ActionBarEditText, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val search = param.thisObject as EditText
                search.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

                    override fun afterTextChanged(editable: Editable?) {
                        val context = search.context
                        var command = editable.toString()
                        if (command.startsWith("#") && command.endsWith("#")) {
                            command = command.drop(1).dropLast(1)
                            notifyParallel("onHandleCommand") { plugin ->
                                val consumed = (plugin as ISearchBarConsole).onHandleCommand(context, command)
                                if (consumed) {
                                    // Hide Input Method
                                    val imm = search.context.getSystemService(INPUT_METHOD_SERVICE)
                                    (imm as InputMethodManager).hideSoftInputFromWindow(search.windowToken, 0)
                                }
                            }
                        }
                    }
                })
            }
        })

        WechatStatus.toggle(WechatStatus.StatusFlag.STATUS_FLAG_COMMAND)
    }
}
