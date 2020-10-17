import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {

    private static final Timer timer = Timer.getInstance();


    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/31421911", new Handler31421911());
        server.createContext("/79488975", new Handler79488975());
        server.createContext("/47450596", new Handler47450596());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        new Thread(timer).start();
    }

    static class Handler31421911 implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            timer.set(120);
            String response = "<html><head><link rel='icon' href='data:,'><style>p {text-align: center; font-size: 10vw;} </style></head><body>" +
                    "<p>La tua richiesta &egrave; stata ricevuta.</p>" +
                    "</body>" +
                    "<script>" +
                    "function func() {\n" +
                    "window.location.href='79488975';\n}\n" +
                    "setTimeout(func, 2000);" +
                    "</script>" +
                    "</html>";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class Handler79488975 implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            String response = "<html><head><link rel='icon' href='data:,'><style>p {text-align: center; font-size: 10vw;}</style></head><body>" +
                    "<p id='3647'></p>" +
                    "</body>" +
                    "<script>" +
                    "function func() {\n" +
                    "var xhttp = new XMLHttpRequest();\n" +
                    "  xhttp.onreadystatechange = function() {\n" +
                    "    if (this.readyState == 4 && this.status == 200) {\n" +
                    "      document.getElementById(\"3647\").innerHTML = this.responseText; \n" +
                    "    }\n" +
                    "  };\n" +
                    "  xhttp.open(\"GET\", \"47450596\", true);\n" +
                    "  xhttp.send();} setInterval(func, 4900);" +
                    "</script>" +
                    "</html>";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class Handler47450596 implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            int timeLeft = timer.get();
            String response;
            if (timeLeft > 0) {
                response = "Ancora " + timeLeft + " secondi rimanenti.";
            } else {
                response = "Situazione come da quadro.";
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}