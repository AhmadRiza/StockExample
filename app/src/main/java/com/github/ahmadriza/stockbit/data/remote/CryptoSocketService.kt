package com.github.ahmadriza.stockbit.data.remote

import com.github.ahmadriza.stockbit.models.SocketCryptoMessage
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

interface CryptoSocketService {

    @Send
    fun send(message: Any): Boolean

    @Receive
    fun observeEvents(): ReceiveChannel<WebSocket.Event>

    @Receive
    fun observeApiMessage(): ReceiveChannel<SocketCryptoMessage>

}