int main(){
	int pid;
	int pid2;
	int a;

	for( a=0;a<3;a++ ){
		fork();
	}
	
	do{
		fork();
	}while(a<10);
	
	if(fork()&&fork()){
		fork();
		pid2=0;
	} 

	for( a=0;a<3;a++ ){
		fork();
	}
	
	a=3;
	printf("pid=%d\n", pid);
	wait(NULL);
}