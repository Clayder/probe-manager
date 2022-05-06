
# :fist_right::fist_left: Objetivo:

O objetivo do projeto é criar um simulador, para poder controlar sondas em outros planetas por meio de comandos.

## Explicação da necessidade:

Tamanho da área do planeta : 5x5

Posição de pouso da sonda 1: **x=1, y=2** apontando para Norte
Sequencia de comandos: `LMLMLMLMM`
Posição final da sonda: **x=1 y=3** apontando para Norte

Posição de pouso da sonda 2: **x=3, y=3** apontando para Leste
Sequencia de comandos: `MMRMMRMRRML`
Posição final da sonda: **x=5 y=1** apontando para Norte

### Detalhes sobre o funcionamento acima:

A sequência de comandos é um conjunto de instruções enviadas da terra para a sonda, onde :
- `M` -> Andar para a frente na direção que está 1 posição.
- `L` -> Virar a sonda para a esquerda (90 graus)
- `R` -> Virar a sonda para a direita (90 graus)

A área do planeta é um plano cartesiano com o tamanho informado pelo operador.

A orientação da sonda dentro do plano cartesiano usa uma rosa dos ventos como referência

![rosa dos ventos](http://i.imgur.com/li8Ae5L.png "Rosa dos Ventos")

# :man_technologist: Tecnologias utilizadas

 1. **Spring Boot**
 2. **MySQL**
 3. **Docker**
 4. **Swagger** 
 5. **Flyway**: Para criação das "migrations" do banco de dados
 6. **Spring Security**
 7. **Postman** 
 8. **JUnit**


# :alien: Testes

Esse projeto foi desenvolvido utilizando testes de integração e de unidade. 
Na camada **controller** e **repository** foi implementado teste de integração com o banco de dados.

![Peek 06-05-2022 18-222](https://user-images.githubusercontent.com/8002128/167218389-73b01c98-7473-4634-bc47-fe84e22bcf11.gif)

# :open_file_folder: Organização do projeto

![image](https://user-images.githubusercontent.com/8002128/167219157-de91573b-bc30-40eb-a5ee-77ddbadfcbdc.png)

## Principais arquivos/diretórios

 1. **api**: Todas as funcionalidades referente a API da aplicação.
 2. **core** : Núcleo da aplicação, utilizado para reúso de código.
 3. **domain**: Camada utilizada para implementação das regras de negocio, o seu principal objetivo é ser independente de framework.
 4. **db.migration**: Onde fica armazenado os arquivos de migrations que são executados pelo Flyway para criar o banco de dados. 
 5. **test**: Local onde fica armazenado os testes da aplicação.
 6. **test.api.controller**: Teste de integração utilizando o banco de testes.
 7. **docker-compose.yaml**: Utilizado para criar o banco de dados de desenvolvimento e testes.

# :floppy_disk: Banco de dados

## Modelagem

![image](https://user-images.githubusercontent.com/8002128/167210855-f9b7c2af-05a7-457f-b966-764025a9a481.png)

## Banco de dados

Os bancos de **desenvolvimento** e **testes** podem ser utilizados através de contêiner docker .

Para subir o banco é só executar o comando: `docker-compose up --build`

**Obs:** Em toda execução dos testes de integração o banco de teste é apagado.

**Credenciais do banco de desenvolvimento:** 

**Porta**: 3306

    MYSQL_DATABASE: 'probe_manager'  
	MYSQL_USER: 'user'  
	MYSQL_PASSWORD: 'password'  
	MYSQL_ROOT_PASSWORD: 'password'

**Credenciais do banco de testes:** 

**Porta**: 3307

    MYSQL_DATABASE: 'probe_manager_test'  
	MYSQL_USER: 'user'  
	MYSQL_PASSWORD: 'password'  
	MYSQL_ROOT_PASSWORD: 'password'

# :spiral_notepad: Documentação da API

Toda a API foi documentada utilizando o Swagger.
Para acessar a documentação, é só acessar a URL [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)


![image](https://user-images.githubusercontent.com/8002128/167207909-c77ee9a2-e398-41ba-93fb-c8a5b1e37572.png)


# :closed_lock_with_key: Autenticação na API

## Dados do usuário default

  **email**: admin@gmail.com,
  **password**: 123456

![image](https://user-images.githubusercontent.com/8002128/167209153-524f516e-c66f-4e33-8ea9-605bb410e198.png)


## Exemplo de uso

Após a autenticação é só passar o token, nos cabeçalhos das requisições.

![image](https://user-images.githubusercontent.com/8002128/167209840-bb58c8fd-df2d-4e5d-aac0-c7d030358a34.png)

**Utilização no Swagger**

![image](https://user-images.githubusercontent.com/8002128/167209995-c3ae2dab-8d42-42de-a38a-6f1bbacb8032.png)



