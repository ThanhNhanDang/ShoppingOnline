export class UserProfile {
    id!:number;
    name!: string;
    email!:string;
    address!:string;
    mobile!:string;
    image_url!:string;
    nameRole!:string;
    gender!:string;
    role_id!:number;
    is_email_verfied!:boolean;
    deliveryAddressId!:number;
    fileId!:number
    constructor(name:string, email:string, mobile:string, gender:string, roleId:number, active:boolean){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender; 
        this.role_id =roleId;
        this.is_email_verfied = active;
    }
}