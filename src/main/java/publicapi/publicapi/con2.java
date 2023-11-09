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

                br.readLine();
                String returnLine;

                while ((returnLine = br.readLine()) != null) {
                    result.append(returnLine);
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
                String[] jsonKeysForDouble = {"clciQy", "naQy", "dryMatter", "ashsQy", "crfbQy", "totEdblfibrQy", "vtmaQy", "ptssQy", "mitrQy", "slwtEdblfibrQy", "liacQy", "fatQy", "lnacQy", "trypQy", "crbQy", "phphQy", "protQy", "inslbltyEdblfibrQy"};
                String[] jsonKeysForInt = {"mtralPc", "feedSeqNo", "feedClCode", "upperFeedClCode"};
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    for (int j = 0; j < jsonKeysForDouble.length; j++) {
                        if (!jsonObject.getString(jsonKeysForDouble[j]).equals("")) {
                            double JsonValue = Double.parseDouble(jsonObject.getString(jsonKeysForDouble[j]));
                            jsonObject.put(jsonKeysForDouble[j], JsonValue);
                        }
                    }

                    for (int j = 0; j < jsonKeysForInt.length; j++) {
                        if (!jsonObject.getString(jsonKeysForInt[j]).equals("")) {
                            int JsonValue = Integer.parseInt(jsonObject.getString(jsonKeysForInt[j]));
                            jsonObject.put(jsonKeysForInt[j], JsonValue);
                        }
                    }


                    System.out.println("데이터 " + i + " " + jsonObject.toString());
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
