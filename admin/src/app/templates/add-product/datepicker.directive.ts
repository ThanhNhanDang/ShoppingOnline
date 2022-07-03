import { AfterViewInit, Directive, ElementRef, NgZone, Output, EventEmitter } from '@angular/core';

declare var $ : any;
@Directive({
  selector: '[appDatepicker]',
  exportAs: 'datepicker'
})
export class DatepickerDirective implements AfterViewInit{
  myDate: any;
  @Output() dateEventEmitter = new EventEmitter();
  constructor(private elementRef:ElementRef, private ngZone:NgZone) { }
  ngAfterViewInit():void{
    this.ngZone.runOutsideAngular(()=>{
      $(this.elementRef.nativeElement).datepicker({
        onSelect: (date:any)=>{
          this.ngZone.run(()=>{
            this.setDate(date)
          })
        },
      })
    })
  }
  setDate(date:any){
    this.myDate = date;
    this.dateEventEmitter.emit(this.myDate);
  }
}
