package uy.edu.ort.obligatorio.peajes.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private List<Observador> subscriptos;

    public Observable(){
        subscriptos = new ArrayList<Observador>();
    }

    public void subscribir(Observador observador){
        subscriptos.add(observador);
    }

    public void desubscribir(Observador observador){
        subscriptos.remove(observador);
    }

    public void notificar(Object evento){
        for (Observador o : subscriptos){
            o.actualizar(this, evento);
        }
    }
    
}
