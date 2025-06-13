package Agente;


import jade.core.behaviours.*;
import java.util.ArrayList;
import java.util.List;
import plataforma_multiagente.EntornoVirtual;
import plataforma_multiagente.datos.SensorResponse;

//Represents the behavior of the agent related to updating its vision.
public class see_behaviour extends Behaviour{

    private estado a;
    private EntornoVirtual env;


    public see_behaviour(estado a, EntornoVirtual env) {
        
        super();
        this.a = a;
        this.env = env;
    }
    
    @Override
    public void action() {
        
        // Actualizamos campo de visión del agente
		// Update the agent's field of vision
        a.setVision(this.actualizaVision());
    }
    @Override
    public boolean done(){
        return false;
    }
    
    // Method that updates and returns an array representing the agent's vision in the environment
    /*
    0 - Abajo izquierda     646770972
    1 - Abajo               321114294
    2 - Abajo derecha       434826718
    3 - Derecha             125519409
    4 - Arriba derecha      815824542
    5 - Arriba              958462822
    6 - Arriba izquierda    654911947
    7 - Izquierda           702258787
    */
    public int[] actualizaVision(){
        int[] vision = new int[8];
        int mov = 10;
        int x = this.a.getCurrentPosition()[0];
        int y = this.a.getCurrentPosition()[1];
        int z = this.a.getCurrentPosition()[2];
        List<Long> sensores = new ArrayList();
        sensores.add(Long.valueOf(646770972));
        sensores.add(Long.valueOf(321114294));
        sensores.add(Long.valueOf(434826718));
        sensores.add(Long.valueOf(125519409));
        sensores.add(Long.valueOf(815824542));
        sensores.add(Long.valueOf(958462822));
        sensores.add(Long.valueOf(654911947));
        sensores.add(Long.valueOf(702258787));
        
        for(int i = 0; i < vision.length; ++i){
            vision[i] = -1;
        }
        SensorResponse sensor = null;
        //Hace uso de los bumpers para percibir sus alreadedores
        for(int i = 0; i < vision.length;i++){
            //Peticion de uso del sensor al entorno
            sensor = env.UsarSensor(a.getId_agente(), sensores.get(i));
            if(!sensor.getCasillas().isEmpty()){
                if((mov >= sensor.getCasillas().getFirst().getZ() - z) &&
                    (mov >= z - sensor.getCasillas().getFirst().getZ())
                ){
                    vision[i] = 0;
                }
            }
        }
          
        return vision;
    }
}
