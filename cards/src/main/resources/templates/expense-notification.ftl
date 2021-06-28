<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <title>Nova despesa</title>
</head>
<body>
<div style="margin-top: 10px">Olá, ${transaction.card.customer.fullName}!</div>
<div>
    <span>Você teve uma nova despesa.</span>
    <ul>
        <li>${transaction.description}</li>
        <li>Valor: <b>R$ ${(transaction.amount)?string["#,##0.00"]}</b></li>
    </ul>
</div>
<div>Tenha um bom dia!</div>
<div><small>Se você não reconhece essa despesa, entre em contato conosco imediatamente.</small></div>
</body>
</html>