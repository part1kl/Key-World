package code.part1kl.keyworld.entity;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRANSFORM_BIT;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.nio.FloatBuffer;

import com.sun.prism.impl.BufferUtil;

public class Camera {

	public float x, y, z, xRot, yRot, zRot;
	private static FloatBuffer matrix = BufferUtil.newFloatBuffer(16);
	private float fov, aspect, near, farRender;
	
	public Camera(float f, float asp, float nea, float far){
		x = 0;
		y = 0;
		z = 0;
		
		xRot = 0;
		yRot = 0;
		zRot = 0;
		
		this.fov = f;
		this.aspect = asp;
		this.near = nea;
		this.farRender = far;
		
		initProjection();
	}
	
	private void initProjection(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
        glCustomPerspective(fov, aspect, near, farRender);
        
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_DEPTH_TEST);
	}
	
	public static void glCustomPerspective(float fovy, float aspect, float zNear, float zFar) {
		  float sine, cotangent, deltaZ;
		  float radians = (float) (fovy / 2 * Math.PI / 180);

		  deltaZ = zFar - zNear;
		  sine = (float) Math.sin(radians);

		  if ((deltaZ == 0) || (sine == 0) || (aspect == 0)) {
		   return;
		  }

		  cotangent = (float) Math.cos(radians) / sine;

		  matrix.put(0 * 4 + 0, cotangent / aspect);
		  matrix.put(1 * 4 + 1, cotangent);
		  matrix.put(2 * 4 + 2, - (zFar + zNear) / deltaZ);
		  matrix.put(2 * 4 + 3, -1);
		  matrix.put(3 * 4 + 2, -2 * zNear * zFar / deltaZ);
		  matrix.put(3 * 4 + 3, 0);

		  glMultMatrix(matrix);
		 }
	
	public void useView(){
		glPushAttrib(GL_TRANSFORM_BIT);
			glMatrixMode(GL_MODELVIEW);
			glRotatef(xRot, 1, 0, 0);
			glRotatef(yRot, 0, 1, 0);
			glRotatef(zRot, 0, 0, 1);
		
			glTranslatef(x, y, z);
		glPopAttrib();
	}
	
	public void moveZ(float amt){
		z += amt * Math.sin(Math.toRadians(yRot + 90));
		x += amt * Math.cos(Math.toRadians(yRot + 90));
	}
	
	public void moveX(float amt){
		z += amt * Math.sin(Math.toRadians(yRot));
		x += amt * Math.cos(Math.toRadians(yRot));
	}
	
	public void rotateY(float amt){
		yRot += amt;
	}
	
	public void rotateX(float amt){
		int max = 360, min = 0;
		
		if(xRot <= max  && xRot >= min)
			xRot += amt;
		else if(xRot >= min)
			xRot = max;
		else
			xRot = min;	
	}
	
	public void rotateZ(float amt){
		zRot += amt;
	}
	public void moveUp(float amt){
		y += amt;
	}
	
	
}
