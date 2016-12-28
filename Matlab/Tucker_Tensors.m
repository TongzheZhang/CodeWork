core = tensor(rand(3,2,1),[3 2 1]); %<-- The core tensor.
U = {rand(5,3), rand(4,2), rand(3,1)}; %<-- The matrices. �������󱻴�����Ȧ����
A = ttensor(core,U); %<-- Create the ttensor.
core1 = sptenrand([3 2 1],3); %<-- Create a 3 x 2 x 1 sptensor.
B = ttensor(core1,U); %<-- Core is a sptensor.

V = {rand(3,2),rand(2,2),rand(1,2)}; %<-- Create some random matrices.
core2 = ktensor(V); %<-- Create a 3 x 2 x 1 ktensor.
C = ttensor(core2,U); %<-- Core is a ktensor.

core3 = ttensor(tensor(1:8,[2 2 2]),V); %<-- Create a 3 x 2 x 1 ttensor.
D = ttensor(core3,U); %<-- Core is a ttensor. �˻���һ��t����

get_core = A.core; %tucker �����ĺ�
get_matrices = A.U; %<-- Cell array of matrices.

E = full(A); %<-- Converts to a tensor. ת����������ttensor������ת����t����
f = A.U{2}; %<-- Extract a matrix.������Ŷ
A.core = tenones(size(A.core)); %<-- Insert a new core. t�����ļӼ��ں���
