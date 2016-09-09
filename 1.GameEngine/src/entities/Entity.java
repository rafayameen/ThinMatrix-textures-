package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity
{
	private TexturedModel texturedModel;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	public Entity(TexturedModel texturedModel, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		this.texturedModel = texturedModel;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public void increasePosition(float px, float py, float pz)
	{
		this.position.x += px;
		this.position.y += py;
		this.position.z += pz;
	}
	
	public void increaseRotation(float rx, float ry, float rz)
	{
		this.rotX += rx;
		this.rotY += ry;
		this.rotZ += rz;
	}
	
	public TexturedModel getTexturedModel()
	{
		return texturedModel;
	}
	public void setTexturedModel(TexturedModel texturedModel)
	{
		this.texturedModel = texturedModel;
	}
	public Vector3f getPosition()
	{
		return position;
	}
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	public float getRotX()
	{
		return rotX;
	}
	public void setRotX(float rotX)
	{
		this.rotX = rotX;
	}
	public float getRotY()
	{
		return rotY;
	}
	public void setRotY(float rotY)
	{
		this.rotY = rotY;
	}
	public float getRotZ()
	{
		return rotZ;
	}
	public void setRotZ(float rotZ)
	{
		this.rotZ = rotZ;
	}
	public float getScale()
	{
		return scale;
	}
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	
	
	

}

