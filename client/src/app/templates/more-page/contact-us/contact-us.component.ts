import { FeedbackPayload } from './../../../payload/FeedbackPayload';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {
  feedback: FeedbackPayload = new FeedbackPayload();
  constructor(private http: HttpServiceService) {
    this.http.setTitle("E Store-Contact us")
  }

  ngOnInit(): void {
  }
  sendFeedback() {
    this.http.postRequest("/feedback/save", this.feedback).subscribe(()=>{
      alert("Successfully!");
    })  
  }


}
