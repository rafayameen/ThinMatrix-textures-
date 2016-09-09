package engineTester;


import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop
{

	public static void main(String[] args)
	{
		// open the display
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		// vertices of quad to be rendered
		float[] vertices = { -0.5f, 0.5f, 0, // V0
				-0.5f, -0.5f, 0, // V1
				0.5f, -0.5f, 0, // V2
				0.5f, 0.5f, 0 // V3

		};
		// indices
		int[] indices = { 0, 1, 3, 3, 1, 2 };

		// texture coordinates
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		TexturedModel texturedModel = new TexturedModel(model, texture);

		Entity entity = new Entity(texturedModel, new Vector3f(-1, 0, 0), 0, 0, 0, 0.5f);
		while (!Display.isCloseRequested())
		{

			// prepare renderer every single frame
			renderer.prepare();
			shader.start();
			// game logic

			entity.increasePosition(0.002f, 0, 0);

			// rendering
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		// cleanUp shader
		shader.cleanUp();
		// clean up created vaos and vbos
		loader.cleanUp();
		// close display
		DisplayManager.closeDisplay();

	}

}
