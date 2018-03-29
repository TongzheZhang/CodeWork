a0 = [0;0.0924];
%ed = [0; 0.13258445];
%r = 0.0122528;
result = [];
lastP = [100000,0;0,56.8548907540434];
for i = 1:41
    ed = [para(i,1); para(i,2)];
    r = para(i,3);
    [a0, lastP] = forliuchang(a0, r, ed, lastP);
    result = [result a0];
end

