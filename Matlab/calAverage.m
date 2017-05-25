function [ aver,Std ] = calAverage( t,n,P )
%calAverage 计算t天的前数n天的平均数
%   此处显示详细说明
newP = P(t-n+1:t);
aver = mean(newP);
Std = std(newP);
end

