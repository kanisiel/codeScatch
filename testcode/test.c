int main(){
	int pid;
	int pid2;
	int a;

	for( a=0;a<3;a++ ){
		fork();
		if(fork()&&fork()){
			fork();
			pid2=0;
		} else if (fork()){
			pid=0;
			a--;
		} else if (a>2){
			pid=0;
			a--;
		}
	}
	
	do{
		fork();
	}while(a<10);

	for( a=0;a<3;a++ ){
		fork();
	}
	
	a=3;
	printf("pid=%d\n", pid);
	wait(NULL);
}