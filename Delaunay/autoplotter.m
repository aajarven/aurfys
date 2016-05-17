tumharm = [0.5, 0.5, 0.5];
vaalharm = [0.8, 0.8, 0.8];
tumpun = [0.5, 0, 0];
vaalpun = [0.8, 0, 0];
iteraatio = 0;

plottaaKolmiot('debug/ensimmaiset', tumharm);
disp('ensimmäinen tetraedri')
pause;


while true
    hold on;
    iteraatio=iteraatio+1;
    
    disp(strcat('debug/tyostettava-',num2str(iteraatio)))
    plottaaKolmiot(strcat('debug/tyostettava-',num2str(iteraatio)),tumpun);
    disp(strcat('iteraation ',num2str(iteraatio),' työstettävä'))
    pause;
    
    plottaaPisteet(strcat('debug/nakyvat-',num2str(iteraatio),'.txt'),tumharm);
    disp(strcat('iteraation ',num2str(iteraatio),' näkyvät pisteet'));
    pause;
    
    plottaaPisteet(strcat('debug/kaukaisin-',num2str(iteraatio),'.txt'),'red');
    disp(strcat('iteraation ',num2str(iteraatio),' kaukaisin piste'));
    pause;
    
    plottaaKolmiot(strcat('debug/valoisat-',num2str(iteraatio)),vaalpun);
    disp(strcat('iteraation ',num2str(iteraatio),' valoisat'))
    pause;
    
    plottaaViivat(strcat('debug/horisontti-',num2str(iteraatio)), 'cyan');
    disp(strcat('iteraation ',num2str(iteraatio),' horisontti'))
    pause;
    
    plottaaKolmiot(strcat('debug/uudet-',num2str(iteraatio)),vaalharm);
    disp(strcat('iteraation ',num2str(iteraatio),' uudet'))
    pause;
    
    hold off;
    plottaaKolmiot(strcat('debug/palautettavat-',num2str(iteraatio)),tumharm);
    disp(strcat('tilanne iteraation ',num2str(iteraatio),' lopuksi'))
    pause;
end