package com.kafka.otpv.service;

import org.springframework.stereotype.Service;

@Service
public class ParallelService implements Runnable{

    //questo servizio e solo di prova, non serve al momento.
    // gestisce su un thread parallelo a l'avvio determinate cose
    @Override
    public void run() {
        // lanciere poi qui il servizio che si vuole far eseguiere su thread separato
    }


}
