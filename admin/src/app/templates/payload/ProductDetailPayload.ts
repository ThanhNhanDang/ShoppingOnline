export class ProductDetailUrlImg {
    id!: number;
    file!:File;
    productId!: number;
    urlImg!: string;
    srcImg!:string;
    checkUpload!: boolean
    checkOnChange!: boolean
    constructor() {
        this.checkUpload = false;
        this.checkOnChange = false;
    }
}