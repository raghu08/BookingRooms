package com.roombooking.ui.roomdetail

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.roombooking.R
import com.roombooking.custom.ViewPagerImageAdapter
import com.roombooking.model.ItemParcelable
import com.roombooking.model.SendPassModel
import com.roombooking.model.TimeBound
import com.roombooking.ui.addparticipant.AddParticipantActivity
import com.roombooking.ui.roomdetail.adapter.TimesAdapter
import me.relex.circleindicator.CircleIndicator
import java.util.ArrayList


class RoomDetailActivity : AppCompatActivity(),TimesAdapter.NotifyDataToUi{

    private var times: Array<String>? = null
    private val bounds = ArrayList<TimeBound>()
    private var dateStr: String? = null
    private var startTime: String? = null
    private var endTime: String? = null
    private var setTime: TextView? = null
    private var reservation: TextView? = null
    private var reset: Button? = null
    private lateinit var rv: RecyclerView



    companion object {
        val PARCEL = "parcel"
        val DATE_STRING = "dateStr"
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_room_detail)
        dateStr = intent.extras!!.getString(DATE_STRING)
        initUi()

    }

    private fun initUi() {
        val item = intent.extras?.getParcelable<ItemParcelable>(PARCEL)
        //val binding = DataBindingUtil.setContentView<ActivityRoomDetailBinding>(this, R.layout.activity_room_detail)
        //binding.item = item
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = getString(R.string.room_detail_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTime = findViewById(R.id.setTime) as TextView
        reservation = findViewById(R.id.reservation) as TextView
        reset = findViewById(R.id.reset) as Button
        val equipment = findViewById(R.id.equipment_list) as TextView
        val book = findViewById(R.id.book_room) as Button
        book.setOnClickListener(View.OnClickListener {
            if (reset?.getVisibility() == View.GONE) {
                Toast.makeText(this@RoomDetailActivity, R.string.time_set_msg, Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val model = SendPassModel()
            model.name = item!!.name
            model.startTime = startTime
            model.endTime = endTime
            model.date = dateStr
            val intent = Intent(this@RoomDetailActivity, AddParticipantActivity::class.java)
            intent.putExtra(AddParticipantActivity.SEND_PASS, model)
            startActivity(intent)
        })
        for (equipmentStr in item!!.equipment) {
            equipment.append(equipmentStr + "\n")
        }

        updateTimebounds(item)
        initPager(item)
        initListAndUpdateTimes(item)


    }

    private fun initListAndUpdateTimes(item: ItemParcelable?) {
        rv = findViewById(R.id.recyclerView) as RecyclerView
        val timeAdapter = TimesAdapter(this, bounds, this)
        rv.adapter = timeAdapter
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        reset?.setOnClickListener({
            bounds.clear()
            rv.visibility = View.VISIBLE
            reset?.visibility = View.GONE
            setTime?.visibility = View.GONE
            reservation?.setText(R.string.reservation_info)
            updateTimebounds(item)
            timeAdapter.notifyData(bounds)
        })
    }

    private fun initPager(item: ItemParcelable) {
        val pager = findViewById(R.id.pager) as ViewPager
        val indicator = findViewById(R.id.indicator) as CircleIndicator
        val adapter = ViewPagerImageAdapter(this@RoomDetailActivity, item.images)
        pager.adapter = adapter
        indicator.setViewPager(pager)
        adapter.registerDataSetObserver(indicator.dataSetObserver)

    }

    private fun updateTimebounds(item: ItemParcelable?) {
        for (time in item!!.avail) {
            times = time.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val bound = TimeBound()
            bound.startTime = times!![0]
            bound.endTime = times!![1]
            bounds.add(bound)

        }
    }


    override fun updateStatus(startTime: String, endTime: String) {
        this.startTime = dateStr + startTime
        this.endTime = dateStr + endTime
        rv.visibility = View.GONE
        setTime?.visibility = View.VISIBLE
        reset?.visibility = View.VISIBLE
        reservation?.setText(R.string.time_set)
        setTime?.text = startTime.substring(0, 6) + " - " + endTime.substring(0, 6)

    }

}