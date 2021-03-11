#language: pt
@AllCenarios
Funcionalidade: Contatar Atendimento ao Cliente
  Como cliente de uma loja virtual
  Eu desejo preencher o formulário de atendimento
  Para contatar o setor de Atendimento ao Cliente

  Contexto: 
    Dado que desejo submeter o formulário de contato

  Cenário: Submeter formulário de contato com sucesso
    Quando eu informo <Subject Heading>
    E eu informo <Email address>
    E eu informo <Order reference>
    E eu informo <Message>
    E eu seleciono botão Send
    Então eu devo ver a mensagem "Your message has been successfully sent to our team."

  Cenário: Submeter formulário de contato sem informar Email
    Quando eu informo <Subject Heading>
    E eu informo <Order reference>
    E eu informo <Message>
    E eu seleciono botão Send
    Então eu devo ver a mensagem de erro para email "Invalid email address."
