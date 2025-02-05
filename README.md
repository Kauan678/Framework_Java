# CRUD Framework em Java

## Descrição do Projeto
Este projeto é um framework simples em Java para gerenciar operações CRUD (Create, Read, Update, Delete) em entidades genéricas. O framework oferece duas implementações de repositórios: uma na memória e outra utilizando arquivos para armazenamento. Ele foi desenvolvido seguindo os princípios de programação orientada a objetos (POO) e boas práticas de design

## Estrutura do Projeto

```
CRUD_FRAMEWORK/                             <- Diretório raiz do projeto
├── .vscode/                                <- Configurações do VS Code
├── bin/                                    <- Arquivos compilados (.class)
├── lib/                                    <- Dependências externas (não há dependências)
├── manifest/                               <- Diretório do arquivo de manifesto
│   └── MANIFEST.MF                         <- Metadados do arquivo JAR
├── src/                                    <- Código-fonte do projeto
│   └── framework/                          <- Pacote principal
│       ├── CrudRepository.java             <- Interface CRUD genérica
│       ├── Main.java                       <- Classe principal com a interface gráfica
│       ├── SerializableEntity.java         <- Interface para serialização/desserialização
│       ├── impl/                           <- Implementações de repositório
│       │   ├── InFileRepository.java       <- Implementação CRUD com armazenamento em arquivos
│       │   └── InMemoryRepository.java     <- Implementação CRUD com armazenamento em memória
│       └── model/                          <- Modelos de dados
│           └── Produto.java                <- Classe da entidade Produto
├── crud_framework.jar                      <- Arquivo JAR gerado
├── print_logs.png                          <- Arquivo de imagem com o funcionamento da app 
├── produtos.txt                            <- Arquivo de persistência dos produtos
└── README.md                               <- Documentação do projeto

```
## Funcionalidades
- Cadastro, consulta, atualização e remoção de entidades genéricas
- Implementações de repositórios:
  - **InMemoryRepository:** Armazena dados em memória
  - **InFileRepository:** Armazena dados em arquivos
- Exemplo com a entidade `Produto`

## Dependências
Este projeto utiliza apenas as bibliotecas padrões do Java, não sendo necessário instalar dependências externas

## Como compilar e executar

1. **Navegue até a pasta do projeto:**
   ```sh
   cd Atvs-RS\framework_java\crud_framework
   ```

2. **Compile os arquivos:**
   ```sh
   javac -d bin (Get-ChildItem -Recurse -Filter *.java).FullName
   ```

3. **Execute o programa:**
   ```sh
   java -cp bin framework.Main
   ```

## Exemplo de Saída
```plaintext
A saída são os produtos no arquivo "produtos.txt" quando armazenados em arquivo. Quando armazenado em memória ele permanece enquanto o programa está em execução, após o encerramento do programa o produto é deletado da memória
```

## Contribuições
Aceito contribuições! Sintam-se à vontade para enviar *pull requests* ou abrir *issues*