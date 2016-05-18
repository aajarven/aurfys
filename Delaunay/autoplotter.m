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
    
    plottaaKolmiot(strcat('debug/tyostettava-',num2str(iteraatio)),tumpun);
    disp(strcat('iteraation ',num2str(iteraatio),' työstettävä'))
    pause;
    
    seuraava = strcat('debug/nakyvat-',num2str(iteraatio),'.txt');
    if exist(seuraava, 'file') == 2
        plottaaPisteet(seuraava,vaalharm);
        disp(strcat('iteraation ',num2str(iteraatio),' näkyvät pisteet'));
    else
        disp(strcat('iteraatiossa ',num2str(iteraatio),' ei näkyviä pisteitä'));
    end
    
    seuraava = strcat('debug/kaukaisin-',num2str(iteraatio),'.txt');
    if exist(seuraava, 'file') == 2
        plottaaPisteet(seuraava,'red');
        disp(strcat('iteraation ',num2str(iteraatio),' kaukaisin piste'));
    else
        disp(strcat('iteraatiossa ',num2str(iteraatio),' ei kaukaisinta'));
    end
    pause;
    
    seuraava = strcat('debug/valoisat-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        plottaaKolmiot(seuraava,vaalpun);
        disp(strcat('iteraation ',num2str(iteraatio),' valoisat'))
    else
        disp(strcat('iteraatiossa ',num2str(iteraatio),' ei valoisia'));
    end
    pause;
    
    seuraava = strcat('debug/horisontti-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        plottaaViivat(seuraava, 'cyan');
        disp(strcat('iteraation ',num2str(iteraatio),' horisontti'))
    else
        disp(strcat('iteraatiossa ',num2str(iteraatio),' ei horisonttia'));
    end
    pause;
    
    seuraava = strcat('debug/uudet-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        plottaaKolmiot(strcat('debug/uudet-',num2str(iteraatio)),vaalharm);
        disp(strcat('iteraation ',num2str(iteraatio),' uudet'))
    else
        disp(strcat('iteraatiossa ',num2str(iteraatio),' ei uusia'));
    end
    pause;
    
    seuraava = strcat('debug/palautettavat-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        hold off;
        plottaaKolmiot(seuraava,tumharm);
        disp(strcat('tilanne iteraation ',num2str(iteraatio),' lopuksi'))
    else
        disp(strcat('tilanne ei muuttunut iteraatiossa ',num2str(iteraatio)));
    end
    pause;
end