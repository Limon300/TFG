package Agente;


import jade.core.behaviours.*;


//Represents the behavior of the agent related to decision-making and moving.
public class think_behaviour extends Behaviour {
    private estado state;
    int[] current;

    public think_behaviour(estado a) {
        super();
        this.state = a;
        this.current = new int[2];
    }

    @Override
    public void action() {
	// Update the current position of the agent
        this.current[0] = state.getCurrentPosition()[0];
        this.current[1] = state.getCurrentPosition()[1];
        
		// Initialize nextMove
        int nextMove = -1;
         
		// Continue making decisions until a valid nextMove is determined
        while(nextMove == -1){
			// Switch between different algorithms based on the agent's state
            switch(state.getAlg()){
                case 0:
                    nextMove = this.Alg_1(nextMove);
                    break;
                case 1:
                    nextMove = this.Alg_2();
                    break;
                case 2:
                    nextMove = this.Alg_3(this.state.getDirection()); // Counterclockwise_Version
                    break;
            }
        }
        
		// Set the determined nextMove and update the agent's position
        state.setNextMove(nextMove);
        //state.updatePosition(nextMove);
        
    }
    
	// Algorithm 1: Move to the neighboring square with the lowest Euclidean distance to the target
    private int Alg_1(int nextMove){
        double minEuclideanDistance = Double.MAX_VALUE;
        // Check adjacent squares (up, down, left, right, and diagonals)
        int[] moves = {-1, 0, 1};
        for (int moveX : moves) {
            for (int moveY : moves) {
                if (moveX == 0 && moveY == 0) {
                    continue;
                }

                int neighborX = this.current[0] + moveX;
                int neighborY = this.current[1] + moveY;

                // Calculate the Euclidean distance to the target for the neighbor square
                double euclideanDistance = this.state.actualizarDistanciaObjetivo(neighborX, neighborY);

                // Choose the square with the lowest Euclidean distance
                if (euclideanDistance < minEuclideanDistance) {
                    minEuclideanDistance = euclideanDistance;
                    nextMove = this.calculateMove(moveX, moveY);
                }
            }
        }
		// Check if the chosen nextMove is blocked by an obstacle
        if(((state.getVision()[nextMove]!=-1)&&(nextMove%2==1))||((state.getVision()[nextMove]!=-1)&&((state.getVision()[((((nextMove+1) % 8) + 8) % 8)]!=-1)||(state.getVision()[((((nextMove-1) % 8) + 8) % 8)]!=-1)))){
            return nextMove;
        }
        else{
		// Set obstacle-related information and switch to Algorithm 2
            state.setDistanciaObstaculo(minEuclideanDistance);
            state.setMano(nextMove);
            state.setAlg(1);
            state.setInicio(true);
            state.setManoInicial(nextMove);
            state.setDistanciaAlg2(this.state.actualizarDistanciaObjetivo(this.current[0], this.current[1]));
            return -1;
        }
    }
    
    // Agent attempts to pass the obstacle with a shorter version of Alg_3. It
    // tries to go around the obstacle int he oppsite direction of "Alg_3" for a
    // few movements. In Alg_3, it goes around the obstacle until the end
    
    
    // Aqui pone que empieza en clockwise, si gasta sus intentos de clockwise se mete en no clocwise
    // Y hace intentos en el sentido opuesto y si esto tampoco funciona cambia a 3
    // Pero parece ser que siempre ejecuta la version clockwise
    // Ademas hemos ejecutado:
    // java -cp dist/Agente_2D.jar jade.Boot -container -agents 'mjcobo:dba_pr2.Agent_2d(test/mapWithVerticalWall.txt,0,0,5,5)'
    // O sea, el mapWithVerticalWall con el agente en 0,0 y el objetivo en 5,5 y hemos encontrado que se atasca en 3,9
    // El algoritmo 3 esta puesto de tal forma que no calcule el punto muerto si lo llama el algoritmo 2
    // Esto lo hace comprobando en que algoritmo se encuentra actualmente
    // Pero parece ser que el algoritmo 2 hace state.setAlg(2) y luego sigue ejecutandose y llamando a 3 que cree que se encuentra en 2
    // Esto provoca que en ciertas circunstancias el algoritmo piensa que esta en un punto muerto
    // Arreglalo
    // Queriamos añadir algunos criterios para que esto funcione mejor
    // Al ver el error, los hemos quitado pero hemos dejado los datos y funcionesde state
	
	
// Here it states that it starts in clockwise. If it exhausts its clockwise attempts, it switches to counterclockwise.
// It makes attempts in the opposite direction, and if this also doesn't work, it switches to Algorithm 3.
// However, it seems that it always executes the clockwise version.
// Additionally, we have executed:
// java -cp dist/Agente_2D.jar jade.Boot -container -agents 'mjcobo:dba_pr2.Agent_2d(test/mapWithVerticalWall.txt,0,0,5,5)'
// That is, the mapWithVerticalWall with the agent at 0,0 and the target at 5,5, and we found that it gets stuck at 3,9.
// Algorithm 3 is set up in such a way that it does not calculate the stalemate if called by Algorithm 2.
// It does this by checking which algorithm it is currently in.
// However, it seems that Algorithm 2 does state.setAlg(2) and then continues to execute and call 3, which thinks it is in 2.
// This causes the algorithm to think it is in a stalemate under certain circumstances.
// Fix it.
// We wanted to add some criteria to make this work better.
// Upon seeing the error, we removed them but left the data and functions of the state.
    
    // Algorithm 2: Attempt to pass the obstacle
	private int Alg_2(){
        
        // Si todavía quedan intentos
		 // If there are remaining attempts, execute Algorithm 3
        if (state.getAttemptsAlg2() > 0) {
            state.setAttemptsAlg2(state.getAttemptsAlg2() - 1); // Consume 1 attempt
            return Alg_3(-1);
        }
        // Si no quedan intentos --> comenzamos ejecución de Alg_3(1) sentido contrario
		// If there are no attempts left, initiate Algorithm 3 in the opposite direction
        else {
            this.state.setDirection(this.decisionAlg2());
            state.setAttemptsAlg2(5);   // Recuperamos los 5 intentos para siguiente ocasión // Reset attempts for the next time
            state.setAlg(2);            // Es turno del Alg_3 //Switch to algo3
            return -1;
        }        
    }


     // Algorithm 3: Move around the obstacle (clockwise or counterclockwise)
    private int Alg_3(int i){//-1 derecha, 1 izquierda // -1 for clockwise, 1 for counterclockwise
        int ret;
        //Ha dado la vuelta
		// If the agent has completed a full turn
        if((state.getAttemptsAlg2() == 5) && (state.checkStalemate())){
            ret = 8; // Indicates a stalemate
        }
        //No ha dado la vuelta aun
		// If the agent hasn't completed a full turn yet
        else{
            
            //El agente esta en diagonal con respecto al obstaculo
			// If the agent is on a diagonal with respect to the obstacle
            if(state.getMano(0)%2==0){//Diagonal
                if(state.getVision()[state.getMano(1*i)] == -1){
                    state.setMano(state.getMano(1*i));
                    state.setManoInicial(state.getMano(0));
                    ret = -1;
                }
                else if(state.getVision()[state.getMano(-1*i)] == -1){
                    state.setMano(state.getMano(-1*i));
                    state.setManoInicial(state.getMano(0));
                    ret = -1;
                }
                else{
                    state.setMano(state.getMano(-1*i));
                    state.setManoInicial(state.getMano(0));
                    ret = state.getMano(2*i);
                }
            }
            //El agente NO esta en diagonal con respecto al obstaculo
			//If the agent is NOT on a diagonal with respect to the obstacle
            else{//NO Diagonal
                if(state.getDistanciaObstaculo() > state.getDistanciaObjetivo()){//Ha superado el obstaculo // The obstacle has been passed
                    state.setAlg(0);
                    // Por si la ejecución de Alg_3 proviene de una llamada de Alg_2
                    // En ese caso, habría que recuperar los intentos para una próxima
                    // ejecución de Alg_2, ya que el obstáculo realmente se ha superado
                    // en el turno de Alg_2
					
					// In case Alg_3 is called from Alg_2
                    // In that case, the attempts for Alg_2 should be recovered for the next
                    // Alg_2 execution, as the obstacle has really been passed in Alg_2's turn
                    state.setAttemptsAlg2(5);   
                    ret = -1;
                }
                else if((state.getVision()[state.getMano(1*i)] != -1)&&((state.getVision()[state.getMano(0)] != -1)||(state.getVision()[state.getMano(2*i)] != -1))){//Mov_dig
                    ret = state.getMano(1*i);
                    state.setMano(state.getMano(-2*i));
                }
                else if(state.getVision()[state.getMano(2*i)] != -1){//Mov_hor
                    ret = state.getMano(2*i);
                }
                else{//Turn
                    state.setMano(state.getMano(2*i));
                    ret = -1;
                }
                if(this.state.getAlg()==2){
                    //Inicio y vuelta
                    if(state.getInicio()){
                        state.setInicio(false);
                    }
                    if(ret!=-1){
                        state.updateVuelta(ret);
                    }
                }
                else{
                    state.setManoInicial(this.state.getMano(0));
                }
            } 
        }
        return ret;
    }

    // Helper method to calculate the next move based on moveX and moveY
    private int calculateMove(int moveX, int moveY) {
        switch (moveX) {
            case -1:
                if (moveY == -1) return 0; // Move left-up
                if (moveY == 0) return 7; // Move up
                return 6; // Move right-up
            case 0:
                if (moveY == -1) return 1; // Move left
                return 5; // Move right
            default:
                if (moveY == -1) return 2; // Move left-down
                if (moveY == 0) return 3; // Move down
                return 4; // Move right-down
        }
    }
    
	 // Method to make a decision for Alg_2 based on the distances of adjacent squares
    public int decisionAlg2(){
		// Initialize variables for the left and right movement decisions
        int mov_izq=-1;//1
        int pos_izq[] = {this.current[0],this.current[1]};
        int pos_der[] = {this.current[0],this.current[1]};
        int mov_der=-1;//-1
        
		// Loop through the vision array to find the first obstacle in the left direction
        for(int i=0;i<this.state.getVision().length&&mov_der==-1;i++){
            if(this.state.getVision()[this.state.getMano(-1*i)]!=-1){
                mov_der=this.state.getMano(-1*i);
            }
        }
		// Loop through the vision array to find the first obstacle in the right direction
        for(int i=0;i<this.state.getVision().length&&mov_izq==-1;i++){
            if(this.state.getVision()[this.state.getMano(i)]!=-1){
                mov_izq=this.state.getMano(i);
            }
        }
		// If there is an obstacle in the left direction
        if(mov_izq!=-1){
			// Update the position based on the left movement decision
            switch(mov_izq){
                case 0:
                    pos_izq[0]--;
                    pos_izq[1]--;
                    //this.env.moveLeftUp();        
                    break;

                case 1:
                    pos_izq[1]--;
                    //this.env.moveLeft();
                    break;

                case 2:
                    pos_izq[0]++;
                    pos_izq[1]--;
                    //this.env.moveLeftDown();
                    break;

                case 3:
                    pos_izq[0]++;
                    //this.env.moveDown();
                    break;

                case 4:
                    pos_izq[0]++;
                    pos_izq[1]++;
                    //this.env.moveRightDown();
                    break;

                case 5:
                    pos_izq[1]++;
                    //this.env.moveRight();
                    break;

                case 6:
                    pos_izq[0]--;
                    pos_izq[1]++;
                    //this.env.moveRightUp();
                    break;

                case 7:
                    pos_izq[0]--;
                    //this.env.moveUp();
                    break;
            }
			// Update the position based on the right movement decision
            switch(mov_der){
                case 0:
                    pos_der[0]--;
                    pos_der[1]--;
                    //this.env.moveLeftUp();        
                    break;

                case 1:
                    pos_der[1]--;
                    //this.env.moveLeft();
                    break;

                case 2:
                    pos_der[0]++;
                    pos_der[1]--;
                    //this.env.moveLeftDown();
                    break;

                case 3:
                    pos_der[0]++;
                    //this.env.moveDown();
                    break;

                case 4:
                    pos_der[0]++;
                    pos_der[1]++;
                    //this.env.moveRightDown();
                    break;

                case 5:
                    pos_der[1]++;
                    //this.env.moveRight();
                    break;

                case 6:
                    pos_der[0]--;
                    pos_der[1]++;
                    //this.env.moveRightUp();
                    break;

                case 7:
                    pos_der[0]--;
                    //this.env.moveUp();
                    break;
            }
            
	    // Compare the distances to the target for left and right positions
        // Return 1 if the left position has a greater distance, -1 otherwise
            if(this.state.actualizarDistanciaObjetivo(pos_der[0], pos_der[1])>this.state.actualizarDistanciaObjetivo(pos_izq[0], pos_izq[1])){
                return 1; //Move to the left
            }
            else{
                return -1;  //Move to the right
            }
        }
        else{
            return 1; // Move to the left if there is no obstacle in the left direction
        }
    }
    

    @Override
    public boolean done() {
        return false;
    }
}
