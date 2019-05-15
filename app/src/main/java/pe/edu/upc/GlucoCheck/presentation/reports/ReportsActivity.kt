package pe.edu. upc.GlucoCheck.presentation.reports

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
import com.github.mikephil.charting.formatter.ValueFormatter
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
import pe.edu.upc.GlucoCheck.presentation.patient_education.PatientEducAdapter
import java.lang.Exception
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

                        pager.currentItem = p0.position
                        var graph = findViewById<View>(R.id.lineChart) as LineChart

                        val yValues = ArrayList<Entry>()
                        val aValues = ArrayList<String>()

                        db.collection("Pacientes/" + user + "/Muestras")
                            .get()
                            .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot>{
                                override fun onComplete(task: Task<QuerySnapshot>){
                                    if(task.isSuccessful()) {
                                        for (document in task.result!!) {

                                            val x = document.data["fecha"]
                                            val y = document.data["nivel"]
                                            //val z = document.data["Peso"]
                                            val a = document.data["fecha"]


                                            //val xFinal = x.toString().toFloat()
                                            val xFinal = (a as Timestamp).seconds.toFloat()
                                            val yFinal = y.toString().toFloat()
                                            //val zFinal = z.toString().toFloat()
                                            //val aFinal = (a as Timestamp).seconds.toLong()

                                            //val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()

                                            //aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                            //aValues2.add(xFinal)
                                            yValues.add(Entry(xFinal, yFinal))
                                            //yValues.add(Entry(aFinal,yFinal))
                                            /*if(yFinal < 70 || yFinal>126){
                                                yValues.add(Entry(xFinal,yFinal))
                                            }*/

                                            //zValues.add(BarEntry(xFinal,zFinal))

                                            //Log.d("TAG", document.id +  " => " + document.data["Glucosa"])
                                        }


                                        yValues.sortBy { it.x }


                                        //zValues.sortBy { it.x }

                                        for(i in yValues.indices){

                                            val aFinal = yValues[i].x.toLong()
                                            val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()
                                            aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                            yValues.set(i,Entry(i.toFloat(),yValues[i].y) )
                                        }


                                        //LIne CHART
                                        val set1 = LineDataSet(yValues,"Nivel de glucosa")
                                        set1.color = Color.BLUE
                                        set1.setCircleColor(Color.BLUE)
                                        set1.lineWidth = 1f
                                        set1.circleRadius = 3f
                                        set1.setDrawCircleHole(false)
                                        set1.valueTextSize = 0f
                                        set1.setDrawFilled(false)


                                        val  dataSets = ArrayList<ILineDataSet>()
                                        dataSets.add(set1)

                                        val data = LineData(dataSets)


                                        graph.description.isEnabled = false
                                        graph.legend.isEnabled = false
                                        graph.setPinchZoom(true)
                                        //graph.xAxis.labelCount = 7
                                        //graph.setExtraOffsets(30f, 30f, 30f, 30f);
                                        /*graph.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                        graph.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                        graph.axisLeft.enableGridDashedLine(5f, 5f, 0f) //lineChart.setDrawGridBackground()*/
                                        //graph.xAxis.labelCount = 11
                                        graph.xAxis.granularity = 1f

                                        graph.xAxis.isGranularityEnabled = true
                                        graph.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                        graph.xAxis.valueFormatter = IndexAxisValueFormatter(aValues) as ValueFormatter?
                                        graph.setData(data)
                                        graph.invalidate()
                                        graph.xAxis.axisMinimum = 0f
                                        //graph.xAxis.labelCount = aValues.count()

                                        //BarChart

                                        /*val set2 = BarDataSet(zValues, "Peso")

                                        set2.color = ContextCompat.getColor(this@MainActivity, R.color.material_blue_grey_800)

                                        val data2 = BarData(set2)



                                        bar.setData(data2)
                                        bar.invalidate()
                                        /*.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                        bar.xAxis.labelCount = 11
                                        bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)

                                        bar.animateY(1000)
                                        bar.legend.isEnabled = false*/


                                        bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)
                                        bar.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                        bar.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
                                        bar.animateY(1000)
                                        bar.setPinchZoom(true)
                                        bar.data.setDrawValues(true)
                                        bar.description.isEnabled = false*/




                                    }else{
                                        Log.d("TAG", "Error getting documents")
                                    }
                                }

                            })




                    }

                    if(p0.position == 2){

                        pager.currentItem = p0.position
                        var graph = findViewById<View>(R.id.lineChart2) as LineChart

                        val yValues = ArrayList<Entry>()
                        val aValues = ArrayList<String>()

                        db.collection("Pacientes/"+ user + "/Muestras")
                            .get()
                            .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot>{
                                override fun onComplete(task: Task<QuerySnapshot>){
                                    if(task.isSuccessful()) {
                                        for (document in task.result!!) {

                                            val x = document.data["fecha"]
                                            val y = document.data["nivel"]
                                            //val z = document.data["Peso"]
                                            val a = document.data["fecha"]


                                            //val xFinal = x.toString().toFloat()
                                            val xFinal = (a as Timestamp).seconds.toFloat()
                                            val yFinal = y.toString().toFloat()
                                            //val zFinal = z.toString().toFloat()
                                            //val aFinal = (a as Timestamp).seconds.toLong()

                                            //val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()

                                            //aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                            //aValues2.add(xFinal)
                                            //yValues.add(Entry(xFinal, yFinal))
                                            //yValues.add(Entry(aFinal,yFinal))
                                            if(yFinal < 70 || yFinal>126){
                                                   yValues.add(Entry(xFinal,yFinal))
                                               }

                                            //zValues.add(BarEntry(xFinal,zFinal))

                                            //Log.d("TAG", document.id +  " => " + document.data["Glucosa"])
                                        }


                                        yValues.sortBy { it.x }


                                        //zValues.sortBy { it.x }

                                        for(i in yValues.indices){

                                            val aFinal = yValues[i].x.toLong()
                                            val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()
                                            aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                            yValues.set(i,Entry(i.toFloat(),yValues[i].y) )
                                        }


                                        //LIne CHART
                                        val set1 = LineDataSet(yValues,"Nivel de glucosa")
                                        set1.color = Color.BLUE
                                        set1.setCircleColor(Color.BLUE)
                                        set1.lineWidth = 1f
                                        set1.circleRadius = 3f
                                        set1.setDrawCircleHole(false)
                                        set1.valueTextSize = 0f
                                        set1.setDrawFilled(false)


                                        val  dataSets = ArrayList<ILineDataSet>()
                                        dataSets.add(set1)

                                        val data = LineData(dataSets)


                                        graph.description.isEnabled = false
                                        graph.legend.isEnabled = false
                                        graph.setPinchZoom(true)
                                        //graph.xAxis.labelCount = 7
                                        //graph.setExtraOffsets(30f, 30f, 30f, 30f);
                                        /*graph.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                        graph.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                        graph.axisLeft.enableGridDashedLine(5f, 5f, 0f) //lineChart.setDrawGridBackground()*/
                                        //graph.xAxis.labelCount = 11
                                        graph.xAxis.granularity = 1f

                                        graph.xAxis.isGranularityEnabled = true
                                        graph.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                        graph.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
                                        graph.setData(data)
                                        graph.invalidate()
                                        graph.xAxis.axisMinimum = 0f
                                        //graph.xAxis.labelCount = aValues.count()

                                        //BarChart

                                        /*val set2 = BarDataSet(zValues, "Peso")

                                        set2.color = ContextCompat.getColor(this@MainActivity, R.color.material_blue_grey_800)

                                        val data2 = BarData(set2)



                                        bar.setData(data2)
                                        bar.invalidate()
                                        /*.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                        bar.xAxis.labelCount = 11
                                        bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)

                                        bar.animateY(1000)
                                        bar.legend.isEnabled = false*/


                                        bar.xAxis.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisRight.enableGridDashedLine(5f, 5f, 0f)
                                        bar.axisLeft.enableGridDashedLine(5f, 5f, 0f)
                                        bar.xAxis.position = XAxis.XAxisPosition.BOTTOM
                                        bar.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
                                        bar.animateY(1000)
                                        bar.setPinchZoom(true)
                                        bar.data.setDrawValues(true)
                                        bar.description.isEnabled = false*/




                                    }else{
                                        Log.d("TAG", "Error getting documents")
                                    }
                                }

                            })




                    }

                    if(p0.position == 1){

                        pager.currentItem = p0.position
                        var bar = findViewById(R.id.barChart) as BarChart
                        val zValues = ArrayList<BarEntry>()
                        val aValues = ArrayList<String>()

                        db.collection("Citas").whereEqualTo("paciente_id",user)
                            .get()
                            .addOnSuccessListener { documents ->
                                for(document2 in documents){

                                    val id = document2.id
                                    val fec = document2.data["fecha"]

                                    db.collection("Consultas").whereEqualTo("cita_medica_id",id)
                                        .get()
                                        .addOnSuccessListener { documentsCon ->
                                            for(document3 in documentsCon){

                                                val pes = document3.data["peso"]

                                                val xFinal = (fec as Timestamp).seconds.toFloat()
                                                val yFinal = pes.toString().toFloat()

                                                zValues.add(BarEntry(xFinal,yFinal))

                                            }

                                            if(zValues.size == documents.size()){

                                                Log.d("TAG","Completo")

                                                zValues.sortBy { it.x }

                                                for(i in zValues.indices){

                                                    val aFinal = zValues[i].x.toLong()
                                                    val dt = Instant.ofEpochSecond(aFinal).atZone(ZoneId.systemDefault()).toLocalDateTime()
                                                    aValues.add(dt.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")).toString())
                                                    zValues.set(i,BarEntry(i.toFloat(),zValues[i].y) )
                                                }

                                                val set2 = BarDataSet(zValues, "Peso")

                                                set2.color = ContextCompat.getColor(this@ReportsActivity, R.color.material_blue_grey_800)

                                                val data2 = BarData(set2)

                                                //data2.barWidth = 0.9f

                                                bar.setData(data2)
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
                                                bar.xAxis.valueFormatter = IndexAxisValueFormatter(aValues)
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