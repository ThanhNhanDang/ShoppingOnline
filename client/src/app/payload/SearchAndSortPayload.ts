export class SearchAndSortPayload{
    key!:string;
    page!:number;
    size!:number;
    sortKey!:string;
    oldOrNew!:boolean;
    cateId!:number;
    constructor(){
        this.key = ""
        this.page = 0 ;
        this.size = 9;
        this.sortKey = "id";
        this.oldOrNew = true;
        this.cateId = 0;
    }
}