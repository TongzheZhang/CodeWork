%����һ�������
N = 100;
P = rand(1,N);
%��nΪ5��tΪ50��������֤
n = 5;
t = 50;
%���淽�����ƽ��ֵ�ͱ�׼��
[avernorm1,stdnorm1] = calAverage(t,n,P);
newt = t-1;
[avernorm2,stdnorm2] = calAverage(newt,n,P);
%�����������
averiter = (avernorm2 * n-P(t-n)+P(t))/n;
if averiter == avernorm1
    disp('�ɹ���֤����ƽ��ֵ�ĵ����㷨')
end
stdP = sum((P(t-n+1:t)-mean(P(t-n+1:t))).^2);
stditer = (stdP/n)^(1/2);

if stditer == stdnorm1
    disp('�ɹ���֤�����׼��ĵ����㷨')
end

