package com.gh0u1l5.wechatmagician.spellbook.hookers

import android.widget.BaseAdapter
import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.Predicate
import com.gh0u1l5.wechatmagician.spellbook.WechatStatus
import com.gh0u1l5.wechatmagician.spellbook.base.Hooker
import com.gh0u1l5.wechatmagician.spellbook.base.HookerProvider
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.Classes.MMBaseAdapter
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.Methods.MMBaseAdapter_getItemInternal
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.contact.Classes.AddressAdapter
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui.conversation.Classes.ConversationWithCacheAdapter
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import java.util.concurrent.ConcurrentHashMap

object ListViewHider : HookerProvider {

    data class Section(
            val start: Int,  // Inclusive
            val end: Int,    // Exclusive
            val base: Int
    ) {
        operator fun contains(index: Int) = (start <= index) && (index < end)

        fun size() = end - start

        fun split(index: Int): List<Section> {
            val length = index - base
            return listOf(
                    Section(start, start + length, base),
                    Section(start + length, end - 1, base + length + 1)
            ).filter { it.size() != 0 }
        }
    }

    data class Record(
            // The variable sections records the sections of items we want to show
            @Volatile var sections: List<Section>,
            // The variable predicates records the predicates of the adapters.
            // An item will be hidden if it satisfies any one of the predicates.
            @Volatile var predicates: Map<String, Predicate>
    )

    private val records: MutableMap<BaseAdapter, Record> = ConcurrentHashMap()

    fun register(adapter: BaseAdapter, predicateName: String, predicateBody: Predicate) {
        val record = records[adapter]
        if (record == null) {
            records[adapter] = Record(emptyList(), mapOf(predicateName to predicateBody))
            return
        }
        synchronized(record) {
            record.predicates += (predicateName to predicateBody)
        }
    }

    private fun updateAdapterSections(param: XC_MethodHook.MethodHookParam) {
        val adapter = param.thisObject as BaseAdapter
        val record = records[adapter] ?: return
        synchronized(record) {
            record.sections = emptyList()
            val initial = listOf(Section(0, adapter.count, 0))
            val predicates = record.predicates.values
            record.sections = (0 until adapter.count).filter { index ->
                // Hide the items satisfying any one of the predicates
                val item = adapter.getItem(index)
                predicates.forEach { predicate ->
                    if (predicate(item)) {
                        return@filter true
                    }
                }
                return@filter false
            }.fold(initial) { sections, index ->
                sections.dropLast(1) + sections.last().split(index)
            }
        }
    }

    override fun provideStaticHookers(): List<Hooker>? {
        return listOf(MMBaseAdapterHooker)
    }

    private val MMBaseAdapterHooker = Hooker {
        // Hook getItem() of base adapters
        findAndHookMethod(MMBaseAdapter, MMBaseAdapter_getItemInternal, C.Int, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val adapter = param.thisObject as BaseAdapter
                val index = param.args[0] as Int
                val record = records[adapter] ?: return
                synchronized(record) {
                    record.sections.forEach { section ->
                        if (index in section) {
                            param.args[0] = section.base + (index - section.start)
                            return
                        }
                    }
                }
            }
        })

        // Hook getCount() of base adapters
        findAndHookMethod(MMBaseAdapter, "getCount", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val adapter = param.thisObject as BaseAdapter
                val record = records[adapter] ?: return
                synchronized(record) {
                    if (record.sections.isNotEmpty()) {
                        param.result = record.sections.sumBy { it.size() }
                    }
                }
            }
        })

        // Hook notifyDataSetChanged() of base adapters
        findAndHookMethod(C.BaseAdapter, "notifyDataSetChanged", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                when (param.thisObject::class.java) {
                    AddressAdapter -> {
                        updateAdapterSections(param)
                    }
                    ConversationWithCacheAdapter -> {
                        updateAdapterSections(param)
                    }
                }
            }
        })

        WechatStatus.toggle(WechatStatus.StatusFlag.STATUS_FLAG_BASE_ADAPTER)
    }
}
