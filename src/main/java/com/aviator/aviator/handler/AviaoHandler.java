package com.aviator.aviator.handler;

import com.aviator.aviator.Service.AviaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class AviaoHandler extends TextWebSocketHandler {

    @Autowired
    private AviaoService aviaoService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        aviaoService.iniciarRodada(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // sem lógica por enquanto — só um jogador testando
    }
}