export class CategoryPayload{
    name!:string;
    id!:number;
    classFa!:string;
    constructor(name :string, classFa: string){
        this.name = name;
        this.classFa = classFa;
    }
}