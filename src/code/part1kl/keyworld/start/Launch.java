package code.part1kl.keyworld.start;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import code.part1kl.keyworld.entity.Camera;
import code.part1kl.keyworld.voxel.Voxel;
import code.part1kl.keyworld.world.Sector;
import code.part1kl.keyworld.world.World;

import com.sun.prism.impl.BufferUtil;
 
/**
 * 
 * @author part1kl
 *
 */
public class Launch {
	
	private Camera cam;
	private int cube;
	
	public static World WORLD;
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    // The window handle
    private long window;
 
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        initializeGame();
        
        try {
            init();
            renderLoop();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }
    
    private void initializeGame() {
    	WORLD = new World();
    	WORLD.initializeMaterials();
    	
    	WORLD.loadWorld();
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        int WIDTH = 800;
        int HEIGHT = 450;
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                if(key == GLFW_KEY_W) cam.moveZ(-0.1f);
        		if(key == GLFW_KEY_S) cam.moveZ(0.1f);
        		if(key == GLFW_KEY_D) cam.moveX(-0.1f);
        		if(key == GLFW_KEY_A) cam.moveX(0.1f);
        		
        		if(key == GLFW_KEY_UP) cam.rotateX(-1f);
        		if(key == GLFW_KEY_DOWN) cam.rotateX(1f);
        		if(key == GLFW_KEY_LEFT) cam.rotateY(1f);
        		if(key == GLFW_KEY_RIGHT) cam.rotateY(-1f);
        		
        		if(key == GLFW_KEY_SPACE) cam.moveUp(0.1f);
        		if(key == GLFW_KEY_LEFT_SHIFT) cam.moveUp(-0.1f);
            }
        });
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
        
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();
        
        
        cam = new Camera(70, 800/450f, 0.2f, 100000);
        makeVBOs();
        
        glEnable(GL_RESCALE_NORMAL);
        
        cam.rotateX(180f);
    }
 
    private void renderLoop() {
        // Set the clear color
        glClearColor(0f, 1.0f, 1.0f, 0.0f);
 
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glLoadIdentity();
            cam.useView();
        	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glEnableClientState(GL_VERTEX_ARRAY);
            glBindBuffer(GL_ARRAY_BUFFER, cube);
            
            
            
            glVertexPointer(3, GL_FLOAT, 0, 0);
            glScalef(1, 1, 1);
            for(Sector s : WORLD.sectors) {
            	for(int x=0; x<Sector.SIZE; x++) {
            		for(int y=0; y<Sector.SIZE; y++) {
            			for(int z=0; z<Sector.SIZE; z++) {
                    		Voxel v = s.getVoxel(x, y, z);
                            glPushMatrix();
                            glTranslatef(x, y, z);
                            glColor3f(v.color.a, v.color.b, v.color.c);
                            glDrawArrays(GL_QUADS, 0, 24);
                            glPopMatrix();
                    	}
                	}
            	}
            }
            
            
            
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glDisableClientState(GL_VERTEX_ARRAY);
            
            glfwSwapBuffers(window); // swap the color buffers
            
            // Poll for window events. The key callback above will only be
            // invoked during this call.	
            glfwPollEvents();
        }
    }
    
    private void makeVBOs() {
    	cube = glGenBuffers();
    	System.out.println(cube);
    	float[] cubeVerts = { 0, 0, 0, //left
    						1, 0, 0,
    						1, 1, 0,
    						0, 1, 0,
    						
    						0, 0, 1, //right
    						1, 0, 1,
    						1, 1, 1,
    						0, 1, 1,
    						
    						0, 0, 0, //back
    						0, 0, 1, 
    						0, 1, 1,
    						0, 1, 0,
    						
    						1, 0, 0, //front
    						1, 0, 1,
    						1, 1, 1,
    						1, 1, 0,
    						
    						0, 0, 0, //bottom
    						1, 0, 0, 
    						1, 0, 1,
    						0, 0, 1,

    						0, 1, 0, //top
    						1, 1, 0, 
    						1, 1, 1,
    						0, 1, 1   };
    	FloatBuffer verts = BufferUtil.newFloatBuffer(cubeVerts.length);
    	for(float v : cubeVerts) {
    		verts.put(v);
    	}
    	verts.flip();
    	glBindBuffer(GL_ARRAY_BUFFER, cube);
    	glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW);
    	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
 
    public static void main(String[] args) {
        new Launch().run();
    }
 
}