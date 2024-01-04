# Desafio Votação - Avaliação técnica

## Objetivo:
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução backend para gerenciar essas sessões de votação.

### Tecnologias Utilizadas
Java 17, Spring Boot 3 e PostgressSQL.


### Manual/Swagger API
[http:localhost:8080/swagger-ui/index.html](http:localhost:8080/swagger-ui/index.html)



## API - Endpoints

### Associados.
#### Cadastro:
```
  POST /api/associado/create
```
##### Dados de entrada
| Campo  |   Tipo   | Obrigatório  | Local de entrada |             Descrição              |
|:------:|:--------:|:------------:|:----------------:|:----------------------------------:|
| `nome` | `string` |     Sim      |       Body       | Nome do associado                  |
| `cpf`  | `string` |     Sim      |       Body       | CPF do associado                   |

#### Lista:
##### Dados de entrada
```
  GET /api/associado/list
```
|    Campo    |   Tipo    | Obrigatório | Local de entrada |           Descrição            |
|:-----------:|:---------:|:-----------:|:----------------:|:------------------------------:|
| `id`        | `integer` |     Não     |   RequestParam   | Código identificador           |
| `cpf`       | `string`  |     Não     |   RequestParam   | CPF do associado               |
| `nome`      | `string`  |     Não     |   RequestParam   | Nome do associado              |

##### Dados de retorno
|     Campo      |   Tipo    | Obrigatório |                   Descrição                    |
|:--------------:|:---------:|:-----------:|:----------------------------------------------:|
| `nome`         | `string` |     Sim     |   Nome do associado                             |
| `cpf`          | `string` |     Sim     |   CPF do associado                              |
| `dataCriacao`  | `date`   |     Sim     |   Data da criação do associado                  |


### Pautas.
#### Cadastro:
```
  POST /api/pauta/create
```
##### Dados de entrada
|    Campo    |   Tipo   | Obrigatório  | Local de entrada |             Descrição              |
|:-----------:|:--------:|:------------:|:----------------:|:----------------------------------:|
|`titulo`     | `string` |     Sim      |       Body       | Titulo da pauta                    |
|`descricao`  | `string` |     Sim      |       Body       | Descricao da pauta                 |

#### Contabilização dos Votos:
```
  POST /api/pauta/count/{idPauta}
```
##### Dados de entrada
|    Campo    |   Tipo   | Obrigatório  | Local de entrada |             Descrição              |
|:-----------:|:--------:|:------------:|:----------------:|:----------------------------------:|
| `id`        |`integer` |     Sim      | PathVariavel     | Titulo da pauta                    |

#### Lista:
```
  GET api/pauta/list
```
|    Campo    |   Tipo    | Obrigatório | Local de entrada |           Descrição            |
|:-----------:|:---------:|:-----------:|:----------------:|:------------------------------:|
| `id`        | `integer` |     Não     |   RequestParam   | Código identificador           |
| `titulo`    | `string`  |     Não     |   RequestParam   | Titulo da pauta                |
| `descricao` | `string`  |     Não     |   RequestParam   | Descricao da pauta             |

##### Dados de retorno
|     Campo      |   Tipo    | Obrigatório |                   Descrição                    |
|:--------------:|:---------:|:-----------:|:----------------------------------------------:|
| `id`         | `integer` |     Sim     |   Código identificador                           |
| `titulo`     | `string`  |     Sim     |   Titulo da pauta                                |
| `descricao`  | `string`  |     Sim     |   Descricao da pauta                             |
| `resultado`  | `string`  |     Sim     |   Resultado da pauta                             |
| `dataCriacao`| `date`    |     Sim     |   Data da criação da Pauta                       |
