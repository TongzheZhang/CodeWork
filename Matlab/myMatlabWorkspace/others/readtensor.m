
clc;
clear;
fid=load('try.txt');
fid=fid(1:5,:);
aa=max(fid);
 num1=aa(1);num2=aa(2);num3=aa(3);
% num1=5;num2=5;num3=5;
z=zeros(num1,num2,num3);

for c=1:5
z(fid(c,1),fid(c,2),fid(c,3))=1;
c=c+1;
end
t=tensor(z);
x=cp_als(t,5);
result = tensor(x);

