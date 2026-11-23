import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HotToastService } from '@ngneat/hot-toast';
import { StockService } from '../../../services/stock.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-stock',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-stock.component.html',
  styleUrl: './create-stock.component.css'
})
export class CreateStockComponent {
  //forcefull installation
  //npm install @ngneat/hot-toast @ngneat/overview  --legacy-peer-deps
  //configure app.config.ts
  toastService = inject(HotToastService)
  router=inject(Router)
  formbuilder = inject(FormBuilder)
  stockService = inject(StockService)
  private urlRegex = /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
  stockForm: FormGroup = this.formbuilder.group({
    symbol: ['', [Validators.required, Validators.minLength(1),Validators.maxLength(15)]],
    companyName: ['', [Validators.required, Validators.pattern("^[a-zA-Z ]+$"), Validators.minLength(5), Validators.maxLength(30)]],
    price: ['', [Validators.required, Validators.min(1), Validators.max(10000)]]
  })



  get f() {
    return this.stockForm.controls;
  }

  onSubmit() {
    this.stockService.createStock(this.stockForm.value).subscribe({
      next: (res) => {
        console.log("Stock Response" + res)
        this.toastService.observe({
          loading: 'Uploading changes...',
          success: 'Data synced successfully!',
          error: 'Upload failed.'
        })
        this.router.navigateByUrl("/stocks")

      },
      error: (err) => {
            this.toastService.error("Something went wrong.error="+err)
      }
    })
  }

  resetForm() {
    this.stockForm.reset();
  }


  //1. triggerAlerts() — Immediate Feedback

  //typescript
  /*triggerAlerts() {
    this.toast.success('Saved successfully!');
    this.toast.error('Something went wrong.');
  }

  //2. saveData() — Smart Asynchronous Pipeline

  //typescript
  saveData() {
    this.http.post('/api/save', { data: true }).pipe(
      this.toast.observe({
        loading: 'Uploading changes...',
        success: 'Data synced successfully!',
        error: 'Upload failed.'
      })
    ).subscribe();
  }

  /*• When to use it: Use this for any API backend request (POST, PUT, DELETE) where the user has to wait for a network roundtrip. 
  It prevents you from having to manually write separate code blocks just to toggle loaders or handle try/catch UI alerts.
  */



}

