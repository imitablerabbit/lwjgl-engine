package info.markhillman.Loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

/**
 * Class: Shader
 * Description: This will read from the shader files
 * and then compile the shader files. it will print out
 * any error messages from compiling. The class will
 * automatically compile the program and the ID can be
 * gotten using the getProgramID function.
 * Created by Mark on 05/12/2015.
 */
public class ShaderLoader {

    private String fragmentPath;
    private String vertexPath;
    private int vertexShaderID;
    private int fragmentShaderID;
    private int programID;

    public ShaderLoader(String vertexPath, String fragmentPath) {

        this.fragmentPath = fragmentPath;
        this.vertexPath = vertexPath;
        compile();
    }

    //Compile both the shaders
    public int compile() {
        vertexShaderID = loadShader(vertexPath, GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentPath, GL_FRAGMENT_SHADER);
        programID = createProgram();
        return programID;
    }

    //This will read and compile a shader, sheck if it is valid
    //Then return the shader id
    private int loadShader(String shaderPath, int type) {

        int shaderID = 0;
        String shaderSource = "";

        //Read the shader files into memory
        try {
            BufferedReader reader = new BufferedReader(new FileReader(shaderPath));
            String line = "";
            while((line = reader.readLine()) != null) {
                shaderSource += (line + "\n");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not find the shader at " + shaderPath);
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Could not read from the shader");
            e.printStackTrace();
        }

        if (!shaderSource.equals("")) {

            //Create the shader
            shaderID = glCreateShader(type);
            glShaderSource(shaderID, shaderSource);
            glCompileShader(shaderID);

            //Check the shader compilation errors
            if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
                System.out.println("Could not compile the shader");
                System.out.println(glGetShaderInfoLog(shaderID));
                return 0;
            }
        }

        return shaderID;
    }

    //Create the shader program using the vertex and fragament shaders
    private int createProgram() {

        int programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        glLinkProgram(programID);
        return programID;
    }

    //Getters and Setters
    public String getFragmentPath() {
        return fragmentPath;
    }
    public void setFragmentPath(String fragmentPath) {
        this.fragmentPath = fragmentPath;
    }
    public String getVertexPath() {
        return vertexPath;
    }
    public void setVertexPath(String vertexPath) {
        this.vertexPath = vertexPath;
    }
    public int getProgramID() {
        return programID;
    }

    //Load the shader programs statically
    public static int loadShaders(String vertexPath, String fragmentPath) {

        return new ShaderLoader(fragmentPath, vertexPath).getProgramID();
    }
}
