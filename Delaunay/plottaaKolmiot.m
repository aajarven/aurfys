function [] = plottaaKolmiot(basename)
    X = transpose(csvread(strcat(basename,'-x.txt')))
    Y = transpose(csvread(strcat(basename,'-y.txt')))
    Z = transpose(csvread(strcat(basename,'-z.txt')))

    fill3(X,Y,Z,[0.5, 0.5, 0.5]);
end