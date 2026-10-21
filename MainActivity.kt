package com.garagepaperco.pdf

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.provider.Settings
import android.view.Gravity
import android.widget.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : Activity() {
    private val PICK = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 42, 32, 32)
        }
        val title = TextView(this).apply {
            text = "Garage PDF Tools"
            textSize = 28f
            gravity = Gravity.CENTER
        }
        val sub = TextView(this).apply {
            text = "Free PDF tools for Garage Paper Co.\n\nKDP-friendly workflow"
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 28)
        }
        box.addView(title); box.addView(sub)

        fun button(label:String, action:()->Unit) {
            val b = Button(this).apply { text = label; setOnClickListener { action() } }
            box.addView(b, LinearLayout.LayoutParams(-1, 58).apply { setMargins(0,8,0,8) })
        }
        button("Open / View PDF") { pickPdf() }
        button("Create PDF from Images") { Toast.makeText(this,"Image-to-PDF is the next module.",Toast.LENGTH_LONG).show() }
        button("Merge PDFs") { Toast.makeText(this,"PDF merge module is queued for the next build.",Toast.LENGTH_LONG).show() }
        button("Split / Extract Pages") { Toast.makeText(this,"Split/extract module is queued for the next build.",Toast.LENGTH_LONG).show() }
        button("KDP Page-Size Checker") { showKdp() }
        button("About") { Toast.makeText(this,"Garage PDF Tools 1.0\nBuilt for Garage Paper Co.",Toast.LENGTH_LONG).show() }

        scroll.addView(box); setContentView(scroll)
    }

    private fun pickPdf() {
        startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/pdf"; addCategory(Intent.CATEGORY_OPENABLE)
        }, PICK)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if (requestCode==PICK && resultCode==RESULT_OK) {
            data?.data?.let { uri ->
                val intent = Intent(Intent.ACTION_VIEW, uri).apply { type="application/pdf"; addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) }
                try { startActivity(intent) }
                catch(e:Exception) { Toast.makeText(this,"No PDF viewer is available.",Toast.LENGTH_LONG).show() }
            }
        }
    }

    private fun showKdp() {
        AlertDialog(this).apply {
            setTitle("KDP Page-Size Checker")
            setMessage("For your current Garage Paper Co. paperback, the target trim is 8.5 × 11 inches.\n\nThis first build provides the workflow shell; the next build can add automatic PDF inspection and reporting.")
            setPositiveButton("OK",null)
        }.show()
    }
}
