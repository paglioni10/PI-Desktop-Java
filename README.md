# Mão na Roda - aplicação desktop

Marketplace de contratação de prestadores de serviços. Esta é a aplicação Java Desktop
da Avaliação 2 de Prática de Linguagem de Programação I, construída sobre a modelagem de
classes entregue no Projeto Integrador.

Curso de Análise e Desenvolvimento de Sistemas - FMP.

## Integrantes

- Henrique Fernandes dos Santos
- Fabio Henrique Thieres Cardoso Santana
- Nicolas Pietro Paglioni

## Como executar

Abra a pasta do projeto no NetBeans (Arquivo > Abrir Projeto) e rode com F6.
O projeto usa Java 8 e não precisa de nenhuma instalação além do próprio NetBeans:
o driver do SQLite já vai junto, na pasta `lib`.

Na primeira execução o programa cria o arquivo `banco/maonaroda.db` a partir do
`banco/script.sql`, já com dados de exemplo. Para voltar tudo ao estado inicial,
basta apagar o `maonaroda.db` e rodar de novo.

## Usuários de teste

| E-mail | Senha | Perfil |
|---|---|---|
| admin@maonaroda.com | 123 | Administrador |
| maria@email.com | 123 | Cliente |
| joao@email.com | 123 | Cliente |
| carlos@email.com | 123 | Prestador verificado |
| ana@email.com | 123 | Prestadora verificada |
| pedro@email.com | 123 | Prestador ainda não verificado |

O Pedro não consegue entrar de propósito: a classe `Prestador` reescreve o método
`autenticar()` para exigir que a moderação tenha verificado o cadastro.

## Estrutura

```
src/Model        entidades do domínio e enumerações
src/Model/DAO    conexão com o banco e operações de leitura e gravação
src/Controller   regras de tela e validações
src/View         telas Swing
banco            script de criação e banco SQLite gerado
lib              driver JDBC do SQLite
```

## Tecnologias

Java 8, Swing, SQLite com driver JDBC Xerial e padrão MVC com camada DAO.
