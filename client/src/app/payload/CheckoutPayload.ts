
export class CheckoutPayload{
    userId!:string;
    totalPrice!:number;
    paymentType!:string;
    deliveryAddress!:string;
    provinceId!:number;
    districtId!:number;
    wardId!:number;
    constructor(userId:string, totalPrice:number, paymentType:string, deliveryAddress:string, provinceId:number, districtId:number,  wardId:number){
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.paymentType = paymentType;
        this.deliveryAddress = deliveryAddress;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
    }
}