package publicapi.publicapi;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class con2 {

    @RestController
    public class RestTestController {

        @RequestMapping(value = "/apitest", method = {RequestMethod.GET, RequestMethod.POST})
        public String callapihttp() {

            StringBuffer result = new StringBuffer();
            try {
                String urlstr = "http://api.nongsaro.go.kr/service/feedRawMaterial/feedRawMaterialAllList?" +
                        "apiKey=20231004MDDAYVB8GN7GYJDSQN2VTQ" +
                        "&type=json";
                URL url = new URL(urlstr);
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestMethod("GET");

                BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));

                System.out.println("허허?" + br.readLine());

                String returnLine;

                while ((returnLine = br.readLine()) != null) {
                    result.append(returnLine);
                    System.out.println("이거임?" + br.readLine());
                }

                String StringifyResult = result.toString();
                XmlMapper xmlMapper = new XmlMapper();
                JsonNode node = xmlMapper.readTree(StringifyResult.getBytes());

                ObjectMapper jsonMapper = new ObjectMapper();
                String json = jsonMapper.writeValueAsString(node);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(json);
                JsonNode itemArrayNode = rootNode.path("body").path("items").path("item");


                JSONArray jsonArray  = new JSONArray(itemArrayNode.toString());

                for (Object o : jsonArray) {
                    System.out.println("ㄷ ㄷ " + o.toString());
                    System.out.println();
                }


                urlconnection.disconnect();

                return json;

            } catch (Exception e) {
                e.printStackTrace();
            }


            return result.toString();
        }
    }
}
