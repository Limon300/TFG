package Agente;


import jade.core.behaviours.*;
import static java.lang.Thread.sleep;

import java.util.logging.Level;
import java.util.logging.Logger;
import plataforma_multiagente.EntornoVirtual;

//Represents the behavior of the agent related to displaying the environment and making decisions.
public class end_behaviour extends Behaviour{

    private estado a;
    private EntornoVirtual env;


    public end_behaviour(estado a, EntornoVirtual env) {
        
        super();
        this.a = a;
        this.env = env;
    }
    
    @Override
    public void action() {
        
        // Se toma decision para actualizar a.nextMove
		// Make a decision to update a.nextMove
        
        //env.show_enviroment();  // Así siempre se movería a la derecha
		// Display the environment
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(end_behaviour.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @Override
    public boolean done(){
        // Check if the agent has reached its goal or a stalemate
        Long sensor = env.UsarSensor(a.getId_agente(), Long.valueOf(694397972)).getValor();
        if(a.end(sensor)){
            // If so, delete the agent and indicate that the behavior is finished
            this.myAgent.doDelete();
            return true;
        }
        else{
            //else the behavior is not finished
            return false;
        }
    }
       
}
