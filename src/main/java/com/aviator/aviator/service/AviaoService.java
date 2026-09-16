package com.aviator.aviator.Service;

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
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    public ScheduledFuture<?> rodada;

    public void iniciarRodada(WebSocketSession session) {
        multiplicador = 1.0;
        pontoDeCrash = 1+(Math.random()*9);

        rodada = scheduler.scheduleAtFixedRate(() -> {
            multiplicador += 0.1;

            if (multiplicador >= pontoDeCrash) {
                rodada.cancel(false);
            }
            try {
                session.sendMessage(new TextMessage(String.valueOf(multiplicador)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }
}