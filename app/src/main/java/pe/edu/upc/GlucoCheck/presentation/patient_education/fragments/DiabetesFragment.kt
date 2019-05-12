package pe.edu.upc.GlucoCheck.presentation.patient_education.fragments


import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_diabetes.*

import pe.edu.upc.GlucoCheck.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DiabetesFragment : Fragment() {


//    var tts = TextToSpeech(activity,this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diabetes, container, false)
    }

//    override fun onInit(status: Int) {
//        if (status == TextToSpeech.SUCCESS) {
//            // set US English as language for tts
//            val result = tts!!.setLanguage(Locale.US)
//
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS","The Language specified is not supported!")
//            } else {
//                txt2Speech.isEnabled = true
//            }
//
//        } else {
//            Log.e("TTS", "Initilization Failed!")
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        txt2Speech.setOnClickListener {
//            val text = answer1_lbl.text.toString()
//            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
//
//        }
    }



}
