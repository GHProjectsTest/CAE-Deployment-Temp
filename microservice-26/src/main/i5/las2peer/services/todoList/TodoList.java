package i5.las2peer.services.todoList;


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
import i5.las2peer.services.todoList.database.DatabaseManager;
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
import java.sql.Connection;
import java.sql.SQLException; 
import java.sql.PreparedStatement;
import java.sql.ResultSet; 

/**
 *
 * 26
 *
 * This microservice was generated by the CAE (Community Application Editor). If you edit it, please
 * make sure to keep the general structure of the file and only add the body of the methods provided
 * in this main file. Private methods are also allowed, but any "deeper" functionality should be
 * outsourced to (imported) classes.
 *
 */
@ServicePath("todolist")
@ManualDeployment
public class TodoList extends RESTService {


  /*
   * Database configuration
   */
  private String jdbcDriverClassName;
  private String jdbcLogin;
  private String jdbcPass;
  private String jdbcUrl;
  private static DatabaseManager dbm;



  public TodoList() {
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
      info = @Info(title = "26", version = "$Metadata_Version$",
          description = "$Metadata_Description$",
          termsOfService = "$Metadata_Terms$",
          contact = @Contact(name = "", email = "CAEAddress@gmail.com") ,
          license = @License(name = "BSD",
              url = "https://github.com/GHProjectsTest/microservice-26/blob/master/LICENSE.txt") ) )
  @Path("/")
  public static class RootResource {

    private final TodoList service = (TodoList) Context.getCurrent().getService();

      /**
   * 
   * postTodo
   *
   * 
   * @param payload  a JSONObject
   * 
   * @return Response 
   * 
   */
  @POST
  @Path("/todo")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "response")
  })
  @ApiOperation(value = "postTodo", notes = " ")
  public Response postTodo(String payload) {
    JSONObject payload_JSON = (JSONObject) JSONValue.parse(payload);




     
    // service method invocations

     




    // response
    boolean response_condition = true;
    if(response_condition) {
      JSONObject result = new JSONObject();

      

      return Response.status(HttpURLConnection.HTTP_OK).entity(result.toJSONString()).build();
    }
    return null;
  }

  /**
   * 
   * getTodos
   *
   * 
   *
   * 
   * @return Response 
   * 
   */
  @GET
  @Path("/todo")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "response")
  })
  @ApiOperation(value = "getTodos", notes = " ")
  public Response getTodos() {




     
    // service method invocations
    JSONArray a = new JSONArray();
    try { 
        Connection c = dbm.getConnection(); 
 
        PreparedStatement s = c.prepareStatement("SELECT * FROM tab;"); 
 
        ResultSet queryResult = s.executeQuery();  
        while(queryResult.next()) {  
            a.add(queryResult.getString("item"));
        }        

    } catch(SQLException e) { 
        e.printStackTrace();
    }

    return Response.status(HttpURLConnection.HTTP_OK).entity(a.toJSONString()).build();
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
