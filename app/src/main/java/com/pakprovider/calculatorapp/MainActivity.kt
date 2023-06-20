package com.pakprovider.calculatorapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.pakprovider.calculatorapp.databinding.ActivityMainBinding
import javax.script.ScriptEngineManager


@SuppressLint("StaticFieldLeak")
        private lateinit var binding: ActivityMainBinding
        var result: String = ""
        var allw = 1
        private lateinit var linster:SharedPreferences.OnSharedPreferenceChangeListener


@Suppress("DEPRECATION", "NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "RtlHardcoded", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editor = getSharedPreferences("Mode_Dark", MODE_PRIVATE)
        val inputformShared=editor.getString("inputText","0")
        if (inputformShared != null) {
            result=inputformShared
        }
         val modec=editor.getBoolean("mode2",false)
        if (modec){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val manger=PreferenceManager.getDefaultSharedPreferences(this)

        linster=SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            val editor = getSharedPreferences("Mode_Dark", MODE_PRIVATE).edit()

            if (key.equals("mode")){
                if (manger.getBoolean("mode",false)) {
                    editor.putBoolean("mode2",true)
                    editor.apply()
                    recreate()


                } else {
                    editor.putBoolean("mode2",false)
                    editor.apply()
                    recreate()

                }
            }
        }
        manger.registerOnSharedPreferenceChangeListener(linster)




        binding.threeDot.setOnClickListener {
            val popupMenu = PopupMenu(this@MainActivity, binding.threeDot, Gravity.RIGHT)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                when (menuItem.itemId) {
                    R.id.exit_app -> {
                        onBackPressed()
                        true
                    }

                    R.id.settings -> {

                        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))

                        true
                    }
                    R.id.rate_us -> {
                        val url = "https://play.google.com/store/apps/details?id=com.pakprovider.calculatorapp"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

                        true
                    }
                    R.id.more_apps -> {
                        val url = "https://play.google.com/store/apps/developer?id=Pak+Provider"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

                        true
                    }

                    else -> false
                }

            }
            // Showing the popup menu
            popupMenu.show()

        }



        binding.ac.setOnClickListener {
            clearAll()
        }
        binding.one.setOnClickListener {
            updateString(1)
        }
        binding.two.setOnClickListener {
            updateString(2)
        }
        binding.three.setOnClickListener {
            updateString(3)
        }
        binding.four.setOnClickListener {
            updateString(4)
        }
        binding.five.setOnClickListener {
            updateString(5)
        }
        binding.six.setOnClickListener {
            updateString(6)
        }
        binding.seven.setOnClickListener {
            updateString(7)
        }
        binding.eight.setOnClickListener {
            updateString(8)
        }
        binding.nine.setOnClickListener {
            updateString(9)
        }
        binding.zero.setOnClickListener {
            updateString("0")
        }
        binding.dZero.setOnClickListener {
            updateString("00")
        }
        binding.dot.setOnClickListener {
            if (allw == 1) {
                updateString(".")


            }
            allw = 0


        }

        binding.division.setOnClickListener {
            updateString("/")
        }
        binding.multiple.setOnClickListener {
            updateString("x")
        }
        binding.mines.setOnClickListener {
            updateString("-")
        }
        binding.plus.setOnClickListener {
            updateString("+")
        }
        binding.back.setOnClickListener {
            if (result == "") {
//                Toast.makeText(this, "Not found value", Toast.LENGTH_SHORT).show()
                allw = 1
            } else {


                result = result.substring(0, result.length - 1)
                binding.input.text = result
            }
        }
        binding.back.setOnLongClickListener {
            result = ""
            binding.input.text = result

            return@setOnLongClickListener true
        }
        binding.equal.setOnClickListener {

            if (result == "") {
//                Toast.makeText(this, "Enter Value", Toast.LENGTH_SHORT).show()

            } else {
//                val resu = binding.input.toString()
                val errorplus = result.endsWith("+")
                val errorpluss = result.startsWith("+")
                val errormies = result.endsWith("-")
                val errormit = result.endsWith("x")
                val errormits = result.startsWith("x")
                val errord = result.endsWith("/")
                val errords = result.startsWith("/")
                val errordot = result.endsWith(".")
                if (errorplus || errormies || errord || errormit || errorpluss || errords || errormits || errordot) {
//                    Toast.makeText(this, "Not Allow", Toast.LENGTH_SHORT).show()
                } else {


                    result = result.replace("x", "*", ignoreCase = true)
                    val engine = ScriptEngineManager().getEngineByName("rhino")
                    val res = engine.eval(result).toString()

                    binding.output.text = res
                }

            }

        }


    }

    private fun updateString(s: String) {

        result += s
        result = result.replace("xx", "x", ignoreCase = true)
        result = result.replace("x-", "-", ignoreCase = true)
        result = result.replace("x+", "+", ignoreCase = true)
        result = result.replace("x/", "/", ignoreCase = true)
        result = result.replace("/x", "x", ignoreCase = true)
        result = result.replace("//", "/", ignoreCase = true)
        result = result.replace("--", "-", ignoreCase = true)
        result = result.replace("-+", "+", ignoreCase = true)
        result = result.replace("+-", "-", ignoreCase = true)
        result = result.replace("-x", "x", ignoreCase = true)
        result = result.replace("-/", "/", ignoreCase = true)
        result = result.replace("+/", "/", ignoreCase = true)
        result = result.replace("++", "+", ignoreCase = true)
        result = result.replace("+x", "x", ignoreCase = true)

        val errordot = result.startsWith(".")
        if (errordot) {

            result = result.replace(".", "0.", ignoreCase = true)

        }
//        val dzore = result.startsWith("00")
        val dzore0 = result.startsWith("0")
        val dzore1 = result.startsWith("00")
        val dzore2 = result.startsWith("000")


        val errorplus = result.endsWith("+")
        val errorpluss = result.startsWith("+")
        val errormies = result.endsWith("-")
//        val  errormiess = result.startsWith("-")
        val errormit = result.endsWith("x")
        val errormits = result.startsWith("x")
        val errord = result.endsWith("/")
        val errords = result.startsWith("/")

        if (dzore0 || dzore1 || dzore2) {
            result = result.replace("0000", "0", ignoreCase = true)
            result = result.replace("000", "0", ignoreCase = true)
            result = result.replace("00", "0", ignoreCase = true)
            result = result.replace("0", "0", ignoreCase = true)


        }
        if (errords || errormits || errorpluss) {

            result = result.substring(0, result.length - 1)
            binding.input.text = result


        }
        if (errord || errormit || errorplus || errormies) {
//            Toast.makeText(this, "sjgtujs", Toast.LENGTH_SHORT).show()
            allw = 1

        }
//        binding.input.
        binding.input.text = result

    }


    private fun updateString(s: Int) {
        result += s
        binding.input.text = result

    }

    private fun clearAll() {
        result = ""
        binding.input.text = result
        binding.output.text = result
        allw = 1
    }

    override fun onResume() {

        binding.input.text = result
        super.onResume()
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Create the object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this)

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?")

        // Set Alert Title
        builder.setTitle(R.string.app_name)

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes") {
            // When the user click yes button then app will close
                _, _ ->
            finish()
        }

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No") {
            // If user click no then dialog box is canceled.
                dialog, _ ->
            dialog.cancel()
        }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }

    override fun onStart() {
        val editor = getSharedPreferences("Mode_Dark", MODE_PRIVATE)
        val inputformShared=editor.getString("inputText","0")
        if (inputformShared != null) {
            result=inputformShared
        }
        super.onStart()
    }

    override fun onPause() {
        val editor = getSharedPreferences("Mode_Dark", MODE_PRIVATE).edit()
        editor.putString("inputText", result)
        editor.apply()
        super.onPause()
    }

    override fun onDestroy() {
        val editor = getSharedPreferences("Mode_Dark", MODE_PRIVATE).edit()
        editor.putString("inputText", result)
        editor.apply()
        super.onDestroy()
    }


}