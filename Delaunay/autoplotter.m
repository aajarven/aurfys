tumharm = [0.5, 0.5, 0.5];
vaalharm = [0.8, 0.8, 0.8];
tumpun = [0.5, 0, 0];
vaalpun = [0.8, 0, 0];
iteraatio = 0;
edellinenToteutunut = 1;
kuva = 0;

plottaaKolmiot('debug/ensimmaiset', tumharm);
hold on;
plottaaPisteet('debug/pisteet.txt', vaalharm);
%disp('ensimmäinen tetraedri')
axis square;


while true
    iteraatio=iteraatio+1;
    disp(num2str(iteraatio));
    plottaaKolmiot(strcat('debug/tyostettava-',num2str(iteraatio)),tumpun);
    print(strcat('data/kolmiointianimaatio/',num2str(kuva)), '-dpng');
    kuva = kuva+1;
    %disp(strcat('iteraation ',num2str(iteraatio),' työstettävä'))
    %pause;
    
    seuraava = strcat('debug/nakyvat-',num2str(iteraatio),'.txt');
    if exist(seuraava, 'file') == 2
        plottaaPisteet(seuraava,'black');
        %disp(strcat('iteraation ',num2str(iteraatio),' näkyvät pisteet'));
        print(strcat('data/kolmiointianimaatio/',num2str(kuva)), '-dpng');
        kuva = kuva+1;
    else
        %disp(strcat('iteraatiossa ',num2str(iteraatio),' ei näkyviä pisteitä'));
    end
    
    seuraava = strcat('debug/kaukaisin-',num2str(iteraatio),'.txt');
    if exist(seuraava, 'file') == 2
        plottaaPisteet(seuraava,'red');
        %disp(strcat('iteraation ',num2str(iteraatio),' kaukaisin piste'));
        edellinenToteutunut = iteraatio;
        print(strcat('data/kolmiointianimaatio/',num2str(kuva)), '-dpng');
        kuva = kuva+1;
    else
        %disp(strcat('iteraatiossa ',num2str(iteraatio),' ei kaukaisinta'));
    end
    %pause;
    
    seuraava = strcat('debug/valoisat-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        plottaaKolmiot(seuraava,vaalpun);
        %disp(strcat('iteraation ',num2str(iteraatio),' valoisat'))
        print(strcat('data/kolmiointianimaatio/',num2str(kuva)), '-dpng');
        kuva = kuva+1;
    else
        %disp(strcat('iteraatiossa ',num2str(iteraatio),' ei valoisia'));
    end
    %pause;
    
    seuraava = strcat('debug/horisontti-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        plottaaViivat(seuraava, 'cyan');
        %disp(strcat('iteraation ',num2str(iteraatio),' horisontti'))
        print(strcat('data/kolmiointianimaatio/',num2str(kuva)), '-dpng');
        kuva = kuva+1;
    else
        %disp(strcat('iteraatiossa ',num2str(iteraatio),' ei horisonttia'));
    end
    %pause;
    
    seuraava = strcat('debug/uudet-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        plottaaKolmiot(strcat('debug/uudet-',num2str(iteraatio)),vaalharm);
        %disp(strcat('iteraation ',num2str(iteraatio),' uudet'))
        print(strcat('data/kolmiointianimaatio/',num2str(kuva)), '-dpng');
        kuva = kuva+1;
    else
        %disp(strcat('iteraatiossa ',num2str(iteraatio),' ei uusia'));
    end
    %pause;
    
    hold off;
    seuraava = strcat('debug/palautettavat-',num2str(iteraatio));
    if exist(strcat(seuraava,'-x.txt'), 'file') == 2
        plottaaKolmiot(seuraava,tumharm);
        %disp(strcat('tilanne iteraation ',num2str(iteraatio),' lopuksi'))
    else
        %disp(strcat('tilanne ei muuttunut iteraatiossa ',num2str(iteraatio)));
        plottaaKolmiot(strcat('debug/palautettavat-',num2str(edellinenToteutunut)),tumharm);
    end
    hold on;
    axis square;
    plottaaPisteet('debug/pisteet.txt', vaalharm);
    print(strcat('data/kolmiointianimaatio/',num2str(kuva)), '-dpng');
    kuva = kuva+1;
    %pause;
end