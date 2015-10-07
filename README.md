# Roteirizador
Roteirizador para o menor caminho, informando também o custo do roteiro

# Developers
- Vinicius Antonio Gai - viniciustoni@gmail.com

# Tecnologias
As técnologias utilizadas para o desenvolvimento da aplicação foram:
- Maven 3
- Spring Framework
- Spring boot
- Hibernate
- HSQLDB
- Swagger - Frameworks para API
- Jung2 (Framework para calculo de roteiros)

# Como executar a aplicação?
Para rodar a aplicação é necessário conter o Maven 3 instalado e configurado na maquina.
Após isso basta executar no prompt, dentro da pasta da aplicação os seguintes comandos maven:
- Compilando a aplicação: mvn clean package
- Executando a aplicação: mvn spring-boot:run -Dserver.port=8080

* As libs necessárias para a execução da aplicação, serão baixadas automaticamente com o maven.
* O banco de dados será criado no seguinte diretorio: ~/roteirizador_db/roteirizador_db.hsqldb

Após subir a aplicação com o maven basta acessar a seguinte URL: http://localhost:8080/index.html

# Como utilizar a aplicação?
Como a aplicação está executando o framework do Swagger, ao acessar a URL (http://localhost:8080/index.html) será redirecionado para uma pagina onde irá conter todos os serviços Rest da aplicação:
- /roteirizador/roteiro/cadastraMapaRoteiro : Cadastra uma malha de roteiros
- /roteirizador/roteiro/calculaRoteiro : Consulta o melhor roteiro para a Malha, origem, Destino informados, além de informar qual é o custo da rota, através da Autonomia e valor do combustivel informado.
- /roteirizador/roteiro/removeMapaRoteiro : Remove a malha de roteiro informado

* Para todos os serviços, caso hajam problemas de validação de dados(dados faltantes etc..) será lançado um erro 500, na mensagem haverá detalhes do valor que está faltando.

## /roteirizador/roteiro/cadastraMapaRoteiro
URL completa: http://localhost:8080/roteirizador/roteiro/cadastraMapaRoteiro

- JSON request:
{
  "nomMapaRoteiro": "string",
  "roteirosTO": [
    {
      "nomOrigem": "string",
      "nomDestino": "string",
      "distancia": 0
    }
  ]
}

* Campos:
- nomMapaRoteiro: Nome do mapa de roteiro.
- roteirosTO : Lista de rotas do mapa
  - nomOrigem : Nome da origem da rota
  - nomDestino : Nom do destino da rota
  - distancia : Distancia entre os pontos

- Response : 
  - HTTP status code: 200 em caso de sucesso
  - HTTP status code: 500 em caso de erro

* Exemplo de chamada Request
![cadastraMapaRoteiroRequest](https://github.com/viniciustoni/roteirizador/blob/master/img_doc/RequestCadastraMapaRoteiro.png)

* Exemplo de response
![cadastraMapaRoteiroResponse](https://github.com/viniciustoni/roteirizador/blob/master/img_doc/ResponseCadastraMapaRoteiro.png)

## /roteirizador/roteiro/calculaRoteiro
URL completa: http://localhost:8080/roteirizador/roteiro/calculaRoteiro

- JSON request:
{
  "nomMapaRoteiro": "string",
  "nomOrigem": "string",
  "nomDestino": "string",
  "vlrAutonomia": 0,
  "vlrCombustivel": 0
}

* Campos:
- nomMapaRoteiro: Nome do mapa de roteiro.
- nomOrigem : Nome da origem da rota
- nomDestino : Nom do destino da rota
- vlrAutonomia : Autonomia do veiculo
- vlrCombustivel : Preço do litro de combustivel

- Response : 
  - HTTP status code: 200 em caso de sucesso
  - HTTP status code: 500 em caso de erro
  - JSON de response:
{
  "menssagemRetorno": "string",
  "vlrCustoTrajeto": 0,
  "rota": [
    "string"
  ],
  "roteirosTO": [
    {
      "nomOrigem": "string",
      "nomDestino": "string",
      "distancia": 0
    }
  ]
}

* Campos
- menssagemRetorno : Mensagem de retorno, contendo os dados da rota, distancia e valor de combustivel total
- vlrCustoTrajeto : Valor do custo do trajeto
- rota : Lista contendo os pontos de passagens na ordem
- roteirosTO : Lista contendo os dados de cada roteiro
  - nomOrigem : Nome da origem da rota
  - nomDestino : Nome do destino da rota
  - distancia : Distancia da rota


* Exemplo de chamada Request
![RequestCalculaRoteiro](https://github.com/viniciustoni/roteirizador/blob/master/img_doc/RequestCalculaRoteiro.png)

* Exemplo de response
![ResponseCalculaRoteiro](https://github.com/viniciustoni/roteirizador/blob/master/img_doc/ResponseCalculaRoteiro.png)

## /roteirizador/roteiro/removeMapaRoteiro
URL completa: http://localhost:8080/roteirizador/roteiro/removeMapaRoteiro

- JSON request:
  String contendo o nome do mapa de roteiro

- Response : 
  - HTTP status code: 200 em caso de sucesso
  - HTTP status code: 500 em caso de erro

* Exemplo de chamada Request
![RequestRemoveMapaRoteiro](https://github.com/viniciustoni/roteirizador/blob/master/img_doc/RequestRemoveMapaRoteiro.png)

* Exemplo de response
![ResponseRemoveMapaRoteiro](https://github.com/viniciustoni/roteirizador/blob/master/img_doc/ResponseRemoveMapaRoteiro.png)

# Deletar data base
Caso queira apagar todo o banco de dados, recomendo a deleção fisica do mesmo, pois ao subir a aplicação o hibernate se encarregara de cria-lo novamente. 
O banco de dados fica na pasta: ~/roteirizador_db 
Basta apagar a pasta e o banco será eliminado.
