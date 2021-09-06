package com.augmented

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.android.synthetic.main.activity_makeup.*


class MakeupActivity3 : AppCompatActivity() {
    companion object {
        const val MIN_OPENGL_VERSION = 3.0
    }

    lateinit var arFragment: FaceArFragment
    private var faceMeshTexture: Texture? = null
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_makeup)
        if (!checkIsSupportedDeviceOrFinish()) {
            return
        }
        arFragment = face_fragment as FaceArFragment
        Texture.builder()
                .setSource(this, R.drawable.redshade)
                .build()
                .thenAccept { texture -> faceMeshTexture = texture }

        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene

        scene.addOnUpdateListener {
            faceMeshTexture.let {
                sceneView.session
                        ?.getAllTrackables(AugmentedFace::class.java)?.let {
                            for (f in it) {
                                if (!faceNodeMap.containsKey(f)) {
                                    val faceNode = AugmentedFaceNode(f)
                                    faceNode.setParent(scene)
                                    faceNode.faceMeshTexture = faceMeshTexture
                                    faceNodeMap.put(f, faceNode)
                                }
                            }
                            // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
                            val iter = faceNodeMap.entries.iterator()
                            while (iter.hasNext()) {
                                val entry = iter.next()
                                val face = entry.key
                                if (face.trackingState == TrackingState.STOPPED) {
                                    val faceNode = entry.value
                                    faceNode.setParent(null)
                                    iter.remove()
                                }
                            }
                        }
            }
        }

        btnPurple.setOnClickListener {
            startActivity(Intent(this, MakeupActivity::class.java))
        }

        btnMagenta.setOnClickListener {
            startActivity(Intent(this, MakeupActivity2::class.java))
        }
        btnRed.setOnClickListener {
            startActivity(Intent(this, MakeupActivity4::class.java))
        }
        btnPeach.setOnClickListener{
            startActivity(Intent(this,MakeupActivity5::class.java))
        }
        btnOrange.setOnClickListener{
            startActivity(Intent(this,MakeupActivity6::class.java))
        }
        btnBabyPink.setOnClickListener {
            startActivity(Intent(this, MakeupActivity7::class.java))
        }

        scn.setOnClickListener {
            startActivity(Intent(this, ScanResultActivity::class.java))
        }
    }







    fun checkIsSupportedDeviceOrFinish() : Boolean {
        if (ArCoreApk.getInstance().checkAvailability(this) == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
            Toast.makeText(this, "Augmented Faces requires ARCore", Toast.LENGTH_LONG).show()
            finish()
            return false
        }
        val openGlVersionString =  (getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)
                ?.deviceConfigurationInfo
                ?.glEsVersion

        openGlVersionString?.let { s ->
            if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
                Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                        .show()
                finish()
                return false
            }
        }
        return true
    }
}

//    private fun applyFilter(function: () -> Unit) {
//
//
//    }
