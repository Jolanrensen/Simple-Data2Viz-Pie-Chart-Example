import io.data2viz.color.*
import io.data2viz.geom.*
import io.data2viz.math.*
import io.data2viz.viz.*
import io.data2viz.shape.*
import io.data2viz.color.Colors.Web


fun main() {
viz {
    size = size(600, 600)  //<- you need a viz size

    // but change everything from here
    val radius: Double = 600.0 / 2.0 - 20.0
    
    val data: Array<Pair<String, Double>> = arrayOf(
        "a" to 10.0, 
        "b" to 5.0, 
        "c" to 6.0,
        "d" to 8.0
    )
    val totalCount: Double = data.sumByDouble { it.second }
    
    val arcParams: Array<ArcParams<Pair<String, Double>>> = pie<Pair<String, Double>> {
        value = { (it.second / totalCount) * tau }
    }.render(data)
    
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
        outerRadius = { radius }
    }
    
    group {
        transform {
           translate(300.0, 300.0)
        }
        arcParams.forEach {
            arcBuilder.buildArcForDatum(it.data!!, path {
                fill = colorOf(it.data!!)
                stroke = Web.black
                strokeWidth = 2.0
            })
        }
    }
    
    
    
    
    
    // to here
}.bindRendererOnNewCanvas()
}
