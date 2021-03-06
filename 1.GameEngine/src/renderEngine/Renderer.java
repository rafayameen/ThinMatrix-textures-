package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer
{
	// prepare opengl to render game
	public void prepare()
	{
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	// render the given RawModel
	public void render(Entity entity, StaticShader shader)
	{
		TexturedModel texturedModel = entity.getTexturedModel();
		RawModel rawModel = texturedModel.getModel();
		// bind vao
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),
				entity.getRotY(), entity.getRotZ(), entity.getScale());
		
		shader.loadTransformationMatrix(transformationMatrix);

		GL13.glActiveTexture(GL13.GL_TEXTURE0); // sampler2D is in gltexture0
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		// disable because we finished using vertexAttribList
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		// unbind vao
		GL30.glBindVertexArray(0);
	}

}
