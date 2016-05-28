int main(){
	int pid;
	int pid2;
	int a;

	if(fork()&&fork()){
		fork();
	}
	
	while(a<10){
		fork();
	}

	a=3;
	printf("pid=%d\n", pid);
	wait(NULL);
}