function [ output ] = stackBlur(I,it )
%UNTITLED2 �˴���ʾ�йش˺�����ժҪ
%   �˴���ʾ��ϸ˵��
output = I/(it+1);
for i =1:1:it
salnew = circshift(I,[0,i])/(it+1);
output = output + salnew;
end


end

