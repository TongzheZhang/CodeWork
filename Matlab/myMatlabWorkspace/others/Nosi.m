
 function [ output ] = Nosi(I,it )
%UNTITLED2 此处显示有关此函数的摘要
%   此处显示详细说明
 nos=normrnd(0,it,401,600,3);
output = double(I) + nos;


end