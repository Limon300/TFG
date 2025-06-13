package Agente;


import jade.core.Agent;
import plataforma_multiagente.EntornoVirtual;

public class AgenteMain extends Agent {
    @Override
    public void setup() {
            EntornoVirtual entorno = new EntornoVirtual("localhost:9000");
            estado state = new estado(entorno,Long.valueOf(0));
            
            addBehaviour(new end_behaviour(state,entorno));
            addBehaviour(new see_behaviour(state,entorno));
            addBehaviour(new think_behaviour(state));
            addBehaviour(new move_behaviour(state,entorno));
            
    }
}
