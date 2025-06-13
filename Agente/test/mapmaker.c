#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char* args[]){
	if(argc < 4){
		exit(-1);
	}
	int x=atoi(args[1]);
	int y=atoi(args[2]);
	int k;
	char buffer[x*y*4];
	int fs = open(args[3],O_WRONLY|O_CREAT|O_TRUNC,S_IRUSR|S_IWUSR);
	printf("Vamos a hacer un mapa %i x %i en el fichero %s\n",x,y,args[3]);
	k = snprintf(buffer,sizeof(buffer),"%i\n%i\n",x,y);
	write(fs,buffer,k);
	
	k=snprintf(buffer,sizeof(buffer),"");
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			if((((j+6)%8==0)&&i!=(2))||(((j+2)%8==0)&&i!=(x-3))||(i==(x/2)&&(j%4!=0))){//escribir -1
				buffer[k]='-';
				buffer[k+1]='1';
				if(j==y-1)
					buffer[k+2]='\n';
				else
					buffer[k+2]='\t';
				k+=3;
			}
			else{//escribir 0
				buffer[k]='0';
				if(j==y-1)
					buffer[k+1]='\n';
				else
					buffer[k+1]='\t';
				k+=2;
			}
		}
	}
	buffer[k]='\0';
	printf("string:\n%sint: %i\n",buffer,k);
	write(fs,buffer,k);
	close(fs);
	return 0;
}
