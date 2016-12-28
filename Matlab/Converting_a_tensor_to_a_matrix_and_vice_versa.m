A = tensor(1:24,[3 2 2 2]); %<-- Create a tensor.
B = tenmat(A,[1 2],[3 4]); %<-- Dims [1 2] map to rows, [3 4] to columns. ������Ӧ�ľ��󣬻��ǿ��Ժ����׵ľͻָ���
C = tenmat(A,1); %<-- Same as A = tenmat(X,1,2:4)
D = tenmat(A,1:4,'t'); %<-- Map all the dimensions to the columns
E = tenmat(A,2,'fc'); %<-- Forward cyclic, i.e., [3 4 1]. ��˳��3 4 Ȼ�����1 A = tenmat(X,2,'bc') %<-- Backward cyclic, i.e., [1 4 3].
E.data; %<-- The matrix itself. A.tsize A.rdims A.cdims
F= double(A); %<-- converts A to a standard matrix ת����һ�������ľ���
G = tensor(B);%�Ҿ�˵���׻ָ�������
size(B); %<-- Matrix size
tsize(B); %<-- Corresponding tensor size ������size
norm(B); %<-- Norm of the matrix.
disp(B)