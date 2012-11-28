//<start id="lis_04_transform_payload_csv"/>
import groovy.xml.MarkupBuilder

writer = new StringWriter()
builder = new MarkupBuilder(writer)

builder.orders() {
    payload.split('\n').each {line -> //<co id="lis04_groovy-payload-csv-1_cor"/>
        def fields = line.split(',') //<co id="lis04_groovy-payload-csv-2_cor"/>
        order() { //<co id="lis04_groovy-payload-csv-3_cor"/>
            subscriberId(fields[0])
            productId(fields[1])
            status(fields[2])
        }
    }
}
return writer.toString() //<co id="lis04_groovy-payload-csv-4_cor"/>
//<end id="lis_04_transform_payload_csv"/>