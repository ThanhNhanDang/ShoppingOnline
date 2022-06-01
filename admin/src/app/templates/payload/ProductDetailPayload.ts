export class ProductDetailUrlImg{
    id!:string;
    productId!:string;
    urlImg!:string;
    urlImgTemp!:string;
    file!:File;
    fileId!:number;
    constructor(productId:string, urlImgTemp:string){
        this.productId = productId;
        this.urlImgTemp = this.urlImg = urlImgTemp;
        this.fileId = 2
    }
}