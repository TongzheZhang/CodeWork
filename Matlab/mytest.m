fest = [1 2 3;1 1 3;1 2 5];
label = [1 0 1]';
% rankingFisher()
size(label,1);
[odrIdx,FisherRatio] = rankingFisher(fest,label)
for i=1:10
    disp(i)
end %control+i 是只能缩进
