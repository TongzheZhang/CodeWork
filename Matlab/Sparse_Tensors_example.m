rand('state',0); %<-- Setup for the script
subs = [1,1,1;1,2,1;3,4,2]; %<-- Subscripts of the nonzeros.
vals = [1; 2; 3]; %<-- The values of the nonzeros.
A = sptensor(subs,vals); %<-- Create a sparse tensor with 3 nonzeros.����Ԫ��Ϊ0
B = sptensor(subs,vals,[3 5 2]); %<-- Or, specify the size explicitly.
myfun = @(x) sum(x) / 3; %<-- Total sum divided by three.��ô���庯�������е���python�����
C = sptensor(subs,2*ones(3,1),[4 4 4],myfun); %<-- Maximum element.����������һ����������ͬһ��λ����Ĭ�����ۼ�
D = sptenrand([40 30 20],5); %<-- Create data.
D.subs; %<-- Subscripts of nonzeros. D.vals %<-- Corresponding nonzero values. D.size %<-- The size.
E = sptenrand([10 10 10],0.01); %<-- Create a tensor with 1% nonzeroes,.С��һʱ�ǰٷֱȣ�����һʱ���Ǹ�������������
F = sptensor([1 1 1; 2 1 1], 1, [2 1 1]); %<-- Create a sparse tensor.
F = squeeze(F); %<-- Remove singleton dimensions.ɾ��û�õ�ά��
G = full(F); %<-- Convert it to a (dense) tensor.��ɳ��ܾ��� ����G = tensor(F)Ҳ��һ���ģ�double�Ǳ�����飬sptensorҲ���Ա��ϡ�����
H = tensor(rand(5,4,2),[5 4 2]); %<-- Create a tensor.
Subs = find(X > 0.9); %<-- Extract subscipts of values greater than 0.9.
Values = X(Subs); %<-- Extract the corresponding values.
I = sptensor(Subs,Values,[5 4 2]); %<-- Create a new tensor
sc = I(3); %<-- Fully specified, single elements are always returned as scalars.
J = elemfun(I, @sqrt); %<-- Square root of every nonzero. Z = elemfun(X, @(x) x+1) %<-- Use a custom function.
K = ones(J); %<-- An easier way to change every nonzero to one.