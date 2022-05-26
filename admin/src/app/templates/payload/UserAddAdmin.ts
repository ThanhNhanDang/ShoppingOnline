export class UserAdmin {
    name!: string;
    email!:string;
    mobile!:string;
    gender!:string;
    role_id!:number;
    is_email_verfied!:boolean;
    password!:string
    
    constructor(name:string,password:string, email:string, mobile:string, gender:string, roleId:number, active:boolean){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender; 
        this.role_id =roleId;
        this.password = password;
        this.is_email_verfied = active;

    }
}