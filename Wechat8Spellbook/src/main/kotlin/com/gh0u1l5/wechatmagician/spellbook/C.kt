@file:Suppress("unused")

package com.gh0u1l5.wechatmagician.spellbook

/**
 * C.(class name) 是 (class name)::class.java 的缩写形式
 *
 * 没有什么特别的意义, 仅仅是为了让 Kotlin 代码看起来比较优雅（事逼）
 */
object C {
    val Boolean = Boolean::class.java
    val File = java.io.File::class.java
    val FileInputStream = java.io.FileInputStream::class.java
    val FileOutputStream = java.io.FileOutputStream::class.java
    val Int = Int::class.java
    val Iterator = java.util.Iterator::class.java
    val Long = Long::class.java
    val Map = Map::class.java
    val Object = Object::class.java
    val String = String::class.java
    val Throwable = Throwable::class.java

    val Activity = android.app.Activity::class.java
    val AdapterView = android.widget.AdapterView::class.java
    val AdapterView_OnItemClickListener = android.widget.AdapterView.OnItemClickListener::class.java
    val AttributeSet = android.util.AttributeSet::class.java
    val BaseAdapter = android.widget.BaseAdapter::class.java
    val Bundle = android.os.Bundle::class.java
    val Button = android.widget.Button::class.java
    val Configuration = android.content.res.Configuration::class.java
    val ContentValues = android.content.ContentValues::class.java
    val Context = android.content.Context::class.java
    val ContextMenu = android.view.ContextMenu::class.java
    val ContextMenuInfo = android.view.ContextMenu.ContextMenuInfo::class.java
    val HeaderViewListAdapter = android.widget.HeaderViewListAdapter::class.java
    val Intent = android.content.Intent::class.java
    val KeyEvent = android.view.KeyEvent::class.java
    val ListAdapter = android.widget.ListAdapter::class.java
    val ListView = android.widget.ListView::class.java
    val Menu = android.view.Menu::class.java
    val Message = android.os.Message::class.java
    val MotionEvent = android.view.MotionEvent::class.java
    val Notification = android.app.Notification::class.java
    val NotificationManager = android.app.NotificationManager::class.java
    val View = android.view.View::class.java
    val ViewGroup = android.view.ViewGroup::class.java

    val ByteArray = ByteArray::class.java
    val IntArray = IntArray::class.java
    val ObjectArray = Array<Any>::class.java
    val StringArray = Array<String>::class.java
}