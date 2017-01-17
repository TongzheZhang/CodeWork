function [Inew,iti] = foura( I,it )
%UNTITLED 此处显示有关此函数的摘要
%   此处显示详细说明
Inew = I/(it+1);
for i =1:1:it
salnew = circshift(I,[0,i])/(it+1);
Inew = Inew + salnew;
end
iti = 1/(it+1);
end

