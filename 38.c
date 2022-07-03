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
	
	
	if(n==0){				//���� ��� ���� 0 �̶�� ���
		
		if(array[0]!=row){		//array[0]�� ���� ���ڰ� �����ʴٸ�, "\n"��� 
			printf("\n");
			isFirst=0;
			row++;
		}
		
		if(isFirst!=0){		   //�� �࿡�� ó������Ȱ��� �ƴ϶��, ","���
			printf(", ");
		}
		
		
		for(a=0; array[a]!=0; a++){
			
			if(a==0 && array[a+1]==0){		//ù ��°� ���ÿ� ������ ��� ex) 3��� 3�������� �� 
				printf("(%d)",array[a]);	//����ϰ� ���� 
				break;
			} 
			
			
			if(array[a+1]==0 ){						//�� ������ �迭���� 0�̸� 
				printf(", %d)",array[a]);			//������ ����̹Ƿ� ��ȣ���� 
			}
			else if(a==0){							//ù��° ����̶��, ��ȣ���� 
				printf("(%d",array[a]);
			}
			else if(array[a]!=0){					//�߰� ��°��� ���̶��, �׳� ��� 
				printf(", %d",array[a]);
			}
		}
		isFirst=1;
	}
}
