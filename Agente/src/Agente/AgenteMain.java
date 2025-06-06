package Agente;


import jade.core.Agent;
import pruebagrafica.EntornoVirtual;

public class AgenteMain extends Agent {

    
    
//Agent setup for initialize the agent
    @Override
    public void setup() {
        
            EntornoVirtual entorno = new EntornoVirtual("localhost:9000",Long.valueOf(0), "33HH66");
            String[] args = (String[])this.getArguments();
            //enviroment env = new enviroment((String)args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
            //enviroment env = new enviroment("Pr1-maps/mapWithoutObstacle.txt", 2, 2, 5, 5);
            estado state = new estado(entorno,Long.valueOf(0));
            
            addBehaviour(new end_behaviour(state,entorno));
            addBehaviour(new see_behaviour(state,entorno));
            addBehaviour(new think_behaviour(state));
            addBehaviour(new move_behaviour(state,entorno));
            
            //addBehaviour(new MainCycle(this, env));
    }

    
    // Getters and setters
    

    

}
