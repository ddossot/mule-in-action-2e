import com.prancingdonkey.product.model.*
import org.mule.api.MuleMessage

import groovy.xml.dom.DOMCategory


def product = new Product()

def products

if (!message.getInvocationProperty("products")) {
    products = []
    message.setInvocationProperty("products", products)
} else {
    products = message.getInvocationProperty("products");
}

use(DOMCategory) {

    product.name = payload.getElementsByTagName('name').item(0).text()
    product.description = payload.getElementsByTagName('description').item(0).text()
    product.upc = payload.getElementsByTagName('upc').item(0).text()
    product.price = payload.getElementsByTagName('price').item(0).text().toBigDecimal()
    product.shippingCost = payload.getElementsByTagName('shipping').item(0)
            .getElementsByTagName('cost').item(0).text().toBigDecimal()
    product.supplier = new Supplier(name: "Balin's Stuff, Inc.")

    def productType = new ProductType()
    productType.name = payload.getElementsByTagName('category').item(0)
            .getElementsByTagName('primary').item(0).text()

    product.productType = productType;

    products += product
}

message.setInvocationProperty("products", products)

return product;