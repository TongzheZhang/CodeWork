A = ones(4,3,2);
B= tensor(A);
C = tensor(rand(5,1)); %<-- Creates a 2-way tensor.
D = tensor(rand(5,1),5); %<-- Creates a 1-way tensor.
whos X Y Z;
A = tenrand([4 3 2]); %<-- Create data.
A.data %<-- The array.让A变数组
disp(A.size);
B = tenones([3 4 2]); %<-- Creates a 3 x 4 x 2 tensor of ones.
ndims(C); %<-- Number of dimensions (or ways).看y是几维的
size(C); %<-- Row vector with the sizes of all dimension.
E = D([1:3]'); %<-- Extract a subtensor.
F = D([1:3]','extract'); %<-- Same thing *but* result is a vector.
B([1 1 1;1 1 2]) = [5;7]; %<-- Replaces the (1,1,1) and (1,1,2) elements.
B(1,1,3) = 1; %<-- Grows the size of the tensor.自动加了一层
lastelement = B(end,end,end);  %<-- Same as X(3,4,3).张量本身知道end具体是什么数
[S,V] = find(A); %<-- Find all the nonzero subscripts and values.
Su = find(A >= 0.1); %<-- Find subscripts of values >= 2.只返回角标
H = tensor(floor(5*rand(2,3,2)));
I = tensor(floor(3*rand(2,3,2)));
H & I;%H | I;xor(H,I);H==I
H./I; %<-- Calls rdivide (but beware divides by zero!)
J = tensor(1:24,[3 4 2]); %<-- Create a tensor.
J = tenfun(@(x)(x+1),J); %<-- Increment every element of A by one.
permute(J,[3 2 1]) %<-- Reverse the modes.打乱顺序




