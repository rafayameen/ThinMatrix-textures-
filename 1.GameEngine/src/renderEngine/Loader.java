package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

public class Loader {
	private ArrayList<Integer> vaos = new ArrayList<Integer>();
	private ArrayList<Integer> vbos = new ArrayList<Integer>();
	private ArrayList<Integer> textures = new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] positions,float[] textureCoords, int[] indices) {
		// create vao
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		vaos.add(vaoID);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;

	}

	public void cleanUp() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}

		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture: textures)
		{
			GL11.glDeleteTextures(texture);
		}
	}

	// three steps for above function
	// 1.create empty VAO and return ID of created VAO
	private int createVAO() {
		// create empty vao
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	// 2.store date in attribute list of VAO at given attributeNumber
	private void storeDataInAttributeList(int attributeNumber,int coordinatesSize, float[] data) {
		// date must be stored as vbo
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		// date is stored in vbo as float buffer
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinatesSize, GL11.GL_FLOAT, false, 0, 0);
		// unbind current vbo
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	// 3. unbindVAO when finished using it
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		// data in indices is stored as int buffer
		IntBuffer buffer = storeDataInIntBuffer(indices);
		// int buffer created store in vbo
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

	}

	// get data in int and convert to IntBuffer
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		// to read data from buffer, flip it
		buffer.flip();
		return buffer;
	}

	// get data in float and convert to FloatBuffer
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		// to read data from buffer, flip it
		buffer.flip();
		return buffer;
	}

}
