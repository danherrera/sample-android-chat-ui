package io.agileninja.androidchatui.ui

import io.agileninja.androidchatui.R
import io.agileninja.androidchatui.model.ChatMessage
import io.agileninja.mvvm.core.BaseViewModel

data class ChatMessageViewModel(private val chatMessage: ChatMessage) : BaseViewModel() {
    override val layout: Int = R.layout.layout_chat_message
    val initial = chatMessage.name[0].toString()
    val name = chatMessage.name
    val message = chatMessage.message
}
