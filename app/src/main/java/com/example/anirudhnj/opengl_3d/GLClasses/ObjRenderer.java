package com.example.anirudhnj.opengl_3d.GLClasses;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.anirudhnj.opengl_3d.CustomObjects.Cube;
import com.example.anirudhnj.opengl_3d.CustomObjects.Square;
import com.example.anirudhnj.opengl_3d.CustomObjects.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by anirudhnj on 29/09/17.
 */

public class ObjRenderer implements GLSurfaceView.Renderer {
    Context context;   // Application's context

    Triangle triangle;     // ( NEW )
    Square quad;           // ( NEW )
    Cube cube;

    Boolean drawTriangle = true;
    Boolean drawSquare = false;
    Boolean drawCube = false;

    private float rotation;


    // Constructor with global application context
    public ObjRenderer(Context context) {
        this.context = context;

        triangle = new Triangle();   // ( NEW )
        quad = new Square();         // ( NEW )
        cube = new Cube();

    }

    // Call back when the surface is first created or re-created
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

        // You OpenGL|ES initialization code here
        // ......
    }

    // Call back after onSurfaceCreated() or whenever the window's size changes
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / (float)height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45.0f, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();                 // Reset

        // You OpenGL|ES display re-sizing code here
        // ......
    }

    // Call back to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear color and depth buffers using clear-value set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // You OpenGL|ES rendering code here
        // ......
        if(drawTriangle) {
            gl.glLoadIdentity();
            gl.glTranslatef(0.0f, 0.0f, -3.0f);
            triangle.draw(gl);                   // Draw triangle ( NEW )
            gl.glLoadIdentity();
            //rotation -= 0.15f;

        }

        if(drawSquare) {

            gl.glLoadIdentity();
            gl.glTranslatef(0.0f, 0.0f, -10.0f);
            gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
            quad.draw(gl);                       // Draw quad ( NEW )
            gl.glLoadIdentity();
            rotation -= 0.15f;

        }

        if(drawCube) {
            gl.glLoadIdentity();
            gl.glTranslatef(0.0f, 0.0f, -10.0f);
            gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
            cube.draw(gl);
            gl.glLoadIdentity();
            rotation -= 0.15f;
        }

    }
}
