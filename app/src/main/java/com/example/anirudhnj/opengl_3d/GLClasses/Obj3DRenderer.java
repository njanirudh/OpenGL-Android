package com.example.anirudhnj.opengl_3d.GLClasses;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.anirudhnj.opengl_3d.GLUtility.ObjLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by anirudhnj on 29/09/17.
 */

public class Obj3DRenderer implements  GLSurfaceView.Renderer {

    private float rotation;
    Context context;

    int numFaces = 0;
    int mBytesPerFloat = 4;

    FloatBuffer mVertexBuffer;
    FloatBuffer mNormalBuffer;
    FloatBuffer mFaceBuffer;

    public Obj3DRenderer(Context context) {
        this.context = context;

        ObjLoader objLoader = new ObjLoader(context,"cube.obj");

        numFaces = objLoader.numFaces;
        //Log.e("Faces","!!!!!!!!!");

// Initialize the buffers.
        mVertexBuffer = ByteBuffer.allocateDirect(objLoader.positions.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(objLoader.positions).position(0);

        mNormalBuffer = ByteBuffer.allocateDirect(objLoader.normals.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mNormalBuffer.put(objLoader.normals).position(0);


    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -10.0f);
        gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);

        gl.glDrawElements(GL10.GL_TRIANGLES, mVertexBuffer.array().length, GL10.GL_UNSIGNED_BYTE,
                mVertexBuffer);

        gl.glLoadIdentity();
        rotation -= 0.15f;
    }

}