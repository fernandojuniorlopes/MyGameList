# MyGameList
Base de dados de jogos, plataformas e géneros. Esta base de dados irá conter informação detalhada de jogos onde eu poderei guardar a lista de jogos que joguei e se o completei ou não.
Terá 5 tabelas:
-Jogos: Informação detalhada sobre jogos.
  Esta tabela conterá 4 atributos.
    -ID_jogo;
    -Nome_jogo;
    -Image_jogo;
    -Atividade_jogo;
    
-Plataformas: Informação detalhada das plataformas.
  Esta tabela conterá 2 atributos.
    -ID_plataforma;
    -Nome_plataforma;
    
-Jogos_Plataformas: Relação entre as tabelas Jogos e Plataformas.
  Esta tabela conterá 2 atributos.
    -Plataformas_ID_plataforma;
    -Jogos_ID_jogo;
    
-Géneros: Informação detalhada dos géneros.
  Esta tabela conterá 2 atributos.
    -ID_genero;
    -Nome_genero;
  
-Jogos_Géneros: Relação entre Jogos e Géneros.
  Esta tabela conterá 2 atributos.
    -Jogos_ID_jogo;
    -Generos_ID_genero;
    
![](images/OtDKil7%20-%20Imgur.png)
