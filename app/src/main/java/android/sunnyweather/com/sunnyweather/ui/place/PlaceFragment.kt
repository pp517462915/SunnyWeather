package android.sunnyweather.com.sunnyweather.ui.place

import android.os.Bundle
import android.sunnyweather.com.sunnyweather.R
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * Created by pp517 on 2020/5/31.
 */
class PlaceFragment : Fragment() {

    val viewModel by lazy{ ViewModelProviders.of(this).get(PlaceViewModel::class.java)}

    private lateinit var adater: PlaceAdater

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adater = PlaceAdater(this, viewModel.placeList)
        recyclerView.adapter = adater
        searchPlaceEdit.addTextChangedListener {editable->
            val content = editable.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adater.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(this, Observer{ result->
            val places = result.getOrNull()
            if (places != null){
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.javaClass
                viewModel.placeList.addAll(places)
                adater.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }


}