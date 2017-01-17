%Problem 7.8
%ECE6258: Digital imaclcge processing 
%Fall 2015
%Prof. Ghassan Alregib 
%School of Electrical and Computer Engineering 
%Georgia Instiute of Technology 

function [] = NoiseEstimation(img)
%%
% This function tkaes an image "img" and allow the user to select a region
% in the image to be used for noise estimation assuming white gaussian
% noise with zero mean

% The function is called as: NoiseEstimation(img)
% The variance and PDF will be displayed 
%%

%selecting a patch from the image 
[B,c,r] = roipoly(img);

% calculating the histogram 
[n x]=hist(double(img(B)),30);
%normalizing to 1 to make it like a PDF 
n = n/sum(n); 

% mean value calculation 
mean_val= sum(n.*x);

%Variance calculation 
variance = sum(n.*x.^2)-mean_val^2;
y = normpdf(x,mean_val,sqrt(variance));
y = y/sum(y); 

variance = variance/255^2; 
disp(' '); 
disp(['Variance =',num2str(variance)]); 

bar(x,n,'w'); 
hold on 
plot(x,y,'r','LineWidth',2); 
axis tight
legend('Histogram of the selected region','Fitted Gaussian PDF'); 
xlabel('Pixel value'); 
ylabel('Occurance'); 

end 