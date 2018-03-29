//
#include <iostream>
#include <string>
#include "stdafx.h"
#include <stdlib.h>
using namespace std;

int main()
{
	int i, sum = 0, n = 100;
	for (i = 1; i <= n; i++)
	{
		sum = sum + i;

	}
	printf("%d", sum);
	system("pause");
    return 0;
}

