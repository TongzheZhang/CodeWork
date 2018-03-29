#include <iostream>
#include<fstream>  
#include<string>
using namespace std;
void main()
{
	cout << "i am in test main function" << endl;
	char test[] = "i am learning";
	for (int i = 0; i < sizeof(test); i++)
	{
		cout << test[i] << endl;
	}
}