int main(){
	int pid;
	int pid2;
	int a;

	for( a=0;a<3;a++ ){
		pid = fork();
		for( b=0;b<3;b++ ){
			pid2 = fork();
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