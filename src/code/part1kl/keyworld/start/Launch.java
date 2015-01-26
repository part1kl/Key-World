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
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import code.part1kl.keyworld.entity.Camera;
import code.part1kl.keyworld.utils.Float3;
import code.part1kl.keyworld.utils.Float4;
import code.part1kl.keyworld.utils.Shader;
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
	private int vertexVBO, colorVBO;
	public static boolean USE_A_SHADER = true;
	public static int program=0;
	public float[] colorVerts = new float[96];
	float[] cubeLeft = { 
			0, 0, 0, //left
			1, 0, 0,
			1, 1, 0,
			0, 1, 0
	},
	cubeRight = {
			0, 0, 1, //right
			1, 0, 1,
			1, 1, 1,
			0, 1, 1
	},
	cubeBack = {
			0, 0, 0, //back
			0, 0, 1, 
			0, 1, 1,
			0, 1, 0
	},
	cubeFront = {
			1, 0, 0, //front
			1, 0, 1,
			1, 1, 1,
			1, 1, 0
	},
	cubeBottom = {
			0, 0, 0, //bottom
			1, 0, 0, 
			1, 0, 1,
			0, 0, 1
	},
	cubeTop = {
			0, 1, 0, //top
			1, 1, 0, 
			1, 1, 1,
			0, 1, 1
	};
	float[][] faces = {cubeLeft, cubeRight, cubeBack, cubeFront, cubeBottom, cubeTop};
	int verticiesNumber=0;
	Shader basicShader;
	Thread rendering, keyListener;
	
	public static World WORLD;
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
 
    // The window handle
    private long window;
 
    public void run() {
        System.out.println("Using LWJGL " + Sys.getVersion());
        initializeGame();
        
        try {
        	init();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
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
        window = glfwCreateWindow(WIDTH, HEIGHT, "Keyworld", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
        
        
        keyListener = new Thread() {
        	public void run() {
        		while ( glfwWindowShouldClose(window) == GL_FALSE ) {
        			if (glfwGetKey(window,GLFW_KEY_ESCAPE) == GLFW_PRESS) glfwSetWindowShouldClose(window, GL_TRUE);
        			
                    if(glfwGetKey(window,GLFW_KEY_W) == GLFW_PRESS) cam.moveZ(-0.1f);
            		if(glfwGetKey(window,GLFW_KEY_S) == GLFW_PRESS) cam.moveZ(0.1f);
            		if(glfwGetKey(window,GLFW_KEY_D) == GLFW_PRESS) cam.moveX(-0.1f);
            		if(glfwGetKey(window,GLFW_KEY_A) == GLFW_PRESS) cam.moveX(0.1f);
            		
            		if(glfwGetKey(window,GLFW_KEY_UP) == GLFW_PRESS) cam.rotateX(-.75f);
            		if(glfwGetKey(window,GLFW_KEY_DOWN) == GLFW_PRESS) cam.rotateX(.75f);
            		if(glfwGetKey(window,GLFW_KEY_LEFT) == GLFW_PRESS) cam.rotateY(.75f);
            		if(glfwGetKey(window,GLFW_KEY_RIGHT) == GLFW_PRESS) cam.rotateY(-.75f);
            		
            		if(glfwGetKey(window,GLFW_KEY_SPACE) == GLFW_PRESS) cam.moveUp(0.1f);
            		if(glfwGetKey(window,GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) cam.moveUp(-0.1f);
            		try { sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        		}
        	}
        };
        keyListener.start();
 
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
        
        
        cam = new Camera(70, 800/450f, 1f, 1000);
        vertexVBO = glGenBuffers();
    	colorVBO = glGenBuffers();
        makeWorldVBOs();
        
        glDisable(GL_RESCALE_NORMAL);
        glEnable(GL_BLEND);
        glEnable(GL_FOG);
        glFogf(GL_FOG_START, 10);
        glFogf(GL_FOG_END, 20);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        cam.rotateX(180f);
        cam.moveUp(-100);
        
        basicShader = new Shader("resource/shader/shader", false);
        
        renderLoop();
    }
    
    private void renderLoop() {
        // Set the clear color
        glClearColor(0f, 1f, 1f, 0f);
 
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glLoadIdentity();
            cam.useView();
        	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        	
            if(USE_A_SHADER) {
            	basicShader.bind();
            }

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);
            
           printWorld();
            
            
            glDisableClientState(GL_VERTEX_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);
            
            if(USE_A_SHADER){
            	Shader.unbind();
            }
            
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            
            glfwSwapBuffers(window); // swap the color buffers
            
            // Poll for window events. The key callback above will only be
            // invoked during this call.	
            glfwPollEvents();
        }
    }
    
    private void printWorld() {
    	 glBindBuffer(GL_ARRAY_BUFFER, vertexVBO);
         glVertexPointer(3, GL_FLOAT, 0, 0);
         glBindBuffer(GL_ARRAY_BUFFER, colorVBO);
         glColorPointer(4, GL_FLOAT, 0, 0);
       
         
         glTranslatef((float)World.SIZE*Sector.SIZE/2f,(float)World.SIZE*Sector.SIZE/2f,(float)World.SIZE*Sector.SIZE/2f);
         glRotatef(180, 1, 0, 0);
         glTranslatef(-(float)World.SIZE*Sector.SIZE/2f,-(float)World.SIZE*Sector.SIZE/2f,-(float)World.SIZE*Sector.SIZE/2f);
         
//         glColor4f(1, 1, 1, 1);
         glDrawArrays(GL_QUADS, 0, verticiesNumber);
    }
    
    private void makeWorldVBOs() {
    	ArrayList<Float> verticies = new ArrayList<>();
    	ArrayList<Float> colors = new ArrayList<>();
    	for(int yi=0; yi<World.WORLD_SECTOR_HEIGHT; yi++) {
    		for(int y=0; y<Sector.SIZE; y++) {
    			Float3 colorz = new Float3((float)Math.random(),(float)Math.random(),(float)Math.random());
	    		for(int xi=0; xi<World.SIZE; xi++) {
	    			for(int zi=0; zi<World.SIZE; zi++) {
	        			Sector s = WORLD.getSector(xi, yi, zi);
		            	for(int x=0; x<Sector.SIZE; x++) {
		            		for(int z=0; z<Sector.SIZE; z++) {
	            				boolean[] facez = s.checkFaces(x, y, z);
	            				for(int face=0; face<6; face++) {
	            					if(facez[face]) {
	            						for(int vert = 0; vert<faces[face].length; vert+=3) {
	            							verticies.add(faces[face][vert]+(xi*Sector.SIZE+x));
	            							verticies.add(faces[face][vert+1]+(yi*Sector.SIZE+y));
	            							verticies.add(faces[face][vert+2]+(zi*Sector.SIZE+z));
	            							Voxel v = WORLD.getSector((x-(x%Sector.SIZE))/Sector.SIZE, (y-(y%Sector.SIZE))/Sector.SIZE, (z-(z%Sector.SIZE))/Sector.SIZE).getVoxel(x%Sector.SIZE, y%Sector.SIZE, z%Sector.SIZE);
	            							colors.add((float)(colorz.a));
	            							colors.add((float)(colorz.b));
	            							colors.add((float)(colorz.c));
	            							colors.add(1f);
	            						}
	            					}
	            				}
	            			}
	            		}
	            	}
        		}
        	}
    	}
    	verticiesNumber = verticies.size()/3;
    	FloatBuffer verts = BufferUtil.newFloatBuffer(verticies.size());
     	for(float e : verticies) {
     		verts.put(e);
     	}
     	verts.flip();
     	FloatBuffer cols = BufferUtil.newFloatBuffer(colors.size());
     	for(float e : colors) {
     		cols.put(e);
     	}
     	cols.flip();
     	glBindBuffer(GL_ARRAY_BUFFER, vertexVBO);
     	glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW);
     	
     	glBindBuffer(GL_ARRAY_BUFFER, colorVBO);
     	glBufferData(GL_ARRAY_BUFFER, cols, GL_STATIC_DRAW);
     	
     	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
 
    public static void main(String[] args) {
        new Launch().run();
    }
    
    
    
}