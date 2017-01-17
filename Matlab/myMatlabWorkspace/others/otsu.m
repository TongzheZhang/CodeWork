
function th=otsu(a)


   count=imhist(a);
   [m,n]=size(a);
   N=m*n;
   L=256;
   count=count/N;
 
for i=1:L
    if count(i)~=0
        st=i-1;
        break;
    end
end
for i=L:-1:1
    if count(i)~=0
        nd=i-1;
        break;
    end
end
f=count(st+1:nd+1); 
p=st;   q=nd-st;
u=0;
for i=1:q
    u=u+f(i)*(p+i-1);  
    ua(i)=u;          
end;
 
for i=1:q
    w(i)=sum(f(1:i));  
end;
 
d=(u*w-ua).^2./(w.*(1-w));
[y,tp]=max(d);  
th=tp+p;
 
for i=1:m
    for j=1:n
        if a(i,j)>th
            a(i,j)=0;
        else
            a(i,j)=255;
        end
    end
end 
imshow(a);

end

