package io.agileninja.androidchatui.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import io.agileninja.androidchatui.R
import io.agileninja.androidchatui.databinding.LayoutChatMessageBinding
import io.agileninja.androidchatui.model.ChatMessage
import kotlin.properties.Delegates

class ChatMessageAdapter : RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder>() {

    var chatMessages by Delegates.observable(mutableListOf<ChatMessageViewModel>()) { _, _, _ ->
        // TODO Use DiffUtil
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_chat_message, parent, false)
        return ChatViewHolder(LayoutChatMessageBinding.bind(view))
    }

    override fun getItemCount(): Int = chatMessages.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.binding.setVariable(BR.viewModel, chatMessages[position])
    }

    class ChatViewHolder(val binding: LayoutChatMessageBinding) : RecyclerView.ViewHolder(binding.root)
}
