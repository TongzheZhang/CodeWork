
clc;
clear;
fid=load('try.txt');
fid=fid(1:5,:);
aa=max(fid);
num1=aa(1);num2=aa(2);num3=aa(3);
z=zeros(num1,num2,num3);

for c=1:5
z(fid(c,1),fid(c,2),fid(c,3))=1;
c=c+1;
end
t=tensor(z);
A1 = tenmat(t,[2 3],1);
A2 = tenmat(t,[1 3],2);
A3 = tenmat(t,[1 2],3);

