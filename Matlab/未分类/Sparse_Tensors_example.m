rand('state',0); %<-- Setup for the script
subs = [1,1,1;1,2,1;3,4,2]; %<-- Subscripts of the nonzeros.
vals = [1; 2; 3]; %<-- The values of the nonzeros.
A = sptensor(subs,vals); %<-- Create a sparse tensor with 3 nonzeros.其余元素为0
B = sptensor(subs,vals,[3 5 2]); %<-- Or, specify the size explicitly.
myfun = @(x) sum(x) / 3; %<-- Total sum divided by three.这么定义函数啊，有点像python里面的
C = sptensor(subs,2*ones(3,1),[4 4 4],myfun); %<-- Maximum element.最后可以随便加一个函数，在同一个位置上默认是累加
D = sptenrand([40 30 20],5); %<-- Create data.
D.subs; %<-- Subscripts of nonzeros. D.vals %<-- Corresponding nonzero values. D.size %<-- The size.
E = sptenrand([10 10 10],0.01); %<-- Create a tensor with 1% nonzeroes,.小于一时是百分比，大于一时就是个数。。无语了
F = sptensor([1 1 1; 2 1 1], 1, [2 1 1]); %<-- Create a sparse tensor.
F = squeeze(F); %<-- Remove singleton dimensions.删掉没用的维度
G = full(F); %<-- Convert it to a (dense) tensor.变成稠密矩阵 或者G = tensor(F)也是一样的，double是变成数组，sptensor也可以变成稀疏矩阵
H = tensor(rand(5,4,2),[5 4 2]); %<-- Create a tensor.
Subs = find(X > 0.9); %<-- Extract subscipts of values greater than 0.9.
Values = X(Subs); %<-- Extract the corresponding values.
I = sptensor(Subs,Values,[5 4 2]); %<-- Create a new tensor
sc = I(3); %<-- Fully specified, single elements are always returned as scalars.
J = elemfun(I, @sqrt); %<-- Square root of every nonzero. Z = elemfun(X, @(x) x+1) %<-- Use a custom function.
K = ones(J); %<-- An easier way to change every nonzero to one.