   <!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
</head>
<body>
  <h1 id="mygamelist">MyGameList</h1>
<p>Base de dados de jogos, plataformas e géneros. Esta base de dados irá conter informação detalhada de jogos onde eu poderei guardar a lista de jogos que joguei e se o completei ou não.</p>
	<p>Terá 5 tabelas:</p>
  <h4 id="jogos">Jogos: Informação detalhada sobre jogos.</h4>
	<p>Esta tabela conterá 5 atributos.</p>
  <p>-ID_jogo;<br>
  -Nome_jogo;<br>
  -Image_jogo;<br>
  -Atividade_jogo;<br>
  -Data_Lancamento_jogo;</p>
  <h4 id="plataformas">Plataformas: Informação das plataformas.</h4>
  <p>Esta tabela conterá 2 atributos.
    -ID_plataforma;
    -Nome_plataforma;</p>
 
<h4 id="jogos_plataformas">Jogos_Plataformas: Relação entre as tabelas Jogos e Plataformas.</h4>
<p>Esta tabela conterá 2 atributos.
    -Plataformas_ID_plataforma;
    -Jogos_ID_jogo;</p>
<h4 id="generos">Géneros: Informaçãodos géneros.</h4>
<p>Géneros: Informação detalhada dos géneros.
  Esta tabela conterá 2 atributos.
    -ID_genero;
    -Nome_genero;</p>
  <h4 id="jogos_generos">Jogos_Géneros: Relação entre Jogos e Géneros.</h4>
<p>-Jogos_Géneros: Relação entre Jogos e Géneros.
  Esta tabela conterá 2 atributos.
    -Jogos_ID_jogo;
    -Generos_ID_genero;</p>
    </body>
</html>
    
![](images/OtDKil7%20-%20Imgur.png)
