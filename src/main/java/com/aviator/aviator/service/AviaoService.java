package com.aviator.aviator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class AviaoService {

    private double multiplicador, pontoDeCrash;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    public ScheduledFuture<?> rodada,jogo;

    public void iniciarRodada(WebSocketSession session) {
        multiplicador = 1.0;
        pontoDeCrash = 1+(Math.random()*9);

        rodada = scheduler.scheduleAtFixedRate(() -> {
            multiplicador += 0.01;

            if(multiplicador >=pontoDeCrash) {
                rodada.cancel(true);
            }

            try {
                session.sendMessage(new TextMessage((String.valueOf(multiplicador))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        },0,100,TimeUnit.MILLISECONDS);
    }
}