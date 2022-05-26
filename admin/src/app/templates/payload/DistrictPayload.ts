export class DistrictPayload{
    id!:number;
    provinceId!: number;
    name!:string;
    type!:string;
    constructor(){
        this.provinceId = 1;
    }
}