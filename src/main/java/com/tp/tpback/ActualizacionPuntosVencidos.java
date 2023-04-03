package com.tp.tpback;

import dao.BolsaPuntoDAO;
import model.BolsaPunto;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;

@Singleton
@Startup
public class ActualizacionPuntosVencidos {

    @Inject
    BolsaPuntoDAO bolsaPuntoDAO;

    @Schedule(second="*/10", minute="*", hour="*", persistent=false)
    public void ejecutarTarea() {
        // Aquí es donde se debe colocar el código que se quiere ejecutar cada 5 minutos
        System.out.println("Control de vencimientos que se ejecuta cada 10 segundos: " + new java.util.Date());
        List<BolsaPunto> bolsas = bolsaPuntoDAO.listarBolsaPuntoesCaducidos();
        System.out.println("Cantidad de bolsas de puntos vencidas: " + bolsas.size());
        for (BolsaPunto bolsa : bolsas) {
            System.out.println("Se actualiza la bolsa vencida: " + bolsa.getIdBolsa());
            bolsa.setSaldoPuntos(0);
            bolsaPuntoDAO.actualizarBolsaPunto(bolsa);
        }
        System.out.println("Fin del Control de vencimientos que se ejecuta cada 10 segundos: " + new java.util.Date());
    }
}