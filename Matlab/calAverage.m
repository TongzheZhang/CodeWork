function [ aver,Std ] = calAverage( t,n,P )
%calAverage ����t���ǰ��n���ƽ����
%   �˴���ʾ��ϸ˵��
newP = P(t-n+1:t);
aver = mean(newP);
Std = std(newP);
end

