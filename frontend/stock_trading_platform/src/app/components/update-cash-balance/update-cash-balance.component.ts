import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { UserService } from '../../services/user.service';
import { HotToastService } from '@ngneat/hot-toast';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-cash-balance',
  standalone: true,
  imports: [CommonModule,MatFormFieldModule,MatFormField,ReactiveFormsModule],
  templateUrl: './update-cash-balance.component.html',
  styleUrl: './update-cash-balance.component.css'
})
export class UpdateCashBalanceComponent {
  formBuilder = inject(FormBuilder)
  userService=inject(UserService)
  toastService=inject(HotToastService)
  router=inject(Router)
  user: any = localStorage.getItem('user')
  stringUser = JSON.parse(this.user)
  isSubmitting=false


  updateForm: FormGroup = this.formBuilder.group({
    username: [this.stringUser.username,[Validators.required, Validators.minLength(5), Validators.maxLength(10), Validators.pattern("^[a-zA-Z]+[-_@]?[0-9]*$")]],
    cashBalance:[0, [Validators.required, Validators.min(50), Validators.max(10000)]]
  })


  updateBalance(){
     this.isSubmitting=true
     console.log(this.updateForm.value)
      this.userService.updateUser(this.stringUser.id,this.updateForm.value).subscribe({
        next:(res:any)=>{
            console.log(res)
            this.isSubmitting=false
            localStorage.removeItem('user')
            localStorage.setItem('user',JSON.stringify(res.user))
            this.toastService.success("User updated.")
            this.router.navigateByUrl('/myAccount')
        },
        error:(err)=>{
              this.toastService.error("Failed to update user.")
               this.router.navigateByUrl('/myAccount')
        }
      })
  
  }



  get f(){
    return this.updateForm.controls;
  }



}
