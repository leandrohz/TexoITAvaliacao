# Avaliação TEXO IT

Importar o projeto para a IDE da sua preferência. 

Executar o programa a partir da classe <b>AvaliacaoApplication.java</b>;

Executar os testes a partir do <b>diretório test - pacote com.texoit.avaliacao</b>;

Para acessar a documentação da <b>API REST</b>, acessar: <b>http://localhost:8080/swagger-ui.html </b>

* O dataset original é carregado automaticamente na base de dados (H2) e pode ser substituído, mantendo o mesmo nome do arquivo "movielist.csv", ou alterando o caminho na consulta SQL (arquivo data.sql).

## Especificação do Teste

Desenvolver uma API RESTful para possibilitar a leitura da lista de indicados e vencedores
da categoria Pior Filme do Golden Raspberry Awards.

## Requisito do sistema:
1) Ler o arquivo CSV dos filmes e inserir os dados em uma base de dados ao iniciar a
   aplicação.

## Requisitos da API
2) Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que
obteve dois prêmios mais rápido, seguindo a especificação de formato definida na
página 2;

## Requisitos não funcionais do sistema:

1) O web service RESTful deve ser implementado com base no nível 2 de maturidade
   de Richardson;
2) Devem ser implementados somente testes de integração. Eles devem garantir que
   os dados obtidos estão de acordo com os dados fornecidos na proposta;
3) O banco de dados deve estar em memória utilizando um SGBD embarcado (por
   exemplo, H2). Nenhuma instalação externa deve ser necessária;
4) A aplicação deve conter um readme com instruções para rodar o projeto e os
   testes de integração.

O código-fonte deve ser disponibilizado em um repositório git (Github, Gitlab, Bitbucket,
etc).

### Atenção: Na avaliação serão utilizados outros conjuntos de dados com cenários diferentes, portanto é importante garantir a precisão dos resultados independente dos dados de entrada.

![image](https://user-images.githubusercontent.com/11754255/138574124-2b2f005e-e153-4768-99cf-6944699eb2a8.png)
