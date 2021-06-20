package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.contact

import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxClasses
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLoader
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxPackageName
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassIfExists
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassesFromPackage

object Classes {
    val AddressUI: Class<*> by wxLazy("AddressUI") {
        findClassIfExists("$wxPackageName.ui.contact.AddressUI\$a", wxLoader!!)
    }

    val AddressAdapter: Class<*> by wxLazy("AddressAdapter") {
        findClassesFromPackage(wxLoader!!, wxClasses!!, "$wxPackageName.ui.contact")
                .filterByMethod(null, "pause")
                .firstOrNull()
    }

    val ContactLongClickListener: Class<*> by wxLazy("ContactLongClickListener") {
        findClassesFromPackage(wxLoader!!, wxClasses!!, "$wxPackageName.ui.contact")
                .filterByEnclosingClass(AddressUI)
                .filterByMethod(C.Boolean, "onItemLongClick", C.AdapterView, C.View, C.Int, C.Long)
                .firstOrNull()
    }

    val SelectContactUI: Class<*> by wxLazy("SelectContactUI") {
        findClassIfExists("$wxPackageName.ui.contact.SelectContactUI", wxLoader!!)
    }
}