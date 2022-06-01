export class ReviewPayload{
	userId!:string;
	productId!:string;
    message!:string;
    reviewDate!:string;
    reviewDateCustom!:string;
    urlImg!:string;
    userName!:string;
    email!:string;
    urlImgProduct!:string;
    productName!:string;
    ratting!:number
    starReview !:boolean[]
    fileIdUser!:number;
    fileIdProduct!:number;
    constructor(userId:string, productId:string, message:string,
        reviewDate:string, urlImg:string,userName:string, ratting:number){
            this.userId = userId;
            this.productId = productId;
            this.message = message;
            this.reviewDate = reviewDate;
            this.urlImg = urlImg;
            this.userName = userName;
            this.ratting = ratting;
          
        }
}