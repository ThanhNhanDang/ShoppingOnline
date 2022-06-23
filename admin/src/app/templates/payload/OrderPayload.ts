export class OrderPayload {
    id!: number;
    orderId!: number
    userName!: String;
    userId!: Number
    deliveryAddress!: String;
    orderDate!: String;
    paymentType!: String
    pricePerItem!: Number
    productId!: Number
    productName!: String
    quantity!: Number
    price!: number
    countTheMostOrders!: number;
    constructor() { }
}