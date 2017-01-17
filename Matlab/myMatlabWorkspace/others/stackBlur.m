function [ output ] = stackBlur(I,it )
%UNTITLED2 此处显示有关此函数的摘要
%   此处显示详细说明
output = I/(it+1);
for i =1:1:it
salnew = circshift(I,[0,i])/(it+1);
output = output + salnew;
end


end

