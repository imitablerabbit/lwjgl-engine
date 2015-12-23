package info.markhillman.Loaders;

import info.markhillman.Models.Model;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: ModelLoader
 * Description: Load a model from a file into a Model
 * object, including UV's and normals
 * Created by Mark on 16/12/2015.
 */
public class ModelLoader {

    public ModelLoader() {

    }

    //Load a model from an OBJ file
    public Model loadOBJModel(String path) {

        Model model = new Model();
        List<Vector3f> vertices = new ArrayList<>(0);
        List<Vector3f> normals = new ArrayList<>(0);
        List<Vector2f> uvs = new ArrayList<>(0);
        List<Integer> vertexIndices = new ArrayList<>(0);
        List<Integer> normalsIndices = new ArrayList<>(0);
        List<Integer> uvIndices = new ArrayList<>(0);

        //Read from the OBJ file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = "";
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {

                //Split the words to finds the first section
                String[] words = line.split(" ");

                //This is a comment so ignore it
                if (words[0].equals("#")) {}

                //This is a vertex
                else if (words[0].equals("v")) {
                    if (words.length == 4) {
                        //Add each of the components to the array
                        Vector3f vertex = new Vector3f(
                                Float.parseFloat(words[1]),
                                Float.parseFloat(words[2]),
                                Float.parseFloat(words[3])
                        );
                        vertices.add(vertex);
                    }
                    else {
                        System.out.println("The vertex at line " + lineNumber + " does not have 3 components");
                        break;
                    }
                }

                //This is UV coordonate
                else if (words[0].equals("vt")) {
                    if (words.length == 3) {

                        //Add the uv components to a vector
                        Vector2f uv = new Vector2f(
                                Float.parseFloat(words[1]),
                                Float.parseFloat(words[2])
                        );
                        uvs.add(uv);
                    }
                    else {
                        System.out.println("The UV at line " + lineNumber + "does not have 2 components");
                    }
                }

                //This is the normal
                else if (words[0].equals("vn")) {
                    if (words.length == 4) {
                        //Add each of the components to the array
                        Vector3f normal = new Vector3f(
                                Float.parseFloat(words[1]),
                                Float.parseFloat(words[2]),
                                Float.parseFloat(words[3])
                        );
                        normals.add(normal);
                    }
                    else {
                        System.out.println("The normal at line " + lineNumber + " does not have 3 components");
                        break;
                    }
                }

                //This is the face so we can create the vertexIndices
                else if (words[0].equals("f")) {
                    if (words.length == 4) {

                        //Split each face vertex into its components
                        for (int i = 0; i < 3; i++) {
                            String[] components = words[1 + i].split("/");
                            if (!components[0].equals("")) {
                                int vertexID = Integer.parseInt(components[0]);
                                vertexIndices.add(vertexID);
                            }
                            if (!components[1].equals("")) {
                                int uvID = Integer.parseInt(components[1]);
                                uvIndices.add(uvID);
                            }
                            if (!components[2].equals("")) {
                                int normalID = Integer.parseInt(components[2]);
                                normalsIndices.add(normalID);
                            }
                        }
                    }
                    else {
                        System.out.println("The face at line " + lineNumber + " does not hold the correct information");
                    }
                }

                lineNumber++;
            }

            List<Float> verticesTemp = new ArrayList<>(0);
            List<Float> normalsTemp = new ArrayList<>(0);
            List<Float> uvsTemp = new ArrayList<>(0);

            //Assemble the vertices for the model
            for (int i = 0; i < vertexIndices.size(); i++) {

                //Get the indices of its attributes
                if (vertexIndices.size() > 0) {

                    int index;

                    //Get the vertex
                    if (uvs.size() > 0) {
                        index = vertexIndices.get(i);
                        verticesTemp.add(vertices.get(index - 1).x);
                        verticesTemp.add(vertices.get(index - 1).y);
                        verticesTemp.add(vertices.get(index - 1).z);
                    }

                    //Get the normal
                    if (uvs.size() > 0) {
                        index = uvIndices.get(i);
                        uvsTemp.add(uvs.get(index - 1).x);
                        uvsTemp.add(uvs.get(index - 1).y);
                    }

                    //Get the normal
                    if (normals.size() > 0) {
                        index = normalsIndices.get(i);
                        normalsTemp.add(normals.get(index - 1).x);
                        normalsTemp.add(normals.get(index - 1).y);
                        normalsTemp.add(normals.get(index - 1).z);
                    }
                }
            }

            model = new Model(verticesTemp, normalsTemp, uvsTemp);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }
}
