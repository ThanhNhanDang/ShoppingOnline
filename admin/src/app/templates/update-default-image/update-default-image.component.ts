import { Component, OnInit } from '@angular/core';
import { HttpService } from '../service/httpService/http.service';

@Component({
  selector: 'app-update-default-image',
  templateUrl: './update-default-image.component.html',
  styleUrls: ['./update-default-image.component.scss']
})
export class UpdateDefaultImageComponent implements OnInit {
  imgURLProduct!: any;
  fileProduct!: File;

  imgURLUser!: any;
  fileUser!: File;

  constructor(private http: HttpService) { }

  ngOnInit(): void {
  }

  onFileChangedProduct(files: any) {
    if (files.length === 0)
      return;
    this.fileProduct = files.item(0)
    var mimeType;
    mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      alert("Only images are supported.")
      return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURLProduct = reader.result as string;
    }
  }

  onFileChangedUser(files: any) {
    if (files.length === 0)
      return;
    this.fileUser = files.item(0)
    var mimeType;
    mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      alert("Only images are supported.")
      return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURLUser = reader.result as string;
    }
  }

  changeProduct() {
    this.http.pushFileToStorage("/upload/product-default", this.fileProduct).subscribe(() => { alert("Succsessfully") }, error => {
      alert(error.error.message)
    })
  }
  changeUser() {
    this.http.pushFileToStorage("/upload/user-default-image", this.fileUser).subscribe(() => { alert("Succsessfully") }, error => {
      alert(error.error.message)
    })

  }

}
