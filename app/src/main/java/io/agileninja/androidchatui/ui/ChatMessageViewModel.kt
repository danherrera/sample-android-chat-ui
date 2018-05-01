package io.agileninja.androidchatui.ui

import android.arch.lifecycle.ViewModel
import io.agileninja.androidchatui.R
import io.agileninja.androidchatui.model.ChatMessage

data class ChatMessageViewModel(private val chatMessage: ChatMessage) : ViewModel(), LayoutViewModel {
    override val layout: Int = R.layout.layout_chat_message
    val initial = chatMessage.name[0].toString()
    val name = chatMessage.name
    val message = chatMessage.message
}
