/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleserver;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author anu
 */
public class ResponseFactory 
{   
    static String baseFilepath = "src/public_files/";
    public ResponseFactory ()
    {
        
    }

    public static Response getResponse(String resource) throws IOException {
            return responseAfterWebServerFlow (resource);
    }

    private static Response responseAfterWebServerFlow (String resource) throws IOException
    {
        Response r = new Response();
        r.putHeaders("Content-type", "text/html");
        r.putHeaders("Connection", "close");
        try
        {
            if(resource == null)
            {
                
                String filepath= baseFilepath+ "index.html";
                File f = new File(filepath);
                if(f.exists())
                {
                 
                    r.getFileContent(filepath);  
                    r.generateResponseString();
                    return r;
                }
               
            }
            else
            {
                if(resource.contains(".")){
                String extension = resource.split("\\.")[1];
               
                        String filepath= baseFilepath+ resource;
                        File f = new File(filepath);
                        if(f.exists())
                        {
                             if((extension.equals("png"))||(extension.equals("jpg"))||(extension.equals("jpeg")))
                             {
                                  r.putHeaders("Content-type", "image/"+extension);
                             }
                             else if(extension.equals("zip"))
                             {
                                  r.putHeaders("Content-type", "application/"+extension);
                             }
                           
                            r.getFileContent(filepath); 
                            r.generateResponseString();
                            return r;
                        }
                }
                        
            }

        }
       
        catch(Exception ex)
        {
            return new Response();
        }
        r.setMessage("<p>404 - FILE NOT FOUND  </p>");
        r.generateResponseString();
        return r;
    }
    
}