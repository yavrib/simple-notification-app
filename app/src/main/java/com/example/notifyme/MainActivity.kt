package com.example.notifyme

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

enum class INTERVALS {
    Day,
    Week,
    Month
}

class MainActivity : AppCompatActivity() {
    private fun getNotificationSchedule(timeUnit: INTERVALS, timeInterval: Int): Long {
        val unit = when(timeUnit) {
            INTERVALS.Day -> 24 * 60 * 60 * 1000
            INTERVALS.Week -> 24 * 60 * 60 * 1000 * 7
            INTERVALS.Month -> 24 * 60 * 60 * 1000 * 30
        }

        // return Calendar.getInstance().timeInMillis + (unit * timeInterval) as Long
        return Calendar.getInstance().timeInMillis + 5000
    }

    private fun generateArray(type: INTERVALS): Array<Int> {
        val limit: Int = when(type) {
            INTERVALS.Day -> 6
            INTERVALS.Week -> 51
            INTERVALS.Month -> 11
        }
        return (1..limit).asIterable().toList().toTypedArray()
    }

    private fun getIntervalLabel(type: INTERVALS): String {
        return when(type) {
            INTERVALS.Day -> "Day(s)"
            INTERVALS.Week -> "Week(s)"
            INTERVALS.Month -> "Month(s)"
        }
    }

    private fun getIntervalType(name: String): INTERVALS {
        return when(name) {
            "Day(s)" -> INTERVALS.Day
            "Week(s)" -> INTERVALS.Week
            "Month(s)" -> INTERVALS.Month
            else -> INTERVALS.Day
        }
    }

    private fun <T> changeSpinnerContents(spinner: Spinner, value: Array<T>) {
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, value)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intervalSpinner = findViewById<Spinner>(R.id.spinner)
        val intervalUnitSpinner = findViewById<Spinner>(R.id.spinner3)

        val intervalUnits = arrayOf(
            getIntervalLabel(INTERVALS.Day),
            getIntervalLabel(INTERVALS.Week),
            getIntervalLabel(INTERVALS.Month)
        )

        intervalSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generateArray(INTERVALS.Day))
        intervalUnitSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, intervalUnits)

        intervalUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                changeSpinnerContents(intervalSpinner, generateArray(getIntervalType(parent?.getItemAtPosition(position) as String)))
            }
        }

        fab.setOnClickListener {
            NotificationUtils().setNotification(getNotificationSchedule(
                getIntervalType(intervalUnitSpinner.selectedItem.toString()),
                0
            ), this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
