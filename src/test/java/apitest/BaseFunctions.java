package apitest;

import io.cucumber.datatable.DataTable;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

 public abstract class BaseFunctions {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(BaseFunctions.class);
    private static final String configFilePath = "src/test/resources/config/config.json";
    private static final String TESTDATA_JSON = "src/test/resources/testdata/";
    private static String API_KEY = "";
    private static String HOSTNAME = "";
    private static String SCHEME = "";
    private static String PATH = "";
    private static String cityName;
    private static String httpResponse;
    private static int statusCode;

    public void setEnvironment(String env) throws Exception {

        // Load json from file
        logger.info("setEnvironment is running");
        File f = new File(configFilePath);
        InputStream is = new FileInputStream(f);
        String jsonTxt = IOUtils.toString(is, "UTF-8");
        // Parsing file to Json object
        JSONObject jsonObj = new JSONObject(jsonTxt);

        // Get environment
        JSONObject envObj = (JSONObject) jsonObj.get(env);
        SCHEME = envObj.getString("scheme");
        HOSTNAME = envObj.getString("hostname");
        PATH = envObj.getString("path");
        API_KEY = envObj.getString("appID");
    }

    public void setCityName(String name){
        logger.info("setCityName is running");
        this.cityName = name;
    }

    public void useGetMethodWithCityName() throws Exception{
        logger.info("useGetMethodWithCityName is running");
        URI uri = new URIBuilder()
                .setScheme(SCHEME)
                .setHost(HOSTNAME)
                .setPath(PATH)
                .setParameter("q", this.cityName)
                .setParameter("appid", this.API_KEY)
                .build();
        HttpGet request = new HttpGet(uri);

        try (CloseableHttpClient client = HttpClients.createDefault();CloseableHttpResponse response = client.execute(request)) {
            this.statusCode = response.getCode();
            HttpEntity entity = response.getEntity();
            String getResponse = EntityUtils.toString(entity);
            this.httpResponse = getResponse;
            logger.debug(getResponse);
        }
    }

    public void responseCodeIs(int statusCode) throws Exception{
        logger.info("responseCodeIs is running");
        Assert.assertEquals(statusCode, this.statusCode);
    }

    public void resBodyIsCorrectAsSaveObject() throws Exception {
        logger.info("resBodyIsCorrectAsSaveObject is running");
        String sourceObj = TESTDATA_JSON + this.cityName + ".json";
        //String jsonString = this.httpResponse.asString();
        String jsonString = this.httpResponse;

        JSONObject jsonRes = new JSONObject(jsonString);
        // Assert cod return is 200
        int codRes = jsonRes.getInt("cod");
        Assert.assertEquals(200, codRes);

        // Assert city name
        String nameCityRes = jsonRes.getString("name");
        Assert.assertTrue(this.cityName.equals(nameCityRes));

        // Load json from file
        File f = new File(sourceObj);
        InputStream is = new FileInputStream(f);
        String jsonTxt = IOUtils.toString(is, "UTF-8");
        // Parsing file to Json object
        JSONObject jsonObj = new JSONObject(jsonTxt);

        // Get longitude from file
        JSONObject coordObj = (JSONObject) jsonObj.get("coord");
        double lonObj = coordObj.getDouble("lon");

        // Get latitude from file
        double latObj = coordObj.getDouble("lat");

        // Get timezone from file
        int timezoneObj = jsonObj.getInt("timezone");

        // Get id from file
        int idObj = jsonObj.getInt("id");

        //Get sys.country from file
        JSONObject sysObj = (JSONObject) jsonObj.get("sys");
        String countryObj = sysObj.getString("country");

        //Assert longitude and latitude
        JSONObject coordRes = (JSONObject) jsonRes.get("coord");
        double lonRes = coordRes.getDouble("lon");
        double latRes = coordRes.getDouble("lat");

        Assert.assertEquals(lonObj, lonRes, 0);
        Assert.assertEquals(latObj, latRes, 0);

        // Assert timezone
        int timezoneRes = jsonRes.getInt("timezone");
        Assert.assertTrue(timezoneObj == timezoneRes);

        // Assert id
        int idRes = jsonRes.getInt("id");
        Assert.assertEquals(idObj, idRes);

        // Assert country
        JSONObject sysRes = (JSONObject) jsonRes.get("sys");
        String countryRes = sysRes.getString("country");
        Assert.assertTrue(countryObj.equals(countryRes));

        // Assert weather
        JSONArray weather = (JSONArray) jsonRes.get("weather");
        JSONObject weather0 = (JSONObject) weather.get(0);
        int idWeatherRes = (int) weather0.get("id");
        String mainRes = (String) weather0.get("main");
        String descRes = (String) weather0.get("description");

        Assert.assertTrue(idWeatherRes >= 0);
        Assert.assertTrue(mainRes.length() >= 0);
        Assert.assertTrue(descRes.length() >= 0);
    }

    public void responseBodyIsCorrectAsTable(DataTable dt) throws Exception{
        logger.info("responseBodyIsCorrectAsTable is running");
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);

        String jsonString = this.httpResponse;
        JSONObject jsonRes = new JSONObject(jsonString);

        // Assert response code in body
        int codRes = jsonRes.getInt("cod");
        int codTbl = Integer.parseInt(list.get(0).get("cod"));
        Assert.assertTrue(codRes == codTbl);

        // Assert city name in body
        String nameCityTbl = list.get(0).get("name");
        String nameCityRes = jsonRes.getString("name");
        Assert.assertTrue(nameCityTbl.equals(nameCityRes));

        // Assert country in body
        String countryTbl = list.get(0).get("country");
        JSONObject sysRes = (JSONObject) jsonRes.get("sys");
        String countryRes = sysRes.getString("country");
        Assert.assertTrue(countryTbl.equals(countryRes));

        // Assert longitude, latitude in body
        double lonTbl = Double.parseDouble(list.get(0).get("longitude"));
        double latTbl = Double.parseDouble(list.get(0).get("latitude"));
        JSONObject coordRes = (JSONObject) jsonRes.get("coord");
        double lonRes = coordRes.getDouble("lon");
        double latRes = coordRes.getDouble("lat");
        Assert.assertEquals(lonTbl,lonRes,0);
        Assert.assertEquals(latTbl,latRes,0);
    }
}
