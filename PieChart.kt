import io.data2viz.color.*
import io.data2viz.geom.*
import io.data2viz.math.*
import io.data2viz.viz.*
import io.data2viz.shape.*
import io.data2viz.color.Colors.Web


fun main() {
viz {
    size = size(600, 600)  //<- you need a viz size
    
    // set margin and radius of the pie chart
    val margin: Double = 20.0
    val radius: Double = 600.0 / 4.0 - margin
    
    // add any type of data you like
    val data: Array<Pair<String, Double>> = arrayOf(
        "a" to 10.0, 
        "b" to 5.0, 
        "c" to 6.0,
        "d" to 8.0
    )
    
    // used to calculate the percentage of each data item
    val totalCount: Double = data.sumByDouble { it.second }
    
    // generate arcParams for this data
    val arcParams: Array<ArcParams<Pair<String, Double>>> = pie<Pair<String, Double>> {
        value = { (it.second / totalCount) * tau }
    }.render(data)
    
    // you can color your data anyway you like, I just made a lambda function mapping a data item to a color based on index
    val colorOf: (Pair<String, Double>) -> RgbColor = {
        listOf(
            Web.purple,
            Web.navy,
            Web.teal,
            Web.lime,
            Web.yellow,
            Web.red,
            Web.gray
        )[data.indexOf(it)]
    }
    
    // connect the arcParams to arcBuilder so a path can be created
    val arcBuilder: ArcBuilder<Pair<String, Double>> = arcBuilder {
        startAngle = { data ->
            arcParams.find{ it.data == data }!!.startAngle
        }
        endAngle = { data ->
            arcParams.find { it.data == data }!!.endAngle
        }
        padAngle = { data ->
            arcParams.find { it.data == data }!!.padAngle ?: 0.0
        }
        
        // the radius of the pie chart.
        // innerRadius is also possible to make a donut chart
        outerRadius = { radius }
    }
    
    // place the pie chart
    group {
        transform {
           translate(x = 150.0, y = 300.0)
        }
        
        // for each arcParam (with data) build an arc using a new path given color, stroke etc
        arcParams.forEach {
            arcBuilder.buildArcForDatum(it.data!!, path {
                fill = colorOf(it.data!!)
                stroke = Web.black
                strokeWidth = 2.0
            })
        }
    }
    
    // place the legend
    group {
        transform {
           translate(x = 300.0 + margin, y = 150.0 + margin)
        }
        
        // places a rectangle and some text right of the pie chart
        data.forEachIndexed { index: Int, data: Pair<String, Double> ->
        	group {
                transform { translate(y = index * 15.0) }
                rect {
                    size = size(10.0, 10.0)
                    fill = colorOf(data)
                }
                group {
                    transform { translate(x = 15.0, y = 9.0) }
                    text {
                        textContent = "test"
                    }
                }
            }
        }        
    }
    
    
    
    
    
    // to here
}.bindRendererOnNewCanvas()
}
