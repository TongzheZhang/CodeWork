%% ��ʼ��ֵ����ʼ������
subs = [1 1 1;2 1 1;3 1 1;2 1 2;2 2 2;2 3 2;3 3 1;3 3 2;3 3 3;3 3 4;3 3 5];
my_sptensor = sptensor(subs,1);
my_tensor = tensor(my_sptensor);
%% ����ͬ��ģչ������
A1 = tenmat(my_tensor,1);
A2 = tenmat(my_tensor,2);
A3 = tenmat(my_tensor,3);
%% �ֽ�չ������
[U1,S1,V1] = svd(A1.data);
[U2,S2,V2] = svd(A2.data);
[U3,S3,V3] = svd(A3.data);
%% ȥ������
U1(:,3) = [];
U2(:,3) = [];
U3(:,3:5) = [];
%% ���������������ع�
S = ttm(my_tensor,{U1',U2',U3'});
re_my_tensor = ttm(S,{U1,U2,U3}); %tucker��������������U1��U2��U3
