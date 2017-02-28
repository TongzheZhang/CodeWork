function [odrIdx,FisherRatio] = rankingFisher(Featrain,gndtrain)
% Ref: [1] Anh Huy Phan, Andrzej Cichocki, Tensor decompositions for feature 
%       extraction and classification of high dimensional datasets.
%       IEICE, NOLTA, vol. 1 No. 1, pp. 37-68 (2010).
% Copyright by Anh Huy Phan, 2010
%%%%%%%%%%%%%%Sort according to Fisher's discriminality%%%%%%%%%%%%%%%
classLabel = unique(gndtrain);
nClass = length(classLabel);%Number of classes
ClsIdxs=cell(nClass,1);
Ns=zeros(nClass,1);
for i=1:nClass
ClsIdxs{i}=find(gndtrain==classLabel(i));
Ns(i)=length(ClsIdxs{i});
end
vecDim = size(Featrain,2);
TSW=zeros(1,vecDim);
TSB=zeros(1,vecDim);
Featrainmean = mean(Featrain);
for i=1:nClass
    clsYp = Featrain(ClsIdxs{i},:);
    clsMean=mean(clsYp,1);
    FtrDiff=bsxfun(@minus,clsYp,clsMean);
    TSW=TSW+sum(FtrDiff.*FtrDiff,1);
    meanDiff=clsMean-Featrainmean;
    TSB=TSB+Ns(i)*meanDiff.*meanDiff;
end
FisherRatio=TSB./TSW;
FisherRatio(isnan(FisherRatio)) = 0;
[FisherRatio,odrIdx]=sort(FisherRatio,'descend');
% FisherRatio = FisherRatio/FisherRatio(1);