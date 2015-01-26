package code.part1kl.keyworld.utils;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Shader {
	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	private int geometryShaderId;
	
	private String shaderName;
	private boolean hasGeometry;
	
	private HashMap<String, Integer> uniforms = new HashMap<String, Integer>();
	
	public Shader(String shaderName, boolean hasGeometry) {
		this.shaderName = shaderName;
		this.hasGeometry = hasGeometry;
		
		programId = glCreateProgram();
		
		String vertexShaderText = loadShader(shaderName + ".vs");
		String fragmentShaderText = loadShader(shaderName + ".fs");
		String geometryShaderText = null; if(hasGeometry) geometryShaderText = loadShader(shaderName + ".gs");

		addVertexShader(vertexShaderText);
		addFragmentShader(fragmentShaderText);
		if(hasGeometry) addGeometryShader(geometryShaderText);

		compileShader();
	}
	
	public void bind() {
		glUseProgram(programId);
	}
	
	public static void unbind() {
		glUseProgram(0);
	}
	
	private void compileShader() {
		try {
			glLinkProgram(programId);
			
			if(glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
				System.err.println("Compling " + shaderName + ": " + glGetProgramInfoLog(programId, 1024));
			}
			
			glValidateProgram(programId);
			
			if(glGetProgrami(programId, GL_VALIDATE_STATUS) == GL_FALSE) {
				System.err.println("Compling " + shaderName + ": " + glGetProgramInfoLog(programId, 1024));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addVertexShader(String text) {
		vertexShaderId = addProgram(text, GL_VERTEX_SHADER);
	}

	private void addGeometryShader(String text)	{
		geometryShaderId = addProgram(text, GL_GEOMETRY_SHADER);
	}

	private void addFragmentShader(String text)	{
		fragmentShaderId = addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	private int addProgram(String text, int type) {
		try {
			int shader = glCreateShader(type);
			
			if(shader == GL_FALSE)	{
				throw new Exception("Shader creation failed: Could not find valid memory location when adding shader");
			}
			
			glShaderSource(shader, text);
			glCompileShader(shader);
			
			if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
				System.err.println("Adding Program to " + shaderName + ": " + glGetShaderInfoLog(shader, 1024));
			}
			
			glAttachShader(programId, shader);
		
			return shader;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try {
			shaderReader = new BufferedReader(new FileReader(fileName));
			String line;

			while((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}		
		
		return shaderSource.toString();
	}
	
	public void addUniform(String name) {
		try {
			int uniformLocation = glGetUniformLocation(programId, name);
			
			if(uniformLocation == 0xFFFFFFFF) {
				throw new Exception("Error: Could not find uniform: " + name);
			}
			
			uniforms.put(name, uniformLocation);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUniformi(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Float3 value) {
		glUniform3f(uniforms.get(uniformName), value.a, value.b, value.c);
	}

	public int getProgramId() {
		return programId;
	}

	public String getName() {
		return shaderName;
	}
	
	public boolean hasGeometry() {
		return hasGeometry;
	}

	public void cleanUp() {
		glDeleteProgram(programId);
		
		glDeleteShader(vertexShaderId);
		glDeleteShader(fragmentShaderId);
		glDeleteShader(geometryShaderId);
	}
}
