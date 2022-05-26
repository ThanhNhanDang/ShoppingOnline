import { RolePlayload } from './../payload/RolePayload';
import { Gender } from './../payload/Gender';
import { UserProfile } from './../payload/UserProfile';
import { environment } from './../../../environments/environment';
import { HttpService } from './../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {
  userId = this.http.getLoginDataByKey("id");
  file!:File;
  fileName!:string;
  roles!:RolePlayload[];
  active =[{value:true, status:"Active"}, {value:false, status:'Inactive'}]

  baseUrl = environment.urlServe;
  user!:UserProfile;
  imgURL!:string;
  genders: Gender[] = [
    {id:1,value: 'Male'},
    {id:2,value: 'Female'},
    {id:3,value: 'Prefer not to say'}
  ];

  key!:string
  constructor(private http : HttpService,private activatedRouter: ActivatedRoute) { }
  
  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data=>{
      if(data.key ==null || data.key == ""){
        this.key = this.userId;
      }
      else{
        this.key = data.key
      }
      this.getProfile();
    })

    this.getAllRole();
  }
  getProfile() {
    let request = {userId: this.key};
    this.http.postRequest("/user/profile", request).subscribe(data=>{
      this.user = data;
      this.imgURL = this.baseUrl+this.user.image_url
    },error=>{
      alert(error.error.message);
    })
  }
  dateEventEmitter(date:any){
    console.log(date);
  }
  onFileChanged(files:any){
    if (files.length === 0)
      return;
    var mimeType;
    this.file = files.item(0)
    this.fileName = this.file.name;
    mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      alert("Only images are supported.")
      return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      this.imgURL = reader.result as string;

    }
  }

  updateMyAccount(){
    if(this.file!=null)
      this.user.image_url="images/avatars/"+this.user.id+"/"+this.fileName
    console.log(this.user)  
    this.http.putRequest("/user/update/admin",this.user).subscribe(()=>{
      this.updateAvatar();
      alert("Update successful.")
    }, error=>{
      alert(error.error.message)
    })
    console.log(this.user)
  }

  updateAvatar(){
    if(this.file != null){
      this.http.pushFileToStorage("/user/update/image?id="+this.user.id, this.file).subscribe(()=>{
        console.log("Upload image Successful");
      },error=>{
        alert(error.error.message);
      })
    
    }
  }

  getAllRole(){
    this.http.getRequest("/role/all").subscribe(data=>{
      this.roles = data;
    },error=>{
      alert(error.error.message);
    })
  }
}
