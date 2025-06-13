package Agente;


import jade.core.behaviours.*;
import java.util.ArrayList;
import java.util.List;
import plataforma_multiagente.EntornoVirtual;

//Represents the behavior of the agent related to movement.
public class move_behaviour extends Behaviour{
    
	// The state of the agent
    private estado state;
	// The environment in which the agent operates
    private EntornoVirtual env;
    private List<Long> habilidades;
    
	// Constructor that initializes the move behavior with the given agent state and environment
    public move_behaviour(estado a, EntornoVirtual env) {
        
        super();
        this.state = a;
        this.env = env;
        this.habilidades = new ArrayList();
        this.habilidades.add(Long.valueOf(894018788));
        this.habilidades.add(Long.valueOf(888913919));
        this.habilidades.add(Long.valueOf(450601107));
        this.habilidades.add(Long.valueOf(140169230));
        this.habilidades.add(Long.valueOf(687048843));
        this.habilidades.add(Long.valueOf(419345001));
        this.habilidades.add(Long.valueOf(119382076));
        this.habilidades.add(Long.valueOf(283020845));
        /*
        0 - Abajo izquierda     894018788
        1 - Abajo               888913919
        2 - Abajo derecha       450601107
        3 - Derecha             140169230
        4 - Arriba derecha      687048843
        5 - Arriba              419345001
        6 - Arriba izquierda    119382076
        7 - Izquierda           283020845
        */
        
        
    }
    
    @Override
    public void action() {
        
        // Accedemos al agente
        int next_move = state.getNextMove();
        
        // Realizamos el movimiento y guardamos la respuesta del servicio
        boolean accion = this.env.UsarHabilidad(
                state.getId_agente(), 
                this.habilidades.get(next_move)
        ).isExito();
        // Actualizamos el estado si tenemos exito
        if(accion){
            state.updatePosition(state.getNextMove());
        }
              
    }
    
    
    @Override
    public boolean done(){
		// Indicate that the behavior is not finished
        return false;
    }
}