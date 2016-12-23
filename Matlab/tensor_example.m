M = ones(4,3,2);
X = tensor(M);
Y = tensor(rand(5,1)); %<-- Creates a 2-way tensor.
Z = tensor(rand(5,1),5); %<-- Creates a 1-way tensor.
whos X Y Z;
A = tenrand([4 3 2]); %<-- Create data.
A.data %<-- The array.让A变数组
disp(A.size);
B = tenones([3 4 2]); %<-- Creates a 3 x 4 x 2 tensor of ones.
ndims(Y); %<-- Number of dimensions (or ways).看y是几维的
size(Y); %<-- Row vector with the sizes of all dimension.