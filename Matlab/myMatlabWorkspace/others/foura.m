function [Inew,iti] = foura( I,it )
%UNTITLED �˴���ʾ�йش˺�����ժҪ
%   �˴���ʾ��ϸ˵��
Inew = I/(it+1);
for i =1:1:it
salnew = circshift(I,[0,i])/(it+1);
Inew = Inew + salnew;
end
iti = 1/(it+1);
end

