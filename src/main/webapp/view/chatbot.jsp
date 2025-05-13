<meta charset="UTF-8">
<%@ page import="br.com.fatec.goldenfit.model.Cliente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url value="/controlador" var="pesquisa"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ChatGolden</title>
    <style>
        #chatbox {
            width: 400px;
            height: 300px;
            border: 1px solid #ccc;
            overflow-y: scroll;
        }
    </style>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/he@1.2.0/he.js"></script>
</head>
<body onload="sendInitialMessage()">
<nav class="navbar" style="background-color: #173B21; height: 60px ">
    <div class="d-flex flex-row mb-3"
         style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="images/LogoGold.png" style="height: 35px; width: 35px">
            <a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 15px ">GOLDENFIT</a>
        </div>
        <div class="d-flex flex-row mb-3"
             style="justify-content: center; align-items: center; margin-left: 15%; margin-right: 5%">
            <form id="formConsultaProdutos" class="d-flex" action="${pesquisa}" method="GET" novalidate>
                <input class="form-control me-2" type="search" id="nome" name="nome" placeholder="O que está buscando?" aria-label="Search" style="height: 25px; width: 550px">
                <button class="btn btn-success" type="submit" style="height: 25px; width: 50px; font-size: 12px; position: relative;">
                    <img style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); margin-top: -2%;" src="images/search.svg">
                </button>
                <input type="hidden" name="acao" value="consultar"/>
                <input type="hidden" name="viewHelper" value="BuscarProdutosVH"/>
                <input type="hidden" name="tipoPesquisa" value="filtros"/>
            </form>
        </div>
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="images/user.png" style="height: 30px; width: 30px; margin-left: 30px">
            <%
                Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
                if (cliente != null) {
                    String nome = cliente.getNome();
            %>

            <div class="btn-group">
                <button style="color:#ffffff" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown"
                        aria-expanded="false">
                    Olá, ${sessionScope.clienteLogado.nome}
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="listaClientes.jsp">Meu perfil</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCartaoVH">Meus
                        cartões</a></li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarEnderecoVH">Endereços</a>
                    </li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">Meus
                        pedidos</a></li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsVH">Meus cupons</a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=Logout">Logout</a></li>
                </ul>
            </div>
            <%
            } else {
            %>
            <a class="navbar-brand" href="login.jsp" style="color:#ffffff ; font-size: 12px; margin-left: 20px">Entre ou
                cadastre-se</a>
            <%
                }
            %>
            <a href="carrinho.jsp">
                <img src="images/shopping-bag.png" style="height: 30px; width: 30px; margin-left: 50px">
            </a>
        </div>
    </div>
</nav>
<div style="background-color: #ECBD69; height: 5px;"></div>

<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-bottom:2%">CHATGOLDEN</h1>
    <div style="background-color: #173B21; height: 5px; margin-bottom: 3%"></div>
    <div class="card-body d-flex flex-column justify-content-center align-items-center">
        <h5 class="text-center mb-4">Obtenha respostas para suas dúvidas sobre moda e estilo com o ChatGolden da nossa empresa.</h5>

        <div id="chatbox" style="width: 70%; height: 400px; overflow-y: auto; margin-bottom: 3%"></div>

        <div class="row">
            <div class="col-10">
                <input type="text" id="userInput" class="form-control mb-3" style="width: 245%; margin-left: -85%" placeholder="Digite sua mensagem...">
            </div>
            <div class="col-2">
                <button onclick="sendMessage()" class="btn btn-primary" style="margin-left: 470%; background-color: #173B21; border-color: #173B21">Enviar</button>
            </div>
        </div>

        <div id="suggestionsDiv" class="row mt-3" style="display: none;">
            <div class="col-12">
                <p>Sugestões de perguntas:</p>
                <button class="btn btn-outline-success mx-1" onclick="suggestionClicked('Quais são as principais tendências de roupas na moda atual?')">Tendências de moda</button>
                <button class="btn btn-outline-success mx-1" onclick="suggestionClicked('Quais peças de roupa você recomenda para compor um look casual na moda atual?')">Recomendação de Look Casual</button>
                <button class="btn btn-outline-success mx-1" onclick="suggestionClicked('Quais são as cores em alta na moda nesta temporada?')">Quais são as cores da moda nesta temporada</button>
            </div>
        </div>
    </div>
</div>

<script>
    var userResponses = [];
    var questionsAsked = [];
    var initialMessageSent = false;
    var finalMessage = false;
    var currentQuestion = "";
    var q = 0;

    var newQuestion = "formule uma pergunta simples sobre roupa e estilo de roupas, sem repetir perguntas iguais ou do mesmo sentido dessas: " + questionsAsked;

    function sendInitialMessage() {
        if (!initialMessageSent) {
            var chatbox = document.getElementById("chatbox");
            var div = document.createElement("div");
            chatbox.appendChild(div);
            initialMessageSent = true;
            sendQuestion(newQuestion, chatbox);
        }
    }

    function suggestionClicked(message) {
        document.getElementById("userInput").value = message;
        sendMessage();
    }

    function sendMessage() {
        var message = document.getElementById("userInput").value;
        var chatbox = document.getElementById("chatbox");
        var div = document.createElement("div");
        div.style.marginBottom = "10px";

        div.innerHTML = "<strong><span style='color: #173B21'>Você:</span></strong> " + message;
        chatbox.appendChild(div);

        userResponses.push(message);

        if (q < 5) {
            var fullMessage = message + ". Isso está relacionado à " + currentQuestion + " Se sim, por favor, responda 'yes'. Caso contrario, responda 'no'.";
            sendQuestion(fullMessage, chatbox);
        } else {
            sendQuestion(message + ". Essa pergunta tem haver especificamente com roupa e estilo de roupa? Caso contrário, retorne sempre: Sua pergunta deve ser referente a roupa ou estilos de roupa");
        }

        document.getElementById("userInput").value = "";
    }

    function sendQuestion(question, chatbox) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "ChatbotController", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var response = xhr.responseText;
                console.log("Resposta do servidor: " + response);

                // Exibir a resposta apenas se não for "Yes" ou "No"
                if ((response.trim().toLowerCase() !== "yes" && response.trim().toLowerCase() !== "no")
                    && (response.trim().toLowerCase() !== "yes." && response.trim().toLowerCase() !== "no.")) {
                    displayBotResponse(response);
                }

                // Gerar nova pergunta apenas se a resposta for "Yes"
                if (response.trim().toLowerCase() === "yes" || response.trim().toLowerCase() === "yes.") {
                    q++;
                    if (q < 5) {
                        questionsAsked.push(newQuestion);

                        sendQuestion(newQuestion, chatbox);
                    }
                } else if (response.trim().toLowerCase() === "no" || response.trim().toLowerCase() === "no.") {
                    // Repetir a mesma pergunta se a resposta for "No"
                    displayBotResponse("Tente novamente: " + currentQuestion)
                }

                // Verificar se q atingiu 5 após a resposta do servidor
                if (q === 5 && !finalMessage) {
                    finalMessage = true;
                    sendQuestion("Com base em todas as respostas do usuário: " + userResponses.join("; ") + ". Retorne uma dica envolvendo as respostas, adicione uma dica de roupas que posso vir a comprar com base nas respostas, e informe que Se tiver alguma dúvida ou precisar de mais sugestões, estou aqui para ajudar!", chatbox);
                }
            }
        };
        xhr.send("question=" + encodeURIComponent(question));
    }

    function displayBotResponse(response) {
        var chatbox = document.getElementById("chatbox");
        var botDiv = document.createElement("div");
        botDiv.innerHTML = "<strong><span style='color: #ECBD69'>ChatGolden:</span></strong> " + "<div style='text-align: justify; padding: 5px; border-left: 2px solid #ECBD69; border-right: 2px solid #ECBD69;'>" + response + "</div>"; // Resposta justificada com bordas
        botDiv.style.marginBottom = "20px";
        chatbox.appendChild(botDiv);

        currentQuestion = response;
    }

    setInterval(function() {
        if (q === 5 && finalMessage) {
            // Exibir sugestões de perguntas
            var suggestionsDiv = document.getElementById("suggestionsDiv");
            suggestionsDiv.style.display = "block"; // Tornar as sugestões visíveis
        }
    }, 1000); // Verificar a cada segundo

</script>
</body>
</html>
