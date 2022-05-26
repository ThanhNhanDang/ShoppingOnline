import { FormControl } from '@angular/forms';
import { ProductPayload } from './../productPayload';
export class EditProduct{
    file!: any;
    dto! : ProductPayload;
    
    constructor(file:any, dto:ProductPayload){
        this.file = file;
        this.dto = dto
    }
}