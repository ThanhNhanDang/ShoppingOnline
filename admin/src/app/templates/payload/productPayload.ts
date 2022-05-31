

export class ProductPayload{
    id!:number;
    category_id!:number;
    name!:string;
    price!:number;
    added_on!:string;
    exprideDate!:string;
    checkselect!:boolean;
    exDate!:string;
    inStock!:number;
    unitSold!:number;
    urlImg!: string;
    description!:string;
    fileId!:number;

    constructor(){
        this.category_id = 1;
        this.name = "";
        this.exDate = "";
        this.description="";
        this.urlImg = "2";
    }

}