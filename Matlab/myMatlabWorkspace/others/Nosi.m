
 function [ output ] = Nosi(I,it )
%UNTITLED2 �˴���ʾ�йش˺�����ժҪ
%   �˴���ʾ��ϸ˵��
 nos=normrnd(0,it,401,600,3);
output = double(I) + nos;


end