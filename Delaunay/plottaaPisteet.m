function [] = plottaaPisteet(tiedostonimi, vari)
    koordinaatit = csvread(tiedostonimi);
    X = koordinaatit(:,1);
    Y = koordinaatit(:,2);
    Z = koordinaatit(:,3);
    scatter3(X,Y,Z,20,vari);
end

