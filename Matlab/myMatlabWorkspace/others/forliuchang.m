function [ a, P] = forliuchang( lasta, r, ed, lastP)
lambda = 0.1;
K = (lastP*ed)/(ed'*lastP*ed+lambda);
a = lasta + K*(r-ed'*lasta);
P = (eye(2)-K*ed')*lastP/lambda;
end

