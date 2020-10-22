import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;
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


            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("html_file.html");
            StringBuilder sb = new StringBuilder();
            try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            String response = sb.toString();


            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class Handler79488975 implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            timer.set(120);
            try (FileWriter fw = new FileWriter("log.txt", true);
                 PrintWriter out = new PrintWriter(new BufferedWriter(fw))) {
                out.println("event at " + new Date());
            } catch (IOException ignored) {
            }
            String response = "";
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class Handler47450596 implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = String.valueOf(timer.get());
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}