export class ProductDetailUrlImg{
    id!:string;
    productId!:string;
    urlImg!:string;
    urlImgTemp!:string;
    file!:File;
    constructor(productId:string, urlImgTemp:string){
        this.productId = productId;
        this.urlImgTemp = this.urlImg = urlImgTemp;
    }
}