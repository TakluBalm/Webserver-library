package server;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.exceptions.InvalidResourceTypeException;

import javax.swing.text.html.HTML;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HTMLResponseTest {
//    @Test
    public void startServer() throws Exception{
        Server server = new Server();
        server.start();
        while(true){}
    }

    @Test
    public void testHTMLbody() throws IOException {
        HTMLResponse r = new HTMLResponse("1.1", "src/test/resources/test_1.html");
        try {
            FileOutputStream byteout = new FileOutputStream("src/test/resources/test_1_response.html");
            byte[] body = r.getBody();
            byteout.write(body);
            byteout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] f1 = Files.readAllBytes(Paths.get("src/test/resources/test_1.html"));
        byte[] f2 = Files.readAllBytes(Paths.get("src/test/resources/test_1_response.html"));
        assertEquals(f1.length, f2.length);
        for(int i = 0; i < f1.length; i++){
            assertEquals(f1[i], f2[i]);
        }
    }

    @Test
    public void testTextEmbedding() throws Exception {
        HTMLResponse r = new HTMLResponse("1.1", "src/test/resources/test_1_response.html");
        HashMap<String, Resource> mp = new HashMap<>();
        mp.put("url", new Resource("text", "https://www.google.com"));
        mp.put("txt", new Resource("text", "Tanuj is not gay?"));
        r.embedData(mp);
    }

    @Test
    public void testImageEmbedding() throws Exception{
        HTMLResponse r = new HTMLResponse("1.1","src/test/resources/testImgEmb.html");
        HashMap<String,Resource> mp=new HashMap<>();
        mp.put("This is img",new Resource("image","src/test/resources/test-img.jpeg"));
        r.embedData(mp);
    }

}