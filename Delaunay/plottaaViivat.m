function [] = plottaaViivat(basename, vari)
    X = transpose(csvread(strcat(basename,'-x.txt')));
    Y = transpose(csvread(strcat(basename,'-y.txt')));
    Z = transpose(csvread(strcat(basename,'-z.txt')));
    
    plot3(X,Y,Z,vari)
end