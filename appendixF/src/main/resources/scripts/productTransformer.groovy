import com.prancingdonkey.product.model.*

def product = new Product()
product.name = payload.elementText('name')
product.description = payload.element('description').elementText('long')
product.upc = payload.elementText('upc')
product.price = payload.elementText('price').toBigDecimal()
product.shippingCost = payload.element('shipping').element('cost').elementText('amount').toBigDecimal()
product.supplier = new Supplier(name: "Balin's Stuff, Inc.")

def productType = new ProductType()
productType.name = payload.element('category').elementText('primary')

product.productType = productType

return product;