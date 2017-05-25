%产生一段随机数
N = 100;
P = rand(1,N);
%设n为5，t为50，进行验证
n = 5;
t = 50;
%常规方法算的平均值和标准差
[avernorm1,stdnorm1] = calAverage(t,n,P);
newt = t-1;
[avernorm2,stdnorm2] = calAverage(newt,n,P);
%迭代方法算的
averiter = (avernorm2 * n-P(t-n)+P(t))/n;
if averiter == avernorm1
    disp('成功验证计算平均值的迭代算法')
end
stdP = sum((P(t-n+1:t)-mean(P(t-n+1:t))).^2);
stditer = (stdP/n)^(1/2);

if stditer == stdnorm1
    disp('成功验证计算标准差的迭代算法')
end

