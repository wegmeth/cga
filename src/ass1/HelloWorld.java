package ass1;

/*
 * Instructions:
 *  - IntelliJ on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 *  - IntelliJ on Windows, Linux: VM Options: -Djava.library.path=libs/LWJGL/native
 */

import org.joml.Vector3f;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
 
import java.nio.ByteBuffer;

//import static org.joml.GeometryUtils.normal;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class HelloWorld {
 
    // references to callback function for error and keyboard handling
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    // the window handle
    private long window;
 
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        
//        triangleSolution(); // you need to provide the implementation for this function down below
 
        try {
        	init(); // initializes the window and OpenGL        
            renderingLoop(); // starts the rendering loop
 
            // release window and window callbacks when finished
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // terminate GLFW and release the GLFW errorcallback
            glfwTerminate();
            errorCallback.release();
        }
    }
    
        
    private void init() {
    	// init() contains everything that needs to be done only once at programstart
    	initWindow(); // initialize the window
        initKeyboardCallbacks(); // initialize the keyboard handling
        initOpenGLSettings(); // initialize OpenGL
    }


	private void initWindow() {
		// the initWindow() functions creates and displays an OpenGL window 
		// to display OpenGL's framebuffer
		
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
    	 glfwSetErrorCallback(errorCallback = createPrint((System.err)));
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        int WIDTH = 300;
        int HEIGHT = 300;
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");        
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
        
    }
	
	
	private void initKeyboardCallbacks() {
	     // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
	}
 
	
	private void initOpenGLSettings() {
	    // This line is critical for LWJGL's interoperation with GLFW's OpenGL context
        GL.createCapabilities(); // valid for latest bui
        // Set the clear color, in this case red //
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	}
	
    
    private void renderingLoop() {    
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            // the display function contains all your rendering calls for each frame
        	display();
        	triangleSolution();
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    
    
    private void display() {
    	// the display function should contain all your rendering calls
    	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer (color and depth)    	
        glfwSwapBuffers(window); // swap the color buffers
    }
    
    
    private void triangleSolution() {
//    	Gegeben seien die drei Punkte A, B und C, die gegen den Uhrzeigersinn ein Dreieck bilden. Berechnen
//    	Sie den Normalenvektor N , der durch das Dreieck definiert wird und den Winkel zwischen N und einem
//    	Vektor V .
    	
    	Vector3f a = new Vector3f(1f,0f,0f);
    	Vector3f b = new Vector3f(0f,1f,0f);
    	Vector3f c = new Vector3f(0f,0f,1f);
    	
//    	Vector3f n = new Vector3f();
//    	GeometryUtils.normal(a,b,c, n);
////    	System.out.println("Vektor n ->"+n);
//    	
//    	Vector3f v = new Vector3f(1,1,-3);
//    	float angle = v.angle(n);
//    	System.out.println("winkel zwischen n und v  " + angle);
    	
    	glColor3f(1.0f,1f,0);
    	 glBegin(GL_TRIANGLES);
    	   glVertex3f(a.x, a.y, a.z);
    	   glVertex3f(b.x, b.y, b.z);
    	   glVertex3f(c.x, c.y, c.z);
    	 glEnd();
    }

    
    public static void main(String[] args) {
        new HelloWorld().run();
    }
 
}