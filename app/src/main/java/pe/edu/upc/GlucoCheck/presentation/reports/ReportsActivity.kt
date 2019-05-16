package pe.edu. upc.GlucoCheck.presentation.reports

import android.annotation.TargetApi
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_reports.*
import kotlinx.android.synthetic.main.fragment_reporte1.*



import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.User
import pe.edu.upc.GlucoCheck.data.UserManager
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ReportsActivity: AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val user = UserManager.user?.id.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        configureTabLayout()

    }



    private fun configureTabLayout() {




        reporte_tab.addTab(reporte_tab.newTab().setText("Nivel Glucosa"))
        reporte_tab.addTab(reporte_tab.newTab().setText("Peso"))
        reporte_tab.addTab(reporte_tab.newTab().setText("Niveles"))
        reporte_tab.addTab(reporte_tab.newTab().setText("Información"))

        val adapter = ReportsAdapter(supportFragmentManager, reporte_tab.tabCount)
        pager.adapter = adapter



        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(reporte_tab))
        pager.currentItem = 3

        reporte_tab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {

                if (p0 != null) {

                    if(p0.position == 0){
                        val dataSets = ArrayList<ILineDataSet>()
                        pager.currentItem = p0.position
                        var graph = findViewById<View>(R.id.lineChart) as LineChart
                        graph.setNoDataText("")

                        db.collection("Pacientes").whereEqualTo("doctor_id","p0KSR8DbA3ey5fpPWvsx")
                  .get()
                  .addOnSuccessListener { documents ->
                      for(document in documents){
                          val yValues = ArrayList<Entry>()
                          //val $name = ArrayList<Entry>()

                          val id = document.id
                          val name = document.data["nombre"].toString()
                          val apell = document.data["apellido"].toString()

                          val fullName = name + " " + apell

                          db.collection("Pacientes/" + id + "/Muestras")
                              .get()
                              .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot>{
                                  override fun onComplete(task: Task<QuerySnapshot>){
                                      if(task.isSuccessful()){
                                          for(document in task.result!!){


                                              val x = document.data["fecha"]
                                              val y = document.data["nivel"]

                                              val xFinal = (x as Timestamp).seconds.toFloat()
                                              val yFinal = y.toString().toFloat()


                                              yValues.add(Entry(xFinal,yFinal))




                                          }
                                          yValues.sortBy { it.x }

                                          for(i in yValues.indices){

                                              //val aFinal = zValues[i].x.toLong()
                                              //val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()
                                              //aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                              yValues.set(i,Entry(i.toFloat(),yValues[i].y) )
                                          }

                                          val set1 = LineDataSet(yValues,fullName)
                                          val R = (1..254).random().toInt()
                                          val G = (1..254).random().toInt()
                                          val B = (1..254).random().toInt()


                                          set1.color = Color.rgb(R,G,B)

                                          set1.setCircleColor(Color.rgb(R,G,B))
                                          dataSets.add(set1)

                                          if(dataSets.size == documents.size()){

                                              val data = LineData(dataSets)
                                              graph.description.isEnabled = false
                                              graph.legend.isEnabled = true
                                              graph.setPinchZoom(true)


                                              graph.setBackgroundColor(Color.parseColor("#D9D0C5"))
                                              //graph.xAxis.labelCount = 7
                                              //graph.setExtraOffsets(30f, 30f, 30f, 30f);
                                              /*graph.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                              graph.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                              graph.axisLeft.enableGridDashedLine(5f, 5f, 0f) //lineChart.setDrawGridBackground()*/
                                              //graph.xAxis.labelCount = 11
                                              graph.xAxis.granularity = 1f

                                              graph.xAxis.isGranularityEnabled = true
                                              graph.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                              //graph.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
                                              graph.setData(data)
                                              graph.invalidate()
                                              graph.xAxis.axisMinimum = 0f



                                          }





                                      }
                                  }
                              })

                      }
                  }





                    }

                    if(p0.position == 2){
                        val dataSets = ArrayList<ILineDataSet>()
                        pager.currentItem = p0.position
                        var graph = findViewById<View>(R.id.lineChart2) as LineChart
                        graph.setNoDataText("")

                        db.collection("Pacientes").whereEqualTo("doctor_id","p0KSR8DbA3ey5fpPWvsx")
                 .get()
                 .addOnSuccessListener { documents ->
                     for(document in documents){

                         val yValues = ArrayList<Entry>()
                         //val $name = ArrayList<Entry>()

                         val id = document.id
                         val name = document.data["nombre"].toString()
                         val apell = document.data["apellido"].toString()

                         val fullName = name + " " + apell

                         db.collection("Pacientes/" + id + "/Muestras")
                             .get()
                             .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot>{
                                 override fun onComplete(task: Task<QuerySnapshot>){
                                     if(task.isSuccessful()){
                                         for(document in task.result!!){


                                             val x = document.data["fecha"]
                                             val y = document.data["nivel"]

                                             val xFinal = (x as Timestamp).seconds.toFloat()
                                             val yFinal = y.toString().toFloat()


                                             if(yFinal < 70 || yFinal>126){
                                                 yValues.add(Entry(xFinal,yFinal))
                                             }

                                             //yValues.add(Entry(xFinal,yFinal))




                                         }
                                         yValues.sortBy { it.x }

                                         for(i in yValues.indices){

                                             //val aFinal = zValues[i].x.toLong()
                                             //val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()
                                             //aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                             yValues.set(i,Entry(i.toFloat(),yValues[i].y) )
                                         }

                                         val set1 = LineDataSet(yValues,fullName)
                                         val R = (1..254).random().toInt()
                                         val G = (1..254).random().toInt()
                                         val B = (1..254).random().toInt()


                                         set1.color = Color.rgb(R,G,B)

                                         set1.setCircleColor(Color.rgb(R,G,B))
                                         dataSets.add(set1)

                                         if(dataSets.size == documents.size()){

                                             val data = LineData(dataSets)
                                             graph.description.isEnabled = false
                                             graph.legend.isEnabled = true
                                             graph.setPinchZoom(true)
                                             graph.setNoDataText("")
                                             graph.setBackgroundColor(Color.parseColor("#D9D0C5"))
                                             //graph.xAxis.labelCount = 7
                                             //graph.setExtraOffsets(30f, 30f, 30f, 30f);
                                             /*graph.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                             graph.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                             graph.axisLeft.enableGridDashedLine(5f, 5f, 0f) //lineChart.setDrawGridBackground()*/
                                             //graph.xAxis.labelCount = 11
                                             graph.xAxis.granularity = 1f

                                             graph.xAxis.isGranularityEnabled = true
                                             graph.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                             //graph.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
                                             graph.setData(data)
                                             graph.invalidate()
                                             graph.xAxis.axisMinimum = 0f



                                         }





                                     }
                                 }
                             })

                     }
                 }






                    }

                    if(p0.position == 1){
                        val dataBrs = ArrayList<IBarDataSet>()
                        pager.currentItem = p0.position
                        var bar = findViewById(R.id.barChart) as BarChart
                        bar.setNoDataText("")


                        db.collection("Pacientes").whereEqualTo("doctor_id","p0KSR8DbA3ey5fpPWvsx")
                            .get()
                            .addOnSuccessListener { documents ->
                                for(document in documents){

                                    val zValues = ArrayList<BarEntry>()
                                    val id = document.id
                                    val name = document.data["nombre"].toString()
                                    val apell = document.data["apellido"].toString()

                                    val fullName = name + " " + apell

                                    db.collection("Citas").whereEqualTo("paciente_id",id)
                                        .get()
                                        .addOnSuccessListener { documents2Com ->
                                            for (document2 in documents2Com) {

                                                val idCita = document2.id.toString()
                                                val fec = document2.data["fecha"]

                                                db.collection("Consultas").whereEqualTo("cita_medica_id",idCita)
                                                    .get()
                                                    .addOnSuccessListener { documents3Com ->
                                                        for(documents3 in documents3Com){

                                                            /*val id2 = documents3.id
                                                            val fec2 = "Hola"*/
                                                            val pes = documents3.data["peso"]

                                                            val xFinal = (fec as Timestamp).seconds.toFloat()
                                                            val yFinal = pes.toString().toFloat()
                                                            zValues.add(BarEntry(xFinal,yFinal))
                                                        }

                                                        if(zValues.size == documents2Com.size()){


                                                            zValues.sortBy { it.x }

                                                            /*for(i in 0..2){
                                                                zValues.removeAt(i)
                                                            }*/

                                                            for(i in zValues.indices){

                                                                //val aFinal = zValues[i].x.toLong()
                                                                //val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()
                                                                //aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                                                zValues.set(i,BarEntry(i.toFloat(),zValues[i].y) )
                                                            }

                                                            val set1= BarDataSet(zValues,fullName)

                                                            val R = (1..254).random().toInt()
                                                            val G = (1..254).random().toInt()
                                                            val B = (1..254).random().toInt()


                                                            set1.color = Color.rgb(R,G,B)

                                                            dataBrs.add(set1)

                                                            if(dataBrs.size == documents.size()){

                                                                val groupSpace = 0.4f
                                                                val barSpace = 0f
                                                                val barWidth = 0.3f

                                                                val data = BarData(dataBrs)
                                                                data.setValueFormatter(LargeValueFormatter())

                                                                bar.setData(data)
                                                                data.setBarWidth(barWidth)
                                                                bar.xAxis.axisMinimum = 0f
                                                                bar.xAxis.axisMaximum = 0f + data.getGroupWidth(groupSpace,barSpace) * 2
                                                                bar.groupBars(0f, groupSpace, barSpace)
                                                                bar.invalidate()
                                                                bar.setBackgroundColor(Color.parseColor("#D9D0C5"))
                                                                bar.setNoDataText("")




                                                                /*.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                                                bar.xAxis.labelCount = 11
                                                                bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                                                bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                                                bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)

                                                                bar.animateY(1000)
                                                                bar.legend.isEnabled = false*/
                                                                //bar.xAxis.axisMinimum = 0f
                                                                //bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                                                //bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                                                //bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)
                                                                bar.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                                                bar.xAxis.labelCount = 2
                                                                //bar.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
                                                                //bar.animateY(1000)
                                                                //bar.setPinchZoom(true)
                                                                bar.description.isEnabled = false
                                                                //bar.data.setDrawValues(true)
                                                                //bar.description.isEnabled = false
                                                                //bar.xAxis.granularity = 0f
                                                                //bar.xAxis.axisMinimum = 0f


                                                            }
                                                        }

                                                    }


                                                /*db.collection("Consultas").whereEqualTo("cita_medica_id",idCita)
                                                    .get()
                                                    .addOnSuccessListener { documents3Com ->
                                                        for(document3 in documents3Com){

                                                            val peso = document3.data["peso"]
                                                            val xFinal = (fec as Timestamp).seconds.toFloat()
                                                            val yFinal = peso.toString().toFloat()

                                                            zValues.add(BarEntry(xFinal,yFinal))
                                                        }

                                                        zValues.sortByDescending { it.x }

                                                        for(i in 0..2){

                                                            //val aFinal = zValues[i].x.toLong()
                                                            //val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()
                                                            //aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                                            zValues.set(i,BarEntry(i.toFloat(),zValues[i].y) )
                                                        }

                                                        val set2 = BarDataSet(zValues, "Peso")
                                                        dataBrs.add(set2)

                                                        if(dataBrs.size == documents.size()){


                                                            val data = BarData(dataBrs)

                                                            bar.setData(data)
                                                            bar.invalidate()


                                                            /*.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                                            bar.xAxis.labelCount = 11
                                                            bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                                            bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                                            bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)

                                                            bar.animateY(1000)
                                                            bar.legend.isEnabled = false*/
                                                            //bar.xAxis.axisMinimum = 0f
                                                            //bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                                            //bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                                            //bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)
                                                            bar.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                                            bar.xAxis.labelCount = aValues.size
                                                            //bar.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
                                                            //bar.animateY(1000)
                                                            //bar.setPinchZoom(true)
                                                            bar.description.isEnabled = false
                                                            //bar.data.setDrawValues(true)
                                                            //bar.description.isEnabled = false
                                                            //bar.xAxis.granularity = 0f
                                                            //bar.xAxis.axisMinimum = 0f

                                                        }





                                                    }*/


                                            }
                                        }



                                }
                            }



                    }

                    if(p0.position == 3){
                        pager.currentItem = p0.position
                    }


                    Log.d("TAG","Hola")

                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

        }



        )



    }
}