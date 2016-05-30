int main(){
	int pid;
	int pid2;
	int a;

	for( a=0;a<3;a++ ){
		fork();
	}
	if(fork()&&fork()){
		fork();
	} else {
		a++;
	}
	
	while(a<10){
		fork();
	}

	for( a=0;a<3;a++ ){
		fork();
	}

	a=3;
	printf("pid=%d\n", pid);
	wait(NULL);
}