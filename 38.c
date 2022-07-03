#include <stdio.h>

int i=0;
int array[10]={0,};
int row=1;
int isFirst=0;

void steps(unsigned int n);

void main(){
	unsigned int stair;
	printf("Enter step number:");
	scanf("%d",&stair);
	
	steps(stair);
	
}

void steps(unsigned int n) {
	int a;
	
	if(n>=1){		
		array[i++]=1;
		steps(n-1);
		array[i--]=0;
	}
		
	if(n>=2){
			
		array[i++]=2;
		steps(n-2);
		array[i--]=0;
	}
		
	if(n>=3){	
		array[i++]=3;
		steps(n-3);
		array[i--]=0;
	}
	
	
	if(n==0){				//남은 계단 수가 0 이라면 출력
		
		if(array[0]!=row){		//array[0]과 행의 숫자가 같지않다면, "\n"출력 
			printf("\n");
			isFirst=0;
			row++;
		}
		
		if(isFirst!=0){		   //그 행에서 처음실행된것이 아니라면, ","출력
			printf(", ");
		}
		
		
		for(a=0; array[a]!=0; a++){
			
			if(a==0 && array[a+1]==0){		//첫 출력과 동시에 끝나는 경우 ex) 3계단 3걸음만에 끝 
				printf("(%d)",array[a]);	//출력하고 종료 
				break;
			} 
			
			
			if(array[a+1]==0 ){						//이 다음의 배열값이 0이면 
				printf(", %d)",array[a]);			//마지막 출력이므로 괄호닫음 
			}
			else if(a==0){							//첫번째 출력이라면, 괄호열음 
				printf("(%d",array[a]);
			}
			else if(array[a]!=0){					//중간 출력과정 중이라면, 그냥 출력 
				printf(", %d",array[a]);
			}
		}
		isFirst=1;
	}
}
//test_1
