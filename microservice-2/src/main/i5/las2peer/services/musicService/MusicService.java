package i5.las2peer.services.musicService;


import java.net.HttpURLConnection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import i5.las2peer.api.Context;
import i5.las2peer.api.ManualDeployment;
import i5.las2peer.api.ServiceException;
import i5.las2peer.api.logging.MonitoringEvent;
import i5.las2peer.restMapper.RESTService;
import i5.las2peer.restMapper.annotations.ServicePath;
import i5.las2peer.services.musicService.database.DatabaseManager;
import java.sql.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.json.simple.*;

import java.util.HashMap;
import java.util.Map;
 

/**
 *
 * 2
 *
 * This microservice was generated by the CAE (Community Application Editor). If you edit it, please
 * make sure to keep the general structure of the file and only add the body of the methods provided
 * in this main file. Private methods are also allowed, but any "deeper" functionality should be
 * outsourced to (imported) classes.
 *
 */
@ServicePath("music")
@ManualDeployment
public class MusicService extends RESTService {


  /*
   * Database configuration
   */
  private String jdbcDriverClassName;
  private String jdbcLogin;
  private String jdbcPass;
  private String jdbcUrl;
  private static DatabaseManager dbm;



  public MusicService() {
	super();
    // read and set properties values
    setFieldValues();
        // instantiate a database manager to handle database connection pooling and credentials
    dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl);
  }

  @Override
  public void initResources() {
	getResourceConfig().register(RootResource.class);
  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // REST methods
  // //////////////////////////////////////////////////////////////////////////////////////

  @Api
  @SwaggerDefinition(
      info = @Info(title = "2", version = "",
          description = "",
          termsOfService = "",
          contact = @Contact(name = "", email = "CAEAddress@gmail.com") ,
          license = @License(name = "BSD",
              url = "https://github.com/GHProjectsTest/microservice-2/blob/master/LICENSE.txt") ) )
  @Path("/")
  public static class RootResource {

    private final MusicService service = (MusicService) Context.getCurrent().getService();

      /**
   * 
   * postSong
   *
   * 
   * @param payload  a JSONObject
   * 
   * @return Response 
   * 
   */
  @POST
  @Path("/songs")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "response")
  })
  @ApiOperation(value = "postSong", notes = " ")
  public Response postSong(String payload) {
    JSONObject payload_JSON = (JSONObject) JSONValue.parse(payload);


    Connection connection;

    try {
        connection = dbm.getConnection();

        String songTitle = (String) payload_JSON.get("title");
        String songArtist = (String) payload_JSON.get("artist");
    
        PreparedStatement statement = connection.prepareStatement("INSERT INTO songs (title, artist) VALUES(?,?);");
        statement.setString(1, songTitle);
        statement.setString(2, songArtist);
        statement.executeUpdate();
        statement.close();

        return Response.ok("Success").build();
    } catch (SQLException e) {
        return Response.serverError().build();
    }
  }

  /**
   * 
   * getSongs
   *
   * 
   *
   * 
   * @return Response 
   * 
   */
  @GET
  @Path("/songs")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "response")
  })
  @ApiOperation(value = "getSongs", notes = " ")
  public Response getSongs() {


    Connection connection;

    try {
        connection = dbm.getConnection();
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM songs;");
        ResultSet result = statement.executeQuery();

        JSONArray a = new JSONArray();
        while(result.next()) {
            JSONObject songJson = new JSONObject();
            songJson.put("title", result.getString("title")); 
            songJson.put("artist", result.getString("artist"));
            a.add(songJson);
        }
        statement.close();
        return Response.ok(a.toJSONString()).build();
    } catch (SQLException e) {
        return Response.serverError().build();
    }
  }



  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // Service methods (for inter service calls)
  // //////////////////////////////////////////////////////////////////////////////////////
  
  

  // //////////////////////////////////////////////////////////////////////////////////////
  // Custom monitoring message descriptions (can be called via RMI)
  // //////////////////////////////////////////////////////////////////////////////////////

  public Map<String, String> getCustomMessageDescriptions() {
    Map<String, String> descriptions = new HashMap<>();
    
    return descriptions;
  }

}
