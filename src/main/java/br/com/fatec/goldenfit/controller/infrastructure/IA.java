package br.com.fatec.goldenfit.controller.infrastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class IA {
    private static final Logger logger = Logger.getLogger(IA.class.getName());


    public static String obterResposta(String pergunta) {
        if (pergunta == null || pergunta.trim().isEmpty()) {
            System.out.println(pergunta);
            return "Por favor, forneça uma pergunta válida.";
        }

        // Carrega as configurações do arquivo .env
        Properties props = new Properties();
        try {
            // Imprime o diretório atual
            FileInputStream fis = new FileInputStream("/home/vinicius/Documentos/GitHub/GoldenFit/.env");
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao carregar arquivo .env";
        }

        String apiKey = props.getProperty("CHATGPT_KEY");
        String link = "https://api.openai.com/v1/chat/completions";
        String idModelo = "gpt-3.5-turbo";

        // Mensagem do chatbot
        Map<String, Object> mensagemChatbot = new HashMap<>();
        mensagemChatbot.put("role", "assistant");
        mensagemChatbot.put("content", "");

        // Mensagem do usuário
        Map<String, Object> mensagemUsuario = new HashMap<>();
        mensagemUsuario.put("role", "user");
        mensagemUsuario.put("content", pergunta);

        // Lista de mensagens
        ArrayList<Map<String, Object>> messages = new ArrayList<>();
        messages.add(mensagemUsuario);
        messages.add(mensagemChatbot);

        // Corpo da solicitação
        Map<String, Object> body = new HashMap<>();
        body.put("model", idModelo);
        body.put("messages", messages);

        Gson gson = new Gson();
        String bodyMensagem = gson.toJson(body);

        // Cabeçalhos da solicitação
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + apiKey);
        headers.put("Content-Type", "application/json");

        HttpURLConnection connection = null;
        try {
            logger.info("Antes de fazer a solicitação para a API da OpenAI");
            logger.info("URL da solicitação: " + link);
            logger.info("Corpo da solicitação: " + bodyMensagem);

            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = bodyMensagem.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            logger.info("Após enviar a solicitação para a API da OpenAI");

            try (Scanner scanner = new Scanner(connection.getInputStream(), "utf-8")) {
                String responseBody = scanner.useDelimiter("\\A").next();
                logger.info("Resposta recebida da API da OpenAI: " + responseBody);

                Map<String, Object> resposta = gson.fromJson(responseBody, Map.class);
                ArrayList<Map<String, Object>> choices = (ArrayList<Map<String, Object>>) resposta.get("choices");
                for (Map<String, Object> choice : choices) {
                    String mensagemResposta = ((Map<String, Object>) choice.get("message")).get("content").toString();
                    logger.info("Mensagem de resposta processada: " + mensagemResposta);
                    return mensagemResposta;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao fazer solicitação para a API da OpenAI", e);
            if (connection != null) {
                try (Scanner errorScanner = new Scanner(connection.getErrorStream(), "utf-8")) {
                    String errorResponse = errorScanner.useDelimiter("\\A").next();
                    logger.log(Level.SEVERE, "Resposta de erro da API da OpenAI: " + errorResponse);
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Erro ao processar resposta de erro da API da OpenAI", ex);
                }
            }
        }
        return "Desculpe, não foi possível obter uma resposta no momento.";
    }
}
