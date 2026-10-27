package com.example.wifisecuritylab

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {
    private val bg = Color.rgb(10, 14, 28)
    private val panel = Color.rgb(20, 28, 48)
    private val cyan = Color.rgb(0, 230, 220)
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 28, 24, 28)
            setBackgroundColor(bg)
        }
        scroll.addView(layout)
        setContentView(scroll)

        layout.addView(label("WI-FI SECURITY LAB", 28f, cyan))
        layout.addView(label("EDUCATIONAL • OFFLINE DEMO", 12f, Color.LTGRAY))
        layout.addView(space(18))

        layout.addView(label("1  PASSWORD STRENGTH CHECK", 18f, Color.WHITE))
        val password = EditText(this).apply {
            hint = "Enter a password to evaluate"
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            inputType = 0x00000081
            setPadding(16, 10, 16, 10)
            setBackgroundColor(panel)
        }
        layout.addView(password, matchWrap())
        layout.addView(button("Analyze password") {
            val p = password.text.toString()
            val tips = mutableListOf<String>()
            if (p.length < 12) tips.add("Use at least 12 characters.")
            if (p.none { it.isUpperCase() }) tips.add("Add uppercase letters.")
            if (p.none { it.isLowerCase() }) tips.add("Add lowercase letters.")
            if (p.none { it.isDigit() }) tips.add("Add numbers.")
            if (p.none { !it.isLetterOrDigit() }) tips.add("Add symbols.")
            output.text = if (p.isEmpty()) "Enter a password first."
                else if (tips.isEmpty()) "Strong pattern: long and varied. Avoid reused or personal information."
                else "Suggestions:\n• " + tips.joinToString("\n• ")
        })
        layout.addView(space(18))

        layout.addView(label("2  SIMULATED CRACKING LAB", 18f, Color.WHITE))
        layout.addView(label(
            "This offline exercise uses a fictional target only. It does not scan or attack real Wi-Fi networks.",
            14f, Color.LTGRAY
        ))
        layout.addView(button("Run safe demo") {
            output.text = "SIMULATION COMPLETE\n\nFictional lab target: DEMO-NET\nResult: demo passphrase matched in the mock exercise.\n\nNo router, network, handshake, or real password was accessed."
        })
        layout.addView(space(18))

        layout.addView(label("3  ROUTER HARDENING CHECKLIST", 18f, Color.WHITE))
        layout.addView(label(
            "✓ Prefer WPA3-Personal, or WPA2-AES if WPA3 is unavailable.\n" +
            "✓ Disable WPS if you do not need it.\n" +
            "✓ Set a unique, long Wi-Fi passphrase.\n" +
            "✓ Update router firmware.\n" +
            "✓ Change the router admin password from its default.\n" +
            "✓ Use a guest network for visitors and IoT devices.",
            15f, Color.LTGRAY
        ))
        layout.addView(space(18))
        output = label("Results will appear here.", 15f, cyan)
        output.setPadding(16, 18, 16, 18)
        output.setBackgroundColor(panel)
        layout.addView(output, matchWrap())
        layout.addView(space(16))
        layout.addView(label("Privacy: all checks run locally in this demo app.", 12f, Color.GRAY))
    }

    private fun label(text: String, size: Float, color: Int) = TextView(this).apply {
        this.text = text
        textSize = size
        setTextColor(color)
        setPadding(0, 5, 0, 8)
    }
    private fun button(text: String, action: () -> Unit) = Button(this).apply {
        this.text = text
        setTextColor(Color.BLACK)
        setBackgroundColor(cyan)
        setOnClickListener { action() }
    }
    private fun space(h: Int) = TextView(this).apply { height = h }
    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply { bottomMargin = 8 }
}
