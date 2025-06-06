package Agente;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.min;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class enviroment {

    // Initialize the agent, world map, and positions
    private int[][] worldMap;

    private int[] currentPosition = new int[2];
    private int[] targetPosition = new int[2];

   //Constructor for initialize the enviroment with a map file and the initial/target positions
    public enviroment(String a,int b,int c,int d,int e){
        try {
            //Reads a .txt file and saves to the cosntractor the map
            this.worldMap = this.readMapFromFile(a);
            System.out.println("Map read successfully");
        } catch (IOException ex) {
            Logger.getLogger(enviroment.class.getName()).log(Level.SEVERE, "Fichero vacio o no lo bastante grande o inexistente", ex);
            System.exit(-1);
        }
       this.currentPosition[0]=b;
       this.currentPosition[1]=c;
       this.targetPosition[0]=d;
       this.targetPosition[1]=e;
       if(this.worldMap[this.currentPosition[0]][this.currentPosition[1]]==-1){
           System.err.println("Posicion del agente invalida, saliendo...");
           System.exit(-1);
       }
    }
    
	// Method that reads the map data from a file and returns a 2D array representing the world map
    public int[][] readMapFromFile(String mapFileName) throws FileNotFoundException {
        File file = new File(mapFileName);
        Scanner scanner = new Scanner(file);

        // Read the number of rows and columns from the first two lines of the file
        int numRows = scanner.nextInt();
        int numColumns = scanner.nextInt();

        int[][] worldMap = new int[numRows][numColumns];

        // Read the map data from the file
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                worldMap[i][j] = scanner.nextInt();
            }
        }


        scanner.close();
        
        return worldMap;
    }
    
    public void show_enviroment() {//Luis
        //shows the map through the shell to the human
        
        int max=21;
        
        try {
            try {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(enviroment.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(enviroment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(max>this.worldMap.length&&max>this.worldMap[0].length){
            for(int i=0;i<this.worldMap.length;i++){
                for(int j=0;j<this.worldMap[0].length;j++){
                    if(i==this.currentPosition[0]&&j==this.currentPosition[1]){
                        System.out.print(" A");
                    }
                    else if(i==this.targetPosition[0]&&j==this.targetPosition[1]){
                        System.out.print(" T");
                    }
                    else if(this.worldMap[i][j]==0){
                        System.out.print(" 0");
                    }
                    else{
                        System.out.print(this.worldMap[i][j]);
                    }            
                    if(j == this.worldMap[0].length - 1){
                        if(i != this.worldMap.length - 1)
                        System.out.print('\n');
                    }
                    else{
                        System.out.print(' ');
                    }
                } 
            }
        }
        else{
            int k;
            int u;
            if(max<this.worldMap.length&&this.currentPosition[0]>(max/2)){
                if(this.currentPosition[0]+max/2>=this.worldMap.length){
                    k=this.worldMap.length-max;
                }
                else{
                    k=this.currentPosition[0]-max/2;
                }
            }
            else{
                k=0;
            }
            if(max<this.worldMap[0].length&&this.currentPosition[1]>(max/2)){
                if(this.currentPosition[1]+max/2>=this.worldMap[0].length){
                    u=this.worldMap[0].length-max;
                }
                else{
                    u=this.currentPosition[1]-max/2;
                }
            }
            else{
                u=0;
            }
            //System.out.println("k = "+k+" u = "+u);
            for(int i=k;i<min(this.worldMap.length,max)+k;i++){
                
                for(int j=u;j<min(this.worldMap[0].length,max)+u;j++){
                    if(i==this.currentPosition[0]&&j==this.currentPosition[1]){
                        System.out.print(" A");
                    }
                    else if(i==this.targetPosition[0]&&j==this.targetPosition[1]){
                        System.out.print(" T");
                    }
                    else if(this.worldMap[i][j]==0){
                        System.out.print(" 0");
                    }
                    else{
                        System.out.print(this.worldMap[i][j]);
                    }            
                    if(j == min(this.worldMap[0].length,max) + u - 1){
                        System.out.print('\n');
                    }
                    else{
                        System.out.print(' ');
                    }
                } 
            }
            
        }
        System.out.print('\n');
    }
    //consultors and stuff
    
	//Movement methods
    public void moveDown(){
        int move_i = this.currentPosition[0]+1;
        int move_j = this.currentPosition[1];
        
        if(move_i<this.worldMap.length)
            if((this.worldMap[move_i][move_j]==0))
                this.currentPosition[0]+=1;
    }
    public void moveUp(){
        int move_i = this.currentPosition[0]-1;
        int move_j = this.currentPosition[1];
        
        if(move_i>=0)
            if((this.worldMap[move_i][move_j]==0))
                this.currentPosition[0]-=1;
    }
    public void moveLeft(){
        int move_i = this.currentPosition[0];
        int move_j = this.currentPosition[1]-1;
        
        if(move_j>=0)
            if((this.worldMap[move_i][move_j]==0))
                this.currentPosition[1]-=1;
    }
    public void moveRight(){
        int move_i = this.currentPosition[0];
        int move_j = this.currentPosition[1]+1;
        
        if(move_j<this.worldMap[0].length)
            if((this.worldMap[move_i][move_j]==0))
                this.currentPosition[1]+=1;
    }
    public void moveLeftUp(){
        int move_i = this.currentPosition[0]-1;
        int move_j = this.currentPosition[1]-1;
        
        if(move_j>=0&&move_i>=0)
            if((this.worldMap[move_i][move_j]==0&&(this.worldMap[move_i+1][move_j]==0||this.worldMap[move_i][move_j+1]==0))){
                this.currentPosition[0]-=1;
                this.currentPosition[1]-=1;
            }
    }
    public void moveLeftDown(){
        int move_i = this.currentPosition[0]+1;
        int move_j = this.currentPosition[1]-1;
        
        if(move_j>=0&&move_i<this.worldMap.length)
            if((this.worldMap[move_i][move_j]==0&&(this.worldMap[move_i-1][move_j]==0||this.worldMap[move_i][move_j+1]==0))){
                this.currentPosition[0]+=1;
                this.currentPosition[1]-=1;
            }
    }
    public void moveRightUp(){
        int move_i = this.currentPosition[0]-1;
        int move_j = this.currentPosition[1]+1;
        
        if(move_j<this.worldMap[0].length&&move_i>=0)
            if((this.worldMap[move_i][move_j]==0)&&(this.worldMap[move_i][move_j-1]==0||this.worldMap[move_i+1][move_j]==0)){
                this.currentPosition[0]-=1;
                this.currentPosition[1]+=1;
            }
    }
    public void moveRightDown(){
        int move_i = this.currentPosition[0]+1;
        int move_j = this.currentPosition[1]+1;
        System.out.println("Hola");
        if(move_j<this.worldMap[0].length&&move_i<this.worldMap.length)
            if((this.worldMap[move_i][move_j]==0)&&(this.worldMap[move_i-1][move_j]==0||this.worldMap[move_i][move_j-1]==0)){
                this.currentPosition[0]+=1;
                this.currentPosition[1]+=1;
            }
    }
    

    public int[] getCurrentPosition() {
        return currentPosition.clone();
    }

    public int[] getTargetPosition() {
        return targetPosition.clone();
    }
    
	
	
    // Method that updates and returns an array representing the agent's vision in the environment
    /*
    0 - Abajo izquierda
    1 - Abajo
    2 - Abajo derecha
    3 - Derecha
    4 - Arriba derecha
    5 - Arriba
    6 - Arriba izquierda
    7 - Izquierda
    */
    public int[] actualizaVision(){
        int[] vision = new int[8];
        int x = this.currentPosition[0];
        int y = this.currentPosition[1];
        for(int i = 0; i < vision.length; ++i)
            vision[i] = -1;
        
        if(y - 1 >= 0){
            if(x - 1 >= 0)  
                vision[0] = worldMap[x - 1][y - 1];
            vision[1] = worldMap[x ][y - 1];
            if(x + 1 < this.worldMap.length) 
                vision[2] = worldMap[x + 1][y - 1];
        }
        if(x + 1 < this.worldMap.length){
            vision[3] = worldMap[x + 1][y];
            if(y + 1 < this.worldMap[0].length)   
                vision[4] = worldMap[x + 1][y + 1]; 
        }
        if(y + 1 < this.worldMap[0].length){
            vision[5] = worldMap[x][y + 1];
            if(x - 1 >= 0)
                vision[6] = worldMap[x - 1][y + 1];
        }
        if(x - 1 >= 0)
            vision[7] = worldMap[x - 1][y];
        
            
        return vision;
    }
}
